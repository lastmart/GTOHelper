package com.gtohelper.presentation.ui.competition_creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.databinding.FragmentCompetitionCreationBinding
import com.gtohelper.presentation.ui.util.runOnUiThread
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompetitionCreationFragment : Fragment() {

    private val binding by lazy { FragmentCompetitionCreationBinding.inflate(layoutInflater) }
    private val viewModel: CompetitionCreationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                if (it.created) {
                    runOnUiThread {
                        findNavController().navigateUp()
                    }
                }
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createTableButton.setOnClickListener { viewModel.createTable() }
        binding.tableNameInput.addTextChangedListener {
            viewModel.updateCompetition(
                viewModel.uiState.value.competition.copy(
                    name = it.toString()
                )
            )
        }

        binding.tableDescriptionInput.addTextChangedListener {
            viewModel.updateCompetition(
                viewModel.uiState.value.competition.copy(
                    description = it.toString()
                )
            )
        }
    }
}