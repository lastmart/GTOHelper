package com.gtohelper.presentation.ui.competitors_results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.gtohelper.data.FakeCompetitors
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.databinding.FragmentCompetitorsResultsBinding
import com.gtohelper.presentation.OnItemClickListener
import com.gtohelper.presentation.ui.competitors_results.adapter.CompetitorResultsAdapter

class CompetitorsResultsFragment : Fragment(), OnItemClickListener<CompetitorResults> {

    companion object {
        fun newInstance() = CompetitorsResultsFragment()
    }

    private lateinit var viewModel: CompetitorsResultsViewModel
    private lateinit var binding: FragmentCompetitorsResultsBinding
    private lateinit var adapter: CompetitorResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCompetitorsResultsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchView()
        initRecyclerView()
    }

    private fun initSearchView() {
        binding.competitorsResultsFragmentSearchView.setIconifiedByDefault(false)
        binding.competitorsResultsFragmentSearchView.setOnQueryTextListener(object :
            OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemClicked(item: CompetitorResults) {
        println("Id: ${item.id}")
        //   Toast.makeText(requireContext(), item.id, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val data = FakeCompetitors.generateCompetitorsWithResults(13)

        adapter = CompetitorResultsAdapter(data, this)

        binding.recyclerViewCompetitorsResults.layoutManager = layoutManager
        binding.recyclerViewCompetitorsResults.adapter = adapter
    }
}