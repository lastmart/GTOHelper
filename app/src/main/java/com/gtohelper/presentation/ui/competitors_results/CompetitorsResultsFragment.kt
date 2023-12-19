package com.gtohelper.presentation.ui.competitors_results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gtohelper.data.database.competitor.CompetitorEntity
import com.gtohelper.data.database.sport.SportEntity
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.databinding.FragmentCompetitorsResultsBinding
import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.OnItemClickListener
import com.gtohelper.presentation.ui.competitors_results.adapter.CompetitorResultsAdapter
import com.gtohelper.presentation.ui.util.appDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompetitorsResultsFragment : Fragment(), OnItemClickListener<CompetitorResults> {

    companion object {
        fun newInstance() = CompetitorsResultsFragment()
    }

    private lateinit var viewModel: CompetitorsResultsViewModel
    private lateinit var binding: FragmentCompetitorsResultsBinding
    private lateinit var adapter: CompetitorResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompetitorsResultsBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            appDatabase.getCompetitorDao().upsertCompetitor(
                CompetitorEntity(2, "Ivan", 43, Gender.MALE, "Crossfit", 5)
            )

            appDatabase.getSportDao().upsertSport(
                SportEntity("Run100m", listOf(11, 22, 33, 44))
            )

            println(appDatabase.getSportDao().getSportCompetitors("Run100m"))

        }

        initSearchView()
        initViewModel()

        return binding.root
    }

    override fun onItemClicked(item: CompetitorResults) {
        println("Id: ${item.id}")
        //   Toast.makeText(requireContext(), item.id, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[CompetitorsResultsViewModel::class.java]

        viewModel.competitorsResultsLiveData.observe(viewLifecycleOwner) {
            showCompetitorsResults(it)
        }

        lifecycleScope.launch(Dispatchers.Default) {
            viewModel.getCompetitorsResults()
        }
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
                lifecycleScope.launch {
                    newText?.let {
                        viewModel.searchCompetitorsResultsByName(it)
                    } ?: viewModel.getCompetitorsResults()
                }

                return true
            }
        })
    }

    private fun showCompetitorsResults(competitorsResults: List<CompetitorResults>) {
        adapter = CompetitorResultsAdapter(competitorsResults, this)
        binding.recyclerViewCompetitorsResults.adapter = adapter
    }
}