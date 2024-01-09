package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.ExcelReader
import com.gtohelper.domain.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCompetitorsFromTableViewModel @Inject constructor(
    private val repository: CompetitorRepository,
    private val application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    private val competitionId: Int = savedStateHandle["competition_id"] ?: 0

    private val _uiState = MutableStateFlow<AddCompetitorsFromTableUiState>(
        AddCompetitorsFromTableUiState.Initial
    )

    val uiState = _uiState.asStateFlow()

    val supportedFormats = listOf("xlsx", "xls", "xlsm")

    fun setInitial() = _uiState.update { AddCompetitorsFromTableUiState.Initial }

    fun addCompetitorsFromTable(excelUri: Uri) {
        viewModelScope.launch {
            _uiState.update { AddCompetitorsFromTableUiState.Loading }
            val reader = ExcelReader()
            val inputStream = application.contentResolver.openInputStream(excelUri) ?: return@launch

            try {
                val competitors = reader.getCompetitorList(competitionId, inputStream)
                val numbers = competitors.map { it.number }
                val duplicates = findAllDuplicates(numbers)
                if (duplicates.isNotEmpty()) {
                    _uiState.update {
                        AddCompetitorsFromTableUiState.Failed(
                            "При импортировании номер(-а): ${duplicates.joinToString()} встретились несколько раз. " +
                                    "Пожалуйста, проверьте правильность присвоенных номеров участникам."
                        )
                    }
                    return@launch
                }

                val alreadyExistingNumbers = getAlreadyExistingNumbers(numbers)
                if (alreadyExistingNumbers.isNotEmpty()) {
                    _uiState.update {
                        AddCompetitorsFromTableUiState.Failed(
                            "При импортировании номер(-а): ${alreadyExistingNumbers.joinToString()} уже существуют в соревновании. " +
                                    "Пожалуйста, проверьте правильность присвоенных номеров участникам."
                        )
                    }
                    return@launch
                }

                for (competitor in competitors) {
                    repository.create(competitor)
                }

                _uiState.update { AddCompetitorsFromTableUiState.Success }

            } catch (ex: Exception) {
                _uiState.update { AddCompetitorsFromTableUiState.Failed("Формат таблицы неверен. Пожалуйста, проверьте, что выбранная таблица не содержит ошибок.") }
            }

            inputStream.close()
        }
    }

    private suspend fun getAlreadyExistingNumbers(competitors: List<Int>): List<Int> {
        return competitors.filter { repository.getBy(it, competitionId) != null }
    }

    private fun findAllDuplicates(items: List<Int>): Set<Int> {
        val seen: MutableSet<Int> = mutableSetOf()
        val duplicates: MutableSet<Int> = mutableSetOf()
        for (i in items) {
            if (!seen.add(i)) {
                duplicates.add(i)
            }
        }
        return duplicates
    }
}