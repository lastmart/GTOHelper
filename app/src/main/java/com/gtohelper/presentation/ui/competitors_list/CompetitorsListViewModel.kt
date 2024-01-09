package com.gtohelper.presentation.ui.competitors_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.repository.CompetitorRepository
//import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CompetitorsListViewModel @Inject constructor(
    competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitionId = savedStateHandle["competition_id"] ?: 0
    
    private val _searchQuery = MutableStateFlow("");
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearch(query: String) = _searchQuery.update { query }

    val uiState: StateFlow<CompetitorListUiState> =
        competitorRepository.getCompetitionCompetitors(competitionId)
            .combine(_searchQuery) { data, query ->
                data.filter { it.name.lowercase().contains(query.lowercase()) }
            }.map {
                CompetitorListUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CompetitorListUiState(),
            )
}