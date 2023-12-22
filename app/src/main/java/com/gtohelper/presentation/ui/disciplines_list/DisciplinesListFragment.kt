package com.gtohelper.presentation.ui.disciplines_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.R
import com.gtohelper.databinding.FragmentDisciplinesListBinding
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.MainActivity
import com.gtohelper.presentation.ui.disciplines_list.adapter.DisciplineAdapter
import com.gtohelper.presentation.ui.disciplines_list.delete_competition.DeleteCompetitionDialogFragment
import com.gtohelper.presentation.ui.disciplines_list.delete_discpiline.DeleteDisciplineDialogFragment
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.OnItemLongClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisciplinesListFragment : Fragment(),
    OnItemClickListener<DisciplinePresentation>, OnItemLongClickListener<DisciplinePresentation> {

    companion object {
        fun newInstance() = DisciplinesListFragment()

        const val TEAM_NAME = "TEAM_NAME"
    }

    private val viewModel: DisciplinesListViewModel by viewModels()
    private lateinit var binding: FragmentDisciplinesListBinding
    private lateinit var adapter: DisciplineAdapter

    private lateinit var menuHost: MenuHost
    private lateinit var menuProvider: MenuProvider

    private lateinit var teamName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisciplinesListBinding.inflate(layoutInflater)

        initArgs()
        initRecyclerView()
        initViewModel()
        initToolbarMenu()
        initButtonClickListeners()

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        menuHost.removeMenuProvider(menuProvider)
    }

    override fun onResume() {
        (requireActivity() as MainActivity).supportActionBar?.title = teamName
        super.onResume()
    }

    private fun initArgs() {
        val defaultTeamName = "МГУ"
        teamName = arguments?.getString(TEAM_NAME, defaultTeamName) ?: defaultTeamName
    }

    private fun initRecyclerView() {
        adapter = DisciplineAdapter(listOf(), this, this)
        binding.disciplinesRecyclerView.adapter = adapter
    }

    private fun initToolbarMenu() {
        initMenuProvider()
        menuHost = requireActivity()
        menuHost.addMenuProvider(menuProvider)
    }

    private fun initMenuProvider() {
        menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_delete, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.itemDelete -> {
                        deleteCompetition()
                        return true
                    }
                }

                return false
            }
        }
    }

    private fun initButtonClickListeners() {
        binding.disciplinesListAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_disciplinesListFragment_to_addDisciplineFragment)
        }
    }


    private fun initViewModel() {
        viewModel.disciplinesLiveData.observe(viewLifecycleOwner) {
            showDisciplines(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getDisciplines()
        }
    }

    private fun showDisciplines(disciplines: List<DisciplinePresentation>) {
        (requireActivity() as MainActivity).supportActionBar?.title = teamName
        adapter.setData(disciplines)
    }

    private fun deleteCompetition() {
        setFragmentResultListener(DeleteCompetitionDialogFragment.DELETE_RESULT) { key, bundle ->
            val isDeleted = bundle.getBoolean(DeleteDisciplineDialogFragment.DELETE_RESULT)

            if (isDeleted) {
                Toast.makeText(requireContext(), "Delete $teamName", Toast.LENGTH_SHORT).show()

                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.deleteCompetitionByName(teamName)
                }

                findNavController().navigateUp()
            }
        }

        navigateToDeleteDialogFragment()
    }

    private fun navigateToDeleteDialogFragment() {
        val args = Bundle().apply {
            putString(DeleteCompetitionDialogFragment.COMPETITION_ARG, teamName)
        }
        findNavController().navigate(
            R.id.action_disciplinesListFragment_to_deleteCompetitionDialogFragment,
            args
        )
    }

    private fun deleteDiscipline(discipline: Discipline) {
        val args = Bundle().apply {
            putString(DeleteDisciplineDialogFragment.DISCIPLINE_ARG, discipline.name)
        }

        findNavController().navigate(
            R.id.action_disciplinesListFragment_to_deleteDisciplineDialogFragment,
            args
        )
    }

    override fun onItemClicked(item: DisciplinePresentation) {
        println("$item clicked")
    }

    override fun onItemLongClicked(item: DisciplinePresentation): Boolean {
        setFragmentResultListener(DeleteDisciplineDialogFragment.DELETE_RESULT) { key, bundle ->
            val isDeleted = bundle.getBoolean(DeleteDisciplineDialogFragment.DELETE_RESULT)

            if (isDeleted) {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.deleteDiscipline(item)
                    viewModel.getDisciplines()
                }
            }
        }

        deleteDiscipline(item)

        return true
    }
}