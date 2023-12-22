package com.gtohelper.presentation.ui.competitors_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gtohelper.databinding.FragmentCompetitorsListBinding
import com.gtohelper.domain.models.Competitor
import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment
import com.gtohelper.presentation.ui.competitors_list.adapter.CompetitorsAdapter
import com.gtohelper.presentation.ui.util.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompetitorsListFragment : Fragment(), OnItemClickListener<Competitor> {

    private val viewModel: CompetitorsListViewModel by viewModels()
    private val binding: FragmentCompetitorsListBinding by lazy {
        FragmentCompetitorsListBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: CompetitorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initSearchView()

        val id = requireArguments().getInt(CompetitionDetailsFragment.COMPETITION_ID_ARG)
        viewModel.bindCompetitionId(id)

        adapter = CompetitorsAdapter(listOf(), this)
        binding.recyclerViewCompetitorsResults.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.competitors.collect {
                adapter.setData(it)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCompetitorButton.setOnClickListener {
            viewModel.create()
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