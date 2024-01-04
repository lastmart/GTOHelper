package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.repository.SportResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddResultsViewModel @Inject constructor(
    private val repository: SportResultRepository,
    private val disciplineRepository: DisciplineRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val disciplineId: String = savedStateHandle["discipline_id"] ?: ""
    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<AddResultsUiState>(AddResultsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            
        }
    }


    fun onEvent(event: AddResultsEvent) {
        val a = repository.getCompetitionDisciplineResults(competitionId, disciplineId)


        val state = _uiState.value
        when (event) {
            AddResultsEvent.ClearResult -> clearResult(state)
            AddResultsEvent.SaveResult -> saveResult(state)
            is AddResultsEvent.SearchResult -> updateSearch(event.query)
            is AddResultsEvent.UpdateNumber -> updateNumber(state)
            is AddResultsEvent.UpdateResult -> updateResult(state)
        }
    }

    private fun updateResult(state: AddResultsUiState) {

    }

    private fun updateNumber(state: AddResultsUiState) {

    }

    private fun updateSearch(query: String) {

    }

    private fun clearResult(state: AddResultsUiState) {
        if (state is AddResultsUiState.Loaded) {
            _uiState.update { state.copy(currentNumber = 0, currentResult = 0) }
        }
    }

    private fun saveResult(state: AddResultsUiState) {
        if (state is AddResultsUiState.Loaded) {
            val result = SportResult(
                sportName = disciplineId,
                competitionId = competitionId,
                competitorNumber = state.currentNumber,
                value = state.currentResult,
            )

            viewModelScope.launch {
                repository.create(result)
            }
        }
    }
}