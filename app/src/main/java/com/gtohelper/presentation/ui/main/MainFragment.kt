package com.gtohelper.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.gtohelper.R
import com.gtohelper.data.FakeTables
import com.gtohelper.data.models.TablePreview
import com.gtohelper.databinding.FragmentMainBinding
import com.gtohelper.presentation.OnItemClickListener
import com.gtohelper.presentation.ui.main.adapter.TablePreviewAdapter

class MainFragment : Fragment(), OnItemClickListener<TablePreview> {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: TablePreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        binding = FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainFragmentSearchView.setIconifiedByDefault(false)

        initRecyclerView()
        binding.mainFragmentAddButton.setOnClickListener {
            Toast.makeText(requireContext(), "Add pressed", Toast.LENGTH_SHORT).show()
        }

        binding.mainFragmentSearchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(requireContext(), 2)

        val fakeList = FakeTables.fakeTables
        adapter = TablePreviewAdapter(fakeList, this)

        binding.recyclerViewTablesPreview.layoutManager = layoutManager
        binding.recyclerViewTablesPreview.adapter = adapter
    }
}