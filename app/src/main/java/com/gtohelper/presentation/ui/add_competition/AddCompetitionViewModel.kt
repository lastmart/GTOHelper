package com.gtohelper.presentation.ui.add_competition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competition
import com.gtohelper.domain.repository.CompetitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddCompetitionViewModel @Inject constructor(
    private val competitionRepository: CompetitionRepository,
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddCompetitionUiState());
    val uiState = _uiState.asStateFlow()

    private val _created = MutableStateFlow(false)
    val created = _created.asStateFlow()

    fun onEvent(event: AddCompetitionEvent) {
        when (event) {
            AddCompetitionEvent.SubmitForm -> submit()
            is AddCompetitionEvent.UpdateDescription -> _uiState.update { it.copy(description = event.value) }
            is AddCompetitionEvent.UpdateName -> _uiState.update { it.copy(name = event.value) }
        }
    }

    private fun submit() {
        viewModelScope.launch {
            with(_uiState.value) {
                val competition = Competition(0, name, description)
                competitionRepository.create(competition)
                _created.update { true }
            }
        }
    }
}