package com.gtohelper.presentation.ui.competition_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.repository.CompetitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompetitionDetailViewModel @Inject constructor(
    private val repository: CompetitionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("competition_id") ?: 0

    val isDeleted = Channel<Boolean>()

    private val _uiState =
        MutableStateFlow<CompetitionDetailUiState>(CompetitionDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                CompetitionDetailUiState.Loaded(repository.getById(id) ?: return@launch)
            }
        }
    }

    fun delete() {
        val state = _uiState.value
        if (state is CompetitionDetailUiState.Loaded) {
            viewModelScope.launch {
                repository.delete(state.competition)
                isDeleted.send(true)
            }
        }
    }
}