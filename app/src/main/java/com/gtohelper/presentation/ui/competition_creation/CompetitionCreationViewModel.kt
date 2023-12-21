package com.gtohelper.presentation.ui.competition_creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompetitionCreationViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepository,
) : ViewModel() {


    private val _uiState = MutableStateFlow(TableCreationUiState());

    val uiState = _uiState.asStateFlow()

    fun updateCompetition(competition: Competition) = _uiState.update { it.copy(competition = competition) }

    fun createTable() {
        viewModelScope.launch {
            _uiState.update { it.copy(created = false) }
            competitionRepository.create(_uiState.value.competition)
            _uiState.update { it.copy(created = true) }
        }
    }

    data class TableCreationUiState(
        val competition: Competition = Competition(0, "", ""),
        val created: Boolean = false,
    )
}