package com.gtohelper.presentation.ui.disciplines_list.add_discipline

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
class AddDisciplineViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val competitionId = savedStateHandle["competition_id"] ?: 0

    val uiState: StateFlow<AddDisciplineUIState> =
        disciplineRepository.getNotSelectedDisciplines(competitionId)
            .map { disciplines ->
                AddDisciplineUIState(
                    disciplines.map { it.toDisciplinePresentation() }
                )
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AddDisciplineUIState(
                    listOf()
                )
            )

    //    private var _disciplinesLiveData = MutableLiveData<List<DisciplinePresentation>>()
//    val disciplinesLiveData: LiveData<List<DisciplinePresentation>> = _disciplinesLiveData
//
//    suspend fun getDisciplines() {
//        val disciplines = disciplineRepository.getNotSelectedDisciplines()
//        _disciplinesLiveData.postValue(disciplines.map { it.toDisciplinePresentation() })
//    }
//
    fun addDiscipline(disciplinePresentation: DisciplinePresentation) {
        viewModelScope.launch(Dispatchers.IO) {
            disciplineRepository.addDisciplineToSelected(disciplinePresentation, competitionId)
        }
    }
}