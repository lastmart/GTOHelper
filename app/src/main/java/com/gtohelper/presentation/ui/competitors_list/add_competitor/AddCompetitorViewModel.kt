package com.gtohelper.presentation.ui.competitors_list.add_competitor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormEvent
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCompetitorViewModel @Inject constructor(
    private val repository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitionId = savedStateHandle["competition_id"] ?: 0

    private val formStateChannel = Channel<FormState>()
    val formState = formStateChannel.receiveAsFlow()

    private val mutableForm = MutableStateFlow(CompetitorFormState())
    val form = mutableForm.asStateFlow()

    fun onEvent(event: CompetitorFormEvent) = when (event) {
        CompetitorFormEvent.Submit -> submit()
        is CompetitorFormEvent.UpdateDegree -> mutableForm.update { it.copy(degree = event.value) }
        is CompetitorFormEvent.UpdateGender -> mutableForm.update { it.copy(gender = event.value) }
        is CompetitorFormEvent.UpdateName -> mutableForm.update { it.copy(name = event.value) }
        is CompetitorFormEvent.UpdateNumber -> mutableForm.update { it.copy(number = event.value) }
        is CompetitorFormEvent.UpdateTeamName -> mutableForm.update { it.copy(teamName = event.value) }
    }

    private suspend fun validateInput(): String? {
        val state = mutableForm.value
        if (state.name.isEmpty()) {
            return "Имя участника не может быть пустым"
        }

        if (state.teamName.isEmpty()) {
            return "Название команды не может быть пустым"
        }

        if (state.number == null){
            return "Номер должен быть указан"
        }

        if (repository.getBy(state.number, competitionId) != null){
            return "Участник с таким номером уже существует"
        }

        return null
    }

    private fun submit() {

        viewModelScope.launch {
            formStateChannel.send(FormState.FormSubmittingState)
        }

        val competitor = Competitor(
            id = 0,
            number = form.value.number ?: 0,
            name = form.value.name,
            gender = form.value.gender,
            degree = form.value.degree,
            teamName = form.value.teamName,
            competitionId = competitionId,
        )

        viewModelScope.launch {
            val error = validateInput()
            if (error == null) {
                try {
                    repository.create(competitor)
                    formStateChannel.send(FormState.FormSubmittedState)
                } catch (e: Exception) {
                    formStateChannel.send(FormState.FormSubmissionFailedState(e.message.toString()))
                }

            } else {
                formStateChannel.send(FormState.FormSubmissionFailedState(error))
            }
        }
    }
}

