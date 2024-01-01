package com.gtohelper.presentation.ui.disciplines_list.add_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gtohelper.domain.repository.CompetitorResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddResultsViewModel @Inject constructor(
    private val repository: CompetitorResultsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

//    private val disciplineId = savedStateHandle[DISCIPLINE_ID]
//    private val competitionId = savedStateHandle[COMPETITION_ID]

    fun onEvent(event: AddResultsEvent) {
        when (event) {
            AddResultsEvent.ClearResult -> TODO()
            AddResultsEvent.SaveResult -> TODO()
            is AddResultsEvent.SearchResult -> TODO()
            is AddResultsEvent.UpdateNumber -> TODO()
            is AddResultsEvent.UpdateResult -> TODO()
        }
    }
}