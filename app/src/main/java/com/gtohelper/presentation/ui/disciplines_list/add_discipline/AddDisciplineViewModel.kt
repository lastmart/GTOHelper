package com.gtohelper.presentation.ui.disciplines_list.add_discipline

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

class AddDisciplineViewModel @AssistedInject constructor(
    private val disciplineRepository: DisciplineRepository,
    @Assisted
    private val competitionId: Int
) : ViewModel() {

    private var _disciplinesLiveData = MutableLiveData<List<DisciplinePresentation>>()
    val disciplinesLiveData: LiveData<List<DisciplinePresentation>> = _disciplinesLiveData

    suspend fun getDisciplines() {
        val disciplines = disciplineRepository.getNotSelectedDisciplines(competitionId)
        _disciplinesLiveData.postValue(disciplines.map { it.toDisciplinePresentation() })
    }

    suspend fun addDiscipline(disciplinePresentation: DisciplinePresentation) {
        disciplineRepository.addDisciplineToSelected(disciplinePresentation, competitionId)
    }

    @AssistedFactory
    interface Factory {
        fun create(competitionId: Int): AddDisciplineViewModel
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