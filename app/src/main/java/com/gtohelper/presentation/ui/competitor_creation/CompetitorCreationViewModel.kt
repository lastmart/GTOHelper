package com.gtohelper.presentation.ui.competitor_creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.presentation.components.forms.FormEditingState
import com.gtohelper.presentation.components.forms.FormViewModel
import com.gtohelper.presentation.components.forms.InputState
import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitorCreationViewModel @Inject constructor(
    private val competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : FormViewModel<CompetitorCreationForm>(CompetitorCreationForm()) {

    private val competitionId = savedStateHandle[CompetitionDetailsFragment.COMPETITION_ID_ARG] ?: 0

    fun updateGender(gender: Gender) {
        val formState = mutableState.value
        if (formState is FormEditingState<CompetitorCreationForm>) {
            mutableState.update { formState.copy(form.copy(gender = gender)) }
        }
    }

    fun updateName(name: String) {
        val formState = mutableState.value
        if (formState is FormEditingState<CompetitorCreationForm>) {
            mutableState.update { formState.copy(form.copy(name = name)) }
        }
    }

    fun updateTeamName(teamName: String) {
        val formState = mutableState.value
        if (formState is FormEditingState<CompetitorCreationForm>) {
            mutableState.update { formState.copy(form.copy(teamName = teamName)) }
        }
    }

    fun updateDegree(degree: Int) {
        val formState = mutableState.value
        if (formState is FormEditingState) {
            mutableState.update { formState.copy(form.copy(degree = degree)) }
        }
    }

    fun updateNumber(number: Int) {
        val formState = mutableState.value
        if (formState is FormEditingState) {
            mutableState.update { formState.copy(form.copy(number = number)) }
        }
    }

    override suspend fun sendData() {
        val competitor = Competitor(
            0,
            name = form.name,
            gender = form.gender,
            degree = form.degree,
            teamName = form.teamName,
            competitionId = competitionId,
            number = form.number,
        )
        viewModelScope.launch {
            competitorRepository.create(competitor)
        }
    }
}

