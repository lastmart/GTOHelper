package com.gtohelper.presentation.ui.main

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
import com.gtohelper.data.models.TablePreview
import com.gtohelper.databinding.FragmentMainBinding
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.main.adapter.TablePreviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainFragment : Fragment(), OnItemClickListener<TablePreview> {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainFragmentViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: TablePreviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        initViewModel()

        initSearchView()

        binding.mainFragmentAddButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add pressed", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onItemClicked(item: TablePreview) {
        Toast.makeText(requireContext(), item.toString(), Toast.LENGTH_SHORT).show()

        val argsBundle = Bundle().apply {
            putString(TablePreviewDetailsDialogFragment.TITLE_ARG, item.title)
            putString(TablePreviewDetailsDialogFragment.DESCRIPTION_ARG, item.description)
        }

        findNavController().navigate(
            R.id.action_mainFragment_to_tablePreviewDetailsDialogFragment,
            argsBundle
        )
    }

    private fun initViewModel() {
        viewModel.tablesLiveData.observe(viewLifecycleOwner) {
            showTables(it)
        }

        lifecycleScope.launch {
            viewModel.getTables()
        }
    }

    private fun initSearchView() {
        binding.mainFragmentSearchView.setIconifiedByDefault(false)

        binding.mainFragmentSearchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()

                lifecycleScope.launch {
                    query?.let {
                        viewModel.searchTablesByName(it)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    newText?.let {
                        viewModel.searchTablesByName(it)
                    } ?: viewModel.getTables()
                }

                return true
            }
        })
    }

    private fun showTables(tables: List<TablePreview>) {
        adapter = TablePreviewAdapter(tables, this)
        binding.recyclerViewTablesPreview.adapter = adapter
    }
}