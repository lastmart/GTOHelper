package com.gtohelper.presentation.ui.competitor_creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gtohelper.databinding.FragmentCompetitorCreationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitorCreation : Fragment() {

    private val viewModel: CompetitorCreationViewModel by viewModels()
    private val binding by lazy { FragmentCompetitorCreationBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {


        return binding.root
    }
}