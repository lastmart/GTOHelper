package com.gtohelper.presentation.ui.disciplines_list.add_discipline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.presentation.ui.models.DisciplinePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddDisciplineViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository
) : ViewModel() {

    private var _disciplinesLiveData = MutableLiveData<List<DisciplinePresentation>>()
    val disciplinesLiveData: LiveData<List<DisciplinePresentation>> = _disciplinesLiveData

    suspend fun getDisciplines() {
        val disciplines = disciplineRepository.getDisciplines()
        _disciplinesLiveData.postValue(disciplines.map {
            DisciplinePresentation(it.imageResource, it.name, it.subDisciplines, false)
        })
    }
}