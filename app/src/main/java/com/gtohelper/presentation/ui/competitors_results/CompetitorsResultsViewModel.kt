package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompetitorsResultsViewModel @Inject constructor(
    val competitorResultsRepository: CompetitorResultsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val uiState: StateFlow<CompetitorsResultsUIState> =
        competitorResultsRepository.getCompetitorsWithSportResults(competitionId)
            .combine(_searchQuery) { list, query ->

                if (query.isBlank())
                    return@combine list

                val newList = list.filter { (competitor, result) ->
                    doesCompetitorMatchQuery(competitor, query)
                }

                return@combine newList
            }
            .map { competitorsResults ->
                CompetitorsResultsUIState(
                    competitorsResults = competitorsResults.sortedByDescending { it.second }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CompetitorsResultsUIState(listOf())
            )

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun doesCompetitorMatchQuery(competitor: Competitor, query: String): Boolean {
        return when {
            competitor.name.contains(query, ignoreCase = true) -> true
            competitor.number == query.toIntOrNull() -> true
            competitor.teamName.contains(query, ignoreCase = true) -> true
            else -> false
        }
    }
}