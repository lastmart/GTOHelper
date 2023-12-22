package com.gtohelper.presentation.ui.competitor_creation

import androidx.lifecycle.ViewModel
import com.gtohelper.domain.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompetitorCreationViewModel @Inject constructor(
    private val competitorRepository: CompetitorRepository,
) : ViewModel() {

    // TODO: Implement competitor creation
    fun create() {

    }
}