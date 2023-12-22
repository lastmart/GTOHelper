package com.gtohelper.presentation.ui.competition_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gtohelper.R
import com.gtohelper.databinding.FragmentCompetitionDetailsBinding
import com.gtohelper.presentation.ui.util.runOnUiThread
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompetitionDetailsFragment : Fragment() {

    private val viewModel: CompetitionDetailsViewModel by viewModels()
    private val binding by lazy { FragmentCompetitionDetailsBinding.inflate(layoutInflater) }


    companion object {
        const val COMPETITION_ID_ARG = "competition_id"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect {
                runOnUiThread {
                    binding.name.text = it.competition?.name.toString()
                    binding.descpription.text = it.competition?.description.toString()
                    (requireActivity() as AppCompatActivity).supportActionBar?.title = it.competition?.name.toString()
                }
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.disciplines.setOnClickListener {
            findNavController().navigate(
                R.id.action_competitionDetailsFragment_to_disciplinesListFragment,
                requireArguments()
            )
        }
        binding.competitors.setOnClickListener {
            findNavController().navigate(
                R.id.action_competitionDetailsFragment_to_competitorsListFragment,
                requireArguments()
            )
        }
        binding.results.setOnClickListener {
            findNavController().navigate(
                R.id.action_competitionDetailsFragment_to_competitorsResultsFragment,
                requireArguments()
            )
        }
    }
}