package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.SportResultAndCompetitor
import com.gtohelper.domain.repository.SportResultRepository
import com.gtohelper.domain.usecases.sport_results.SaveSportResultResult
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
    private val sportResultRepository: SportResultRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val disciplineId: String = savedStateHandle["discipline_id"] ?: ""
    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    var saveResultState by mutableStateOf<SaveSportResultResult?>(null)
        private set

    val results: Flow<List<SportResultAndCompetitor>> =
        sportResultUseCases.getResultsAndCompetitors(
            competitionId,
            disciplineId,
        ).combine(_searchQuery) { data, query ->
            data.filter { it.competitor.number.toString().contains(query) }
        }

    private val _uiState = MutableStateFlow(AddResultsUiState())

    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AddResultsEvent) {
        when (event) {
            AddResultsEvent.SaveResult -> saveResult()
            AddResultsEvent.ClearResult -> clearResult()
            is AddResultsEvent.SearchResult -> _searchQuery.update { event.query }
            is AddResultsEvent.UpdateNumber -> _uiState.update { it.copy(number = event.value) }
            is AddResultsEvent.UpdateResult -> _uiState.update { it.copy(result = event.value) }
        }
    }

    private fun clearResult() {
        _uiState.update {
            it.copy(result = 0, number = 0)
        }
    }

    private fun saveResult() {
        viewModelScope.launch {
            val result = sportResultUseCases.saveSportResult(
                _uiState.value.number,
                _uiState.value.result,
                competitionId,
                disciplineId
            )
            saveResultState = result
        }
    }
}