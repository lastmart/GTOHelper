package com.gtohelper.presentation.ui.competitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.R
import com.gtohelper.databinding.FragmentCompetitionsListBinding
import com.gtohelper.domain.models.Competition
import com.gtohelper.presentation.ui.competitions.components.adapter.CompetitionsAdapter
import com.gtohelper.presentation.ui.competitions.components.CompetitionPreviewDetailsDialogFragment
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.runOnUiThread
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CompetitionListFragment : Fragment(), OnItemClickListener<Competition> {
    private val viewModel: CompetitionsListViewModel by viewModels()

    private val binding: FragmentCompetitionsListBinding by lazy {
        FragmentCompetitionsListBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: CompetitionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = CompetitionsAdapter(listOf(), this)
        binding.recyclerViewTablesPreview.adapter = adapter
        initViewModel()
        initSearchView()

        binding.mainFragmentAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_competitionCreationFragment)
        }

        return binding.root
    }

    override fun onItemClicked(item: Competition) {
        Toast.makeText(requireContext(), item.toString(), Toast.LENGTH_SHORT).show()

        val argsBundle = Bundle().apply {
            putString(CompetitionPreviewDetailsDialogFragment.TITLE_ARG, item.name)
            putString(CompetitionPreviewDetailsDialogFragment.DESCRIPTION_ARG, item.description)
        }

        findNavController().navigate(
            R.id.action_mainFragment_to_tablePreviewDetailsDialogFragment,
            argsBundle
        )
    }

    private fun initViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.competitions.collect {
                runOnUiThread { adapter.setData(it) }
            }
        }
    }

    private fun initSearchView() {
        binding.mainFragmentSearchView.setIconifiedByDefault(false)

        binding.mainFragmentSearchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchCompetitions(query.toString())
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.searchCompetitions(query.toString())
                return true
            }
        })
    }
}