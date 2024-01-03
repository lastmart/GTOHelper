package com.gtohelper.presentation.ui.disciplines_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DisciplinesListViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val competitionId = savedStateHandle["competition_id"] ?: 0

    val uiState: StateFlow<DisciplinesListUIState> =
        disciplineRepository
         //   .getSelectedDisciplines()
            .getSelectedDisciplines()
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

//    private var _disciplinesLiveData = MutableLiveData<List<DisciplinePresentation>>()
//    val disciplinesLiveData: LiveData<List<DisciplinePresentation>> = _disciplinesLiveData
//
//    suspend fun getDisciplines() {
//        val disciplines = disciplineRepository.getSelectedDisciplines()
//        _disciplinesLiveData.postValue(disciplines.map { it.toDisciplinePresentation() })
//    }
//
//    suspend fun deleteDiscipline(discipline: DisciplinePresentation) {
//        disciplineRepository.deleteDisciplineFromSelectedByName(discipline.name)
//    }
//
//    suspend fun deleteCompetitionByName(competitionName: String) {
//        println("Delete competition $competitionName")
//    }
}