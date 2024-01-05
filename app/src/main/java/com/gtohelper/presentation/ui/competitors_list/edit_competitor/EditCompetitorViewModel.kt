package com.gtohelper.presentation.ui.competitors_list.edit_competitor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.presentation.components.forms.FormState
//import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment.Companion.COMPETITION_ID_ARG
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormEvent
import com.gtohelper.presentation.ui.competitors_list.components.forms.CompetitorFormState
//import com.gtohelper.presentation.ui.competitors_list.edit_competitor.EditCompetitorFragment.Companion.COMPETITOR_ID_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditCompetitorViewModel @Inject constructor(
    private val repository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitorId = savedStateHandle["competitor_id"] ?: 0

    private val formStateChannel = Channel<FormState>()
    val formState = formStateChannel.receiveAsFlow()

    val isDeleted = Channel<Boolean>()

    private val mutableForm = MutableStateFlow(CompetitorFormState())
    val form = mutableForm.asStateFlow()

    private var initialCompetitor: Competitor? = null

    init {
        viewModelScope.launch {
            val competitor = repository.getById(competitorId)
            initialCompetitor = competitor
            if (competitor != null) {
                mutableForm.update {
                    it.copy(
                        name = competitor.name,
                        teamName = competitor.teamName,
                        gender = competitor.gender,
                        number = competitor.number,
                        degree = competitor.degree
                    )
                }
            }
        }
    }

    fun deleteCompetitor() {
        viewModelScope.launch {
            repository.delete(initialCompetitor ?: return@launch)
            isDeleted.send(true)
        }
    }

    fun onEvent(event: CompetitorFormEvent) = when (event) {
        CompetitorFormEvent.Submit -> submit()
        is CompetitorFormEvent.UpdateDegree -> mutableForm.update { it.copy(degree = event.value) }
        is CompetitorFormEvent.UpdateGender -> mutableForm.update { it.copy(gender = event.value) }
        is CompetitorFormEvent.UpdateName -> mutableForm.update { it.copy(name = event.value) }
        is CompetitorFormEvent.UpdateNumber -> mutableForm.update { it.copy(number = event.value) }
        is CompetitorFormEvent.UpdateTeamName -> mutableForm.update { it.copy(teamName = event.value) }
    }

    private suspend fun validateCompetitorForm(): String? {
        // TODO: Create UseCases For Validation
        val form = mutableForm.value
        if (form.name.isEmpty()) {
            return "Имя участника не может быть пустым"
        }

        if (form.teamName.isEmpty()) {
            return "Название команды не может быть пустым"
        }

        if (form.number == null) {
            return "Номер должен быть указан"
        }


        if (form.number == initialCompetitor?.number) {
            return null
        }

        if (repository.getByNumberInCompetition(
                form.number, initialCompetitor?.competitionId ?: 0
            ) != null
        ) {
            return "Участник с таким номером уже существует"
        }


        return null
    }

    private fun submit() {
        viewModelScope.launch {
            formStateChannel.send(FormState.FormSubmittingState)
        }

        val competitor = Competitor(
            id = competitorId,
            number = form.value.number ?: 0,
            competitionId = initialCompetitor?.competitionId ?: 0,
            name = form.value.name,
            gender = form.value.gender,
            degree = form.value.degree,
            teamName = form.value.teamName,
        )

        viewModelScope.launch {
            val error = validateCompetitorForm()
            if (error == null) {
                try {
                    repository.update(competitor)
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