package com.gtohelper.presentation.ui.competitors_results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.databinding.FragmentCompetitorsResultsBinding
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.competitors_results.adapter.CompetitorResultsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompetitorsResultsFragment : Fragment(), OnItemClickListener<CompetitorResults> {

    companion object {
        fun newInstance() = CompetitorsResultsFragment()
    }

    private val viewModel: CompetitorsResultsViewModel by viewModels()
    private lateinit var binding: FragmentCompetitorsResultsBinding
    private lateinit var adapter: CompetitorResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompetitorsResultsBinding.inflate(layoutInflater)

        println("Before database")

        /*lifecycleScope.launch(Dispatchers.IO) {
            println(1)

            val dao = appDatabase.getCompetitorDao()

            println(11)

            dao.upsertCompetitor(
                CompetitorEntity(2, "Ivan", 43, Gender.MALE, "Crossfit", 5)
            )

            println(2)

            appDatabase.getSportDao().upsertSport(
                SportEntity("Run100m", listOf(11, 22, 33, 44))
            )

            println(appDatabase.getSportDao().getSportCompetitors("Run100m"))

        }
*/
        println("After database")


        initSearchView()
        initViewModel()

        return binding.root
    }

    override fun onItemClicked(item: CompetitorResults) {
        println("Id: ${item.id}")
        //   Toast.makeText(requireContext(), item.id, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
      //  viewModel = ViewModelProvider(this)[CompetitorsResultsViewModel::class.java]

        println("InitViewModel in fragment")

        viewModel.competitorsResultsLiveData.observe(viewLifecycleOwner) {
            showCompetitorsResults(it)
        }

        println("Before get comp results")

        lifecycleScope.launch(Dispatchers.Default) {
            viewModel.getCompetitorsResults()
        }

        println("After get comp results")

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