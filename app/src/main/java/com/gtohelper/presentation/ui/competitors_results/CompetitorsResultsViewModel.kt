package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.data.database.sport_result.SportResultDao
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
    val sportResultDao: SportResultDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0

    /*val uiState: StateFlow<CompetitorsResultsUIState> = flow {


        val competitorsSportResults = competitorResultsRepository.getCompetitorsWithSportResults(competitionId)
            .collect{
                println("Collected:")
                println(it)
            }


        emit(
            CompetitorsResultsUIState(
                competitorsResults = listOf(
                    Competitor(
                        id = 7,
                        number = 33,
                        competitionId = 2,
                        name = "Хруст Дмит Ром",
                        gender = Gender.MALE,
                        teamName = "Кроссфит",
                        degree = 6
                    ) to 350,

                    Competitor(
                        id = 8,
                        number = 31,
                        competitionId = 2,
                        name = "Хрусталев Дмитрий Романович",
                        gender = Gender.FEMALE,
                        teamName = "Гандбол",
                        degree = 7
                    ) to 72,

                    Competitor(
                        id = 9,
                        number = 32,
                        competitionId = 2,
                        name = "Хрусталев Дмитрий Романовичabcdefgh",
                        gender = Gender.MALE,
                        teamName = "Вьетнам",
                        degree = 8
                    ) to 9,
                )
            )
        )*/

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
    //.map { competitorsResults ->
    //    CompetitorsResultsUIState(
    //        competitorsResults = competitorsResults.sortedByDescending { it.second }
    //    )
    //}.stateIn(
    //    scope = viewModelScope,
    //    started = SharingStarted.WhileSubscribed(5_000),
    //    initialValue = CompetitorsResultsUIState(listOf())
    //)


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