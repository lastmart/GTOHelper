package com.gtohelper.presentation.ui.competitions.add_competition

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
class AddCompetitionViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompetitionFormState())
    val uiState = _uiState.asStateFlow()

    private val formStateChannel = Channel<FormState>()
    val formState = formStateChannel.receiveAsFlow()

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
                val competition = Competition(0, state.name, state.description)
                try {
                    competitionRepository.create(competition)
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