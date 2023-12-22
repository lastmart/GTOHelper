package com.gtohelper.presentation.ui.competitor_creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import com.gtohelper.domain.repository.CompetitorRepository
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

    private val _uiState = MutableStateFlow(CompetitorCreationUiState(CompetitorForm()))

    val uiState = _uiState.asStateFlow()

    fun updateForm(form: CompetitorForm) {
        _uiState.update { it.copy(form = form) }
    }

    fun updateGender(index: Int) {
        _uiState.update { it.copy(form = it.form.copy(gender = Gender.entries[index])) }
    }

    fun submit() {
        with(_uiState.value.form) {
            val competitor = Competitor(
                0,
                name = this.name,
                gender = this.gender,
                degree = this.degree,
                teamName = this.teamName,
                competitionId = competitionId,
                number = this.number
            )

            viewModelScope.launch {
                competitorRepository.create(competitor)
            }
        }
    }

    data class CompetitorCreationUiState(
        val form: CompetitorForm,
    )
}