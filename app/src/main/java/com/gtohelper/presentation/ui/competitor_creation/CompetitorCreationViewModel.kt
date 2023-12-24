package com.gtohelper.presentation.ui.competitor_creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.presentation.components.forms.FormEditingState
import com.gtohelper.presentation.components.forms.FormState
import com.gtohelper.presentation.components.forms.FormSubmissionFailedState
import com.gtohelper.presentation.components.forms.FormSubmittedState
import com.gtohelper.presentation.ui.competition_details.CompetitionDetailsFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitorCreationViewModel @Inject constructor(
    private val competitorRepository: CompetitorRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val competitionId = savedStateHandle[CompetitionDetailsFragment.COMPETITION_ID_ARG] ?: 0

    private val mutableState = MutableStateFlow<FormState<CompetitorCreationForm>>(
        FormEditingState(CompetitorCreationForm())
    )

    val state = mutableState.asStateFlow()

    fun updateGender(gender: Gender) {
        val formState = mutableState.value
        if (formState is FormEditingState<CompetitorCreationForm>) {
            mutableState.update { formState.copy(mutableState.value.form.copy(gender = gender)) }
        }
    }

    fun updateName(name: String) {
        val formState = mutableState.value
        if (formState is FormEditingState<CompetitorCreationForm>) {
            mutableState.update { formState.copy(mutableState.value.form.copy(name = name)) }
        }
    }

    fun updateTeamName(teamName: String) {
        val formState = mutableState.value
        if (formState is FormEditingState<CompetitorCreationForm>) {
            mutableState.update { formState.copy(mutableState.value.form.copy(teamName = teamName)) }
        }
    }

    fun updateDegree(degree: Int) {
        val formState = mutableState.value
        if (formState is FormEditingState) {
            mutableState.update { formState.copy(mutableState.value.form.copy(degree = degree)) }
        }
    }

    fun updateNumber(number: Int) {
        val formState = mutableState.value
        if (formState is FormEditingState) {
            mutableState.update { formState.copy(mutableState.value.form.copy(number = number)) }
        }
    }

    private fun validate(): String? {
        return null
    }

    fun submit() {
        val competitor = Competitor(
            0,
            name = mutableState.value.form.name,
            gender = mutableState.value.form.gender,
            degree = mutableState.value.form.degree,
            teamName = mutableState.value.form.teamName,
            competitionId = competitionId,
            number = mutableState.value.form.number,
        )
        val error = validate()
        if (error == null) {
            viewModelScope.launch {
                competitorRepository.create(competitor)
                mutableState.update { FormSubmittedState(mutableState.value.form) }
            }
        } else {
            mutableState.update { FormSubmissionFailedState(mutableState.value.form, error) }
        }
    }
}

