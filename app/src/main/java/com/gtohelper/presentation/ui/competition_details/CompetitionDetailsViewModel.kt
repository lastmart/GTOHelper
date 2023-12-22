package com.gtohelper.presentation.ui.competition_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment.Companion.COMPETITION_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompetitionDetailsViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompetitionDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>(COMPETITION_ID_ARG) ?: 0
            val competition = competitionRepository.getById(id)
            _uiState.update { it.copy(competition = competition) }
        }
    }

    data class CompetitionDetailsUiState(
        val competition: Competition? = null,
    )
}