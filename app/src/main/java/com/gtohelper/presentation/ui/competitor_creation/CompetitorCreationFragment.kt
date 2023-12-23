package com.gtohelper.presentation.ui.competitor_creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gtohelper.R
import com.gtohelper.databinding.FragmentCompetitorCreationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitorCreationFragment : Fragment() {

    private val viewModel: CompetitorCreationViewModel by viewModels()
    private val binding by lazy { FragmentCompetitorCreationBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.new_competitor)

        return binding.composeView.apply {
            setContent {
                CompetitorCreationScreen(
                    findNavController(),
                    viewModel,
                )
            }
        }
    }
}
