package com.gtohelper.presentation.ui.competitors_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.models.fromAge
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitorsListViewModel @Inject constructor(
    competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitionId = savedStateHandle[CompetitionDetailsFragment.COMPETITION_ID_ARG] ?: 0
    private val _searchQuery = MutableStateFlow("");
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearch(query: String) {
        _searchQuery.update { query }
    }

    val competitors = competitorRepository.getCompetitionCompetitors(competitionId).combine(
        _searchQuery
    ) { data, filter -> data.filter { it.name.lowercase().contains(filter.lowercase()) } }
}