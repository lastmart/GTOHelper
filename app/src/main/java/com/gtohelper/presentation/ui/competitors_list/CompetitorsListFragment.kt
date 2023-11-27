package com.gtohelper.presentation.ui.competitors_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gtohelper.data.FakeCompetitors
import com.gtohelper.data.models.Competitor
import com.gtohelper.databinding.FragmentCompetitorsListBinding
import com.gtohelper.presentation.OnItemClickListener
import com.gtohelper.presentation.ui.competitors_list.adapter.CompetitorsAdapter

class CompetitorsListFragment : Fragment(), OnItemClickListener<Competitor> {
    companion object {
        fun newInstance() = CompetitorsListFragment()
    }

    private lateinit var viewModel: CompetitorsListViewModel
    private lateinit var binding: FragmentCompetitorsListBinding
    private lateinit var adapter: CompetitorsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCompetitorsListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initSearchView()
    }

    override fun onItemClicked(item: Competitor) {
        println("Id: ${item.id}")
        //   Toast.makeText(requireContext(), item.id, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val data = FakeCompetitors.generateCompetitors(13)

        adapter = CompetitorsAdapter(data, this)
        binding.recyclerViewCompetitorsResults.layoutManager = layoutManager
        binding.recyclerViewCompetitorsResults.adapter = adapter
    }

    private fun initSearchView() {
        binding.searchViewCompetitorsListFragment.setIconifiedByDefault(false)
        binding.searchViewCompetitorsListFragment.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}