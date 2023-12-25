package com.gtohelper.presentation.ui.competitors_list.add_competitor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment
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
    private val competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitionId = savedStateHandle[CompetitionDetailsFragment.COMPETITION_ID_ARG] ?: 0

    private val formStateChannel = Channel<FormState>()
    val formState = formStateChannel.receiveAsFlow()

    private val mutableForm = MutableStateFlow(AddCompetitorFormState())
    val form = mutableForm.asStateFlow()

    fun onEvent(event: AddCompetitorEvent) = when (event) {
        AddCompetitorEvent.Submit -> submit()
        is AddCompetitorEvent.UpdateDegree -> mutableForm.update { it.copy(degree = event.value) }
        is AddCompetitorEvent.UpdateGender -> mutableForm.update { it.copy(gender = event.value) }
        is AddCompetitorEvent.UpdateName -> mutableForm.update { it.copy(name = event.value) }
        is AddCompetitorEvent.UpdateNumber -> mutableForm.update { it.copy(number = event.value) }
        is AddCompetitorEvent.UpdateTeamName -> mutableForm.update { it.copy(teamName = event.value) }
    }

    private fun validate(): String? {
        if (mutableForm.value.name.isEmpty()) {
            return "Имя участника не может быть пустым"
        }

        if (mutableForm.value.teamName.isEmpty()) {
            return "Название команды не может быть пустым"
        }

        return null
    }

    private fun submit() {

        viewModelScope.launch {
            formStateChannel.send(FormState.FormSubmittingState)
        }

        val competitor = Competitor(
            0,
            name = form.value.name,
            gender = form.value.gender,
            degree = form.value.degree,
            teamName = form.value.teamName,
            competitionId = competitionId,
            number = form.value.number,
        )

        val error = validate()

        viewModelScope.launch {
            if (error == null) {
                try {
                    competitorRepository.create(competitor)
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

