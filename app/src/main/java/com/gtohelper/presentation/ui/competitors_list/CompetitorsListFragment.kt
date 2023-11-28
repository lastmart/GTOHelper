package com.gtohelper.presentation.ui.competitors_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gtohelper.data.models.Competitor
import com.gtohelper.databinding.FragmentCompetitorsListBinding
import com.gtohelper.presentation.OnItemClickListener
import com.gtohelper.presentation.ui.competitors_list.adapter.CompetitorsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetitorsListFragment : Fragment(), OnItemClickListener<Competitor> {
    companion object {
        fun newInstance() = CompetitorsListFragment()
    }

    private lateinit var viewModel: CompetitorsListViewModel
    private lateinit var binding: FragmentCompetitorsListBinding
    private lateinit var adapter: CompetitorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompetitorsListBinding.inflate(layoutInflater)

        initSearchView()
        initViewModel()

        return binding.root
    }

    override fun onItemClicked(item: Competitor) {
        println("Id: ${item.id}")
        //   Toast.makeText(requireContext(), item.id, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[CompetitorsListViewModel::class.java]

        viewModel.competitorsLiveData.observe(viewLifecycleOwner) {
            showCompetitors(it)
        }

        lifecycleScope.launch {
            viewModel.getCompetitors(13)
        }
    }

    private fun showCompetitors(competitors: List<Competitor>) {
        adapter = CompetitorsAdapter(competitors, this)
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
                lifecycleScope.launch(Dispatchers.Default) {
                    newText?.let {
                        viewModel.searchCompetitorsByName(it)
                    } ?: viewModel.getCompetitors()
                }
                return true
            }
        })
    }
}