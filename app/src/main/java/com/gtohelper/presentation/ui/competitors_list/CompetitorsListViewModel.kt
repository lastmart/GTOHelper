package com.gtohelper.presentation.ui.competitors_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitorsListViewModel @Inject constructor(
    private val competitorRepository: CompetitorRepository,
) : ViewModel() {

    //    TODO: Move to constructor and create ViewModel via Factory
    private val competitionId = MutableStateFlow<Int?>(null);

    fun bindCompetitionId(id: Int) = competitionId.update { id }

    private val _searchQuery = MutableStateFlow("");
    val searchQuery = _searchQuery.asStateFlow()

    fun updateSearch(query: String) = _searchQuery.update { query }

    val competitors by lazy {
        competitorRepository.getCompetitionCompetitors(
            competitionId.value ?: 0
        ).combine(
            _searchQuery
        ) { data, filter -> data.filter { it.name.lowercase().contains(filter.lowercase()) } }
    }

    fun create() {
        viewModelScope.launch {
            competitorRepository.create(
                Competitor(
                    0,
                    "Vova",
                    19,
                    Gender.MALE,
                    "Crossfit",
                    1,
                    competitionId.value ?: 0,
                )
            )
        }
    }
}