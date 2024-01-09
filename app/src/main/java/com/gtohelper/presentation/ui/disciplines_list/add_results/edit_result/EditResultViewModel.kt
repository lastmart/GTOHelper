package com.gtohelper.presentation.ui.disciplines_list.add_results.edit_result

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.SportResult
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.repository.SportResultRepository
import com.gtohelper.domain.usecases.sport_result.EditSportResultResult
import com.gtohelper.domain.usecases.sport_result.SportResultUseCases
import com.gtohelper.presentation.ui.disciplines_list.add_results.ModifyEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditResultViewModel @Inject constructor(
    private val sportResultUseCases: SportResultUseCases,
    private val sportResultRepository: SportResultRepository,
    private val disciplineRepository: DisciplineRepository,
    private val competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val disciplineId: Int = savedStateHandle["discipline_id"] ?: 0
    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0
    private val sportResultId: Int = savedStateHandle["sport_result_id"] ?: 0

    var editResultState by mutableStateOf<EditSportResultResult?>(null)
        private set


    private val _uiState = MutableStateFlow<EditResultUiState>(EditResultUiState.Loading)

    val uiState = _uiState.asStateFlow()

    private lateinit var oldSportResult: SportResult

    init {
        viewModelScope.launch {
            val sportResult = sportResultRepository.getBy(sportResultId) ?: return@launch
            oldSportResult = sportResult
            val discipline = disciplineRepository.getBy(disciplineId) ?: return@launch
            val competitor = competitorRepository.getBy(sportResult.competitorId) ?: return@launch
            _uiState.update {
                EditResultUiState.Loaded(
                    result = sportResult,
                    competitor = competitor,
                    pointType = discipline.type,
                )
            }
        }
    }


    fun onEvent(event: EditResultEvent) {
        val state = _uiState.value
        if (state is EditResultUiState.Loaded) {
            when (event) {
                EditResultEvent.SubmitEdit -> editResult(state)
                EditResultEvent.SubmitDelete -> deleteResult(state)
                is EditResultEvent.UpdateNumber -> _uiState.update {
                    state.copy(
                        competitor = state.competitor.copy(
                            number = event.value
                        )
                    )
                }

                is EditResultEvent.UpdateResult -> _uiState.update {
                    state.copy(
                        result = state.result.copy(
                            value = event.value
                        )
                    )
                }

            }
        }
        if (event is ModifyEvent) {
            editResultState = null
        }
    }

    private fun deleteResult(state: EditResultUiState.Loaded) {
        viewModelScope.launch {
            sportResultUseCases.deleteSportResult(state.result)
            _uiState.update { EditResultUiState.Deleted }
        }
    }

    private fun editResult(state: EditResultUiState.Loaded) {
        viewModelScope.launch {
            editResultState = sportResultUseCases.editSportResult(
                state.competitor.number,
                state.result.value,
                state.result.id ?: 0,
                competitionId,
            )
        }
    }
}