package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.repository.SportResultRepository
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
    private val sportResultRepository: SportResultRepository,
    private val disciplineRepository: DisciplineRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val disciplineId: String = savedStateHandle["discipline_id"] ?: ""
    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0

    private val _searchQuery = MutableStateFlow("")

    val searchQuery = _searchQuery.asStateFlow()

    val results: Flow<List<SportResult>> = sportResultRepository.getCompetitionDisciplineResults(
        competitionId,
        disciplineId,
    ).combine(_searchQuery){
        data, query -> data.filter { it.competitorNumber.toString().contains(query) }
    }

    private val _uiState = MutableStateFlow(AddResultsUiState())

    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AddResultsEvent) {
        when (event) {
            AddResultsEvent.SaveResult -> saveResult()
            AddResultsEvent.ClearResult -> _uiState.update {
                it.copy(
                    currentResult = 0, currentNumber = 0
                )
            }

            is AddResultsEvent.SearchResult -> _searchQuery.update { event.query }
            is AddResultsEvent.UpdateNumber -> _uiState.update { it.copy(currentNumber = event.value) }
            is AddResultsEvent.UpdateResult -> _uiState.update { it.copy(currentResult = event.value) }
        }
    }

    private fun saveResult() {
        val result = SportResult(
            sportName = disciplineId,
            competitionId = competitionId,
            competitorNumber = _uiState.value.currentNumber,
            value = _uiState.value.currentResult,
        )

        viewModelScope.launch {
            sportResultRepository.create(result)
        }
    }
}