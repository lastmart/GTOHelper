package com.gtohelper.presentation.ui.disciplines_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.R
import com.gtohelper.databinding.FragmentDisciplinesListBinding
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.disciplines_list.adapter.DisciplineAdapter
import com.gtohelper.presentation.ui.disciplines_list.delete_competition.DeleteCompetitionDialogFragment
import com.gtohelper.presentation.ui.disciplines_list.delete_discpiline.DeleteDisciplineDialogFragment
import com.gtohelper.presentation.ui.main.MainActivity
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.OnItemLongClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisciplinesListFragment : Fragment(),
    OnItemClickListener<Discipline>, OnItemLongClickListener<Discipline> {

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

        return binding.root
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
        (requireActivity() as MainActivity).supportActionBar?.title = teamName
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

    override fun onPause() {
        super.onPause()
        menuHost.removeMenuProvider(menuProvider)
    }

    private fun initViewModel() {
        viewModel.disciplinesLiveData.observe(viewLifecycleOwner) {
            showDisciplines(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getDisciplines()
        }
    }

    private fun showDisciplines(disciplines: List<Discipline>) {
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

    override fun onItemClicked(item: Discipline) {
        println("$item clicked")
    }

    override fun onItemLongClicked(item: Discipline): Boolean {
        setFragmentResultListener(DeleteDisciplineDialogFragment.DELETE_RESULT) { key, bundle ->
            val isDeleted = bundle.getBoolean(DeleteDisciplineDialogFragment.DELETE_RESULT)

            if (isDeleted) {
                lifecycleScope.launch {
                    viewModel.getDisciplines()
                }
            }
        }

        deleteDiscipline(item)

        return true
    }
}