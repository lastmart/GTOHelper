package com.gtohelper.presentation.ui.disciplines_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.domain.models.Discipline
import com.gtohelper.domain.repository.DisciplineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DisciplinesListViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository
) : ViewModel() {

    private var _disciplinesLiveData = MutableLiveData<List<Discipline>>()
    val disciplinesLiveData: LiveData<List<Discipline>> = _disciplinesLiveData

    suspend fun getDisciplines() {
        val disciplines = disciplineRepository.getDisciplines()
        _disciplinesLiveData.postValue(disciplines)
    }

    suspend fun deleteCompetition() {
        // TODO
    }
}