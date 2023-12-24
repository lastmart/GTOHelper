package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.data.models.ExcelReader
import com.gtohelper.domain.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCompetitorsFromTableViewModel @Inject constructor(
    private val competitorRepository: CompetitorRepository,
) : ViewModel() {

    fun addFromExcelTable(excelPath : String) {
        val reader = ExcelReader()

        viewModelScope.launch {
            reader.getCompetitorList(excelPath, 1).forEach {
                competitorRepository.create(it)
            }
        }
    }
}