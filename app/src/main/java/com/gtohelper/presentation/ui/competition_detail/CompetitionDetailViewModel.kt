package com.gtohelper.presentation.ui.competition_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompetitionDetailViewModel @Inject constructor(
    repository: CompetitionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompetitionDetailUiState())
    val uiState = _uiState

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("competition_id") ?: 0
            val competition = repository.getById(id)
            _uiState.update { it.copy(competition = competition) }
        }
    }
}