package com.gtohelper.presentation.ui.disciplines_list.delete_discpiline

import androidx.lifecycle.ViewModel
import com.gtohelper.domain.repository.DisciplineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteDisciplineViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository
) : ViewModel() {

}