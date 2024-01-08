package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.usecases.sport_results.save_result.SaveSportResultResult
import com.gtohelper.domain.usecases.sport_results.SportResultUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddResultsViewModel @Inject constructor(
    private val sportResultUseCases: SportResultUseCases,
    private val disciplineRepository: DisciplineRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val disciplineName: String = savedStateHandle["discipline_id"] ?: ""
    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    var saveResultState by mutableStateOf<SaveSportResultResult?>(null)
        private set

    val results: Flow<List<SportResultAndCompetitor>> =
        sportResultUseCases.getResultsAndCompetitors(
            competitionId,
            disciplineName,
        ).combine(_searchQuery) { data, query ->
            data.filter { it.competitor.number.toString().contains(query) }
        }

    private val _uiState = MutableStateFlow<AddResultsUiState>(AddResultsUiState.Loading)

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val discipline = disciplineRepository.getBy(disciplineName)
            if (discipline != null) {
                _uiState.update { AddResultsUiState.Loaded(discipline) }
            }
        }
    }

    fun onEvent(event: AddResultsEvent) {
        val state = _uiState.value
        if (state is AddResultsUiState.Loaded) {
            when (event) {
                AddResultsEvent.SaveResult -> saveResult(state)
                is AddResultsEvent.SearchResult -> _searchQuery.update { event.query }
                is AddResultsEvent.UpdateNumber -> _uiState.update { state.copy(number = event.value) }
                is AddResultsEvent.UpdateResult -> _uiState.update { state.copy(result = event.value) }
            }
        }
        if (event is ModifyEvent) {
            saveResultState = null
        }
    }

    private fun clearResult(state: AddResultsUiState.Loaded) {
        _uiState.update {
            state.copy(result = 0, number = 0)
        }
    }

    private fun saveResult(state: AddResultsUiState.Loaded) {
        viewModelScope.launch {
            val result = sportResultUseCases.saveSportResult(
                state.number,
                state.result,
                competitionId,
                disciplineName
            )
            saveResultState = result
            if (result is SaveSportResultResult.Success) {
                clearResult(state)
            }
        }
    }
}