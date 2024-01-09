package com.gtohelper.presentation.ui.competitions.edit_competition

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competitions.components.forms.CompetitionFormEvent
import com.gtohelper.presentation.ui.competitions.components.forms.CompetitionFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCompetitionViewModel @Inject constructor(
    private val repository: CompetitionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitionId: Int  = savedStateHandle["competition_id"] ?: 0

    private val _uiState = MutableStateFlow(CompetitionFormState())
    val uiState = _uiState.asStateFlow()

    private val formStateChannel = Channel<FormState>()
    val formState = formStateChannel.receiveAsFlow()

    private var initialCompetition: Competition? = null

    init {
        viewModelScope.launch {
            val competition = repository.getById(competitionId)
            initialCompetition = competition

            if (competition != null){
                _uiState.update {
                    it.copy(
                        name=competition.name,
                        description = competition.description
                    )
                }
            }
        }
    }


    fun onEvent(event: CompetitionFormEvent) {
        when (event) {
            CompetitionFormEvent.SubmitForm -> submit()
            is CompetitionFormEvent.UpdateDescription -> _uiState.update { it.copy(description = event.value) }
            is CompetitionFormEvent.UpdateName -> _uiState.update { it.copy(name = event.value) }
        }
    }

    private fun validateCompetition(): String? {

        if (_uiState.value.name.isEmpty()) {
            return "Название не может быть пустым"
        }

        return null
    }

    private fun submit() {

        viewModelScope.launch {
            val state = _uiState.value
            val validationError = validateCompetition()
            if (validationError == null) {
                val competition = Competition(competitionId, state.name, state.description)
                try {
                    repository.update(competition)
                    formStateChannel.send(FormState.FormSubmittedState)
                } catch (e: Exception) {
                    formStateChannel.send(FormState.FormSubmissionFailedState(e.message.toString()))
                }
            } else {
                formStateChannel.send(FormState.FormSubmissionFailedState(validationError))
            }
        }
    }
}