package com.gtohelper.presentation.ui.disciplines_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.presentation.ui.mappers.toDisciplinePresentation
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DisciplinesListViewModel @AssistedInject constructor(
    private val disciplineRepository: DisciplineRepository,
    @Assisted
    private val competitionId: Int
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

    @AssistedFactory
    interface Factory {
        fun create(competitionId: Int): DisciplinesListViewModel
    }

    companion object {
        fun provideDisciplinesListViewModelFactory(
            factory: Factory,
            competitionId: Int
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(competitionId) as T
                }
            }
        }
    }
}