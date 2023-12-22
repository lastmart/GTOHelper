package com.gtohelper.presentation.ui.disciplines_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.presentation.ui.mappers.toDisciplinePresentation
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DisciplinesListViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository
) : ViewModel() {

    private var _disciplinesLiveData = MutableLiveData<List<DisciplinePresentation>>()
    val disciplinesLiveData: LiveData<List<DisciplinePresentation>> = _disciplinesLiveData

    suspend fun getDisciplines() {
        val disciplines = disciplineRepository.getSelectedDisciplines()
        _disciplinesLiveData.postValue(disciplines.map { it.toDisciplinePresentation() })
    }

    suspend fun deleteDiscipline(discipline: DisciplinePresentation) {
        disciplineRepository.deleteDisciplineFromSelectedByName(discipline.name)
    }

    suspend fun deleteCompetitionByName(competitionName: String) {
        println("Delete competition $competitionName")
    }
}