package com.gtohelper.presentation.ui.disciplines_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.presentation.ui.mappers.toDisciplinePresentation
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisciplinesListViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val competitionId = savedStateHandle["competition_id"] ?: 0
    private var disciplineToDelete: DisciplinePresentation? = null
    var isDialogShown by mutableStateOf(false)
        private set

    val uiState: StateFlow<DisciplinesListUIState> =
        disciplineRepository
            .getSelectedDisciplines(competitionId)
            .map { disciplines ->
                DisciplinesListUIState(
                    disciplines.map { it.toDisciplinePresentation() }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DisciplinesListUIState()
            )

    fun onDisciplineLongPressed(discipline: DisciplinePresentation) {
        disciplineToDelete = discipline
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    fun deleteDiscipline() {
        viewModelScope.launch(Dispatchers.IO) {
            disciplineToDelete?.let {
                disciplineRepository.deleteDisciplineFromSelectedByName(it.name, competitionId)
            }
        }
    }
//
//    suspend fun deleteCompetitionByName(competitionName: String) {
//        println("Delete competition $competitionName")
//    }
}