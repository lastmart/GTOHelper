package com.gtohelper.presentation.ui.competitions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.repository.CompetitionRepository
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
class CompetitionListViewModel @Inject constructor(
    repository: CompetitionRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val uiState: StateFlow<CompetitionListUiState> =
        repository.getAll()
            .combine(_searchQuery) { data, query ->
                data.filter { it.name.lowercase().contains(query.lowercase()) }
            }.map {
                CompetitionListUiState.Loaded(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CompetitionListUiState.Loading,
            )


    fun updateSearch(name: String) = _searchQuery.update { name }
}