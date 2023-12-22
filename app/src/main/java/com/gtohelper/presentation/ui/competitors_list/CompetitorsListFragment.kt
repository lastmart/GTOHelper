package com.gtohelper.presentation.ui.competitors_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.R
import com.gtohelper.databinding.FragmentCompetitorsListBinding
import com.gtohelper.domain.models.Competitor
import com.gtohelper.presentation.ui.competitors_list.adapter.CompetitorsAdapter
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.runOnUiThread
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompetitorsListFragment : Fragment(), OnItemClickListener<Competitor> {

    private val viewModel: CompetitorsListViewModel by viewModels()
    private val binding by lazy { FragmentCompetitorsListBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initSearchView()
        val adapter = CompetitorsAdapter(listOf(), this)
        binding.recyclerViewCompetitorsResults.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.competitors.collect {
                    runOnUiThread {
                        adapter.setData(it)
                    }
                }
            }
            launch {
                viewModel.searchQuery.collect {
                    binding.searchViewCompetitorsListFragment.setQuery(it, true)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCompetitorButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_competitorsListFragment_to_competitorCreation,
                requireArguments()
            )
        }
    }

    override fun onItemClicked(item: Competitor) {
//        println("Id: ${item.id}")
//        Toast.makeText(requireContext(), item.id, Toast.LENGTH_SHORT).show()
    }


    private fun initSearchView() {
        binding.searchViewCompetitorsListFragment.setIconifiedByDefault(false)
        binding.searchViewCompetitorsListFragment.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.updateSearch(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateSearch(newText.toString())
                return true
            }
        })
    }
}