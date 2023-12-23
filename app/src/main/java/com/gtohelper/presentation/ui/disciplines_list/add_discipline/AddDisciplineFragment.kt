package com.gtohelper.presentation.ui.disciplines_list.add_discipline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.databinding.FragmentAddDisciplineBinding
import com.gtohelper.presentation.ui.disciplines_list.add_discipline.adapter.DisciplineWithSubDisciplinesAdapter
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import com.gtohelper.presentation.ui.util.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddDisciplineFragment : Fragment(), OnItemClickListener<DisciplinePresentation> {

    companion object {
        fun newInstance() = AddDisciplineFragment()
        const val COMPETITION_ID_ARG = "COMPETITION_ID"
    }

    private lateinit var binding: FragmentAddDisciplineBinding
    private lateinit var adapter: DisciplineWithSubDisciplinesAdapter

    @Inject
    lateinit var viewModelFactory: AddDisciplineViewModel.Factory
    private val viewModel: AddDisciplineViewModel by viewModels {
        AddDisciplineViewModel.provideDisciplinesListViewModelFactory(
            viewModelFactory,
            competitionId
        )
    }

    private var competitionId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDisciplineBinding.inflate(layoutInflater)

        initArgs()
        initRecyclerView()
        initViewModel()

        return binding.root
    }

    private fun initArgs() {
        competitionId = requireArguments().getInt(COMPETITION_ID_ARG)
    }

    private fun initViewModel() {
        viewModel.disciplinesLiveData.observe(viewLifecycleOwner) {
            showDisciplines(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getDisciplines()
        }
    }

    private fun showDisciplines(disciplines: List<DisciplinePresentation>) {
        adapter.setData(disciplines)
    }

    override fun onItemClicked(item: DisciplinePresentation) {
        Toast.makeText(requireContext(), "Дисциплина ${item.name} добавлена", Toast.LENGTH_SHORT)
            .show()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.addDiscipline(item)
            viewModel.getDisciplines()
        }
        findNavController().navigateUp()
    }

    private fun initRecyclerView() {
        adapter = DisciplineWithSubDisciplinesAdapter(listOf(), this)
        binding.disciplinesRecyclerView.adapter = adapter
    }
}