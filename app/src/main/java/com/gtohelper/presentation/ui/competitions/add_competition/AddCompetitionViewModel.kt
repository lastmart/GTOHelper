package com.gtohelper.presentation.ui.competitions.add_competition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.presentation.components.forms.FormState
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

    private val _uiState = MutableStateFlow(AddCompetitionUiState())
    val uiState = _uiState.asStateFlow()

    private val formStateChannel = Channel<FormState>()
    val formState = formStateChannel.receiveAsFlow()

    fun onEvent(event: AddCompetitionEvent) {
        when (event) {
            AddCompetitionEvent.SubmitForm -> submit()
            is AddCompetitionEvent.UpdateDescription -> _uiState.update { it.copy(description = event.value) }
            is AddCompetitionEvent.UpdateName -> _uiState.update { it.copy(name = event.value) }
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