package com.gtohelper.presentation.ui.disciplines_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gtohelper.databinding.FragmentDisciplinesListBinding
import com.gtohelper.domain.models.Discipline
import com.gtohelper.presentation.ui.disciplines_list.adapter.DisciplineAdapter
import com.gtohelper.presentation.ui.util.OnItemClickListener
import com.gtohelper.presentation.ui.util.OnItemLongClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisciplinesListFragment : Fragment(),
    OnItemClickListener<Discipline>, OnItemLongClickListener<Discipline> {

    companion object {
        fun newInstance() = DisciplinesListFragment()
    }

    private val viewModel: DisciplinesListViewModel by viewModels()
    private lateinit var binding: FragmentDisciplinesListBinding
    private lateinit var adapter: DisciplineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisciplinesListBinding.inflate(layoutInflater)

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.disciplinesLiveData.observe(viewLifecycleOwner) {
            showDisciplines(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getDisciplines()
        }
    }

    private fun showDisciplines(disciplines: List<Discipline>) {
        adapter = DisciplineAdapter(disciplines, this, this)
        binding.disciplinesRecyclerView.adapter = adapter
    }

    override fun onItemClicked(item: Discipline) {
        println("$item clicked")
    }

    override fun onItemLongClicked(item: Discipline): Boolean {
        println("$item long clicked")
        Toast.makeText(requireContext(), "Remove ${item.name}?", Toast.LENGTH_SHORT).show()
        return true
    }
}