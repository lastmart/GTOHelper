package com.gtohelper.presentation.ui.competition_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompetitionDetailViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepository,
    competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("competition_id") ?: 0

    val isDeleted = Channel<Boolean>()

    val uiState : StateFlow<CompetitionDetailUiState> = competitorRepository.getCompetitionCompetitorCount(id)
        .map {
            val competition = competitionRepository.getById(id)
            if (competition != null) {
                CompetitionDetailUiState.Loaded(competition, it)
            } else {
                CompetitionDetailUiState.LoadFailed
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = CompetitionDetailUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )


    fun delete() {
        val state = uiState.value
        if (state is CompetitionDetailUiState.Loaded) {
            viewModelScope.launch {
                competitionRepository.delete(state.competition)
                isDeleted.send(true)
            }
        }
    }
}