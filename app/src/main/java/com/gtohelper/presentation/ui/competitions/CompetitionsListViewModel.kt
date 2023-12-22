package com.gtohelper.presentation.ui.competitions

import androidx.lifecycle.ViewModel
import com.gtohelper.domain.repository.CompetitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CompetitionsListViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepository,
) : ViewModel() {


    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val competitions = competitionRepository.getAll().combine(
        searchQuery
    ) { data, query ->
        data.filter {
            it.name.lowercase(Locale.ROOT).contains(
                query.lowercase(
                    Locale.ROOT
                )
            )
        }
    }

    fun searchCompetitions(name: String) = _searchQuery.update { name }
}