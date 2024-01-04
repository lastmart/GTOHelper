package com.gtohelper.presentation.ui.competitors_list.add_competitors_from_table

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.data.models.ExcelReader
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
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<AddCompetitorsFromTableUiState>(
        AddCompetitorsFromTableUiState.Loading
    )

    val uiState = _uiState.asStateFlow()

    fun addCompetitorsFromTable(uri: Uri) {
        val reader = ExcelReader()
        val inputStream = application.contentResolver.openInputStream(uri) ?: return

        viewModelScope.launch {
            try {
                val competitors = reader.getCompetitorList(inputStream)
                val numbers = competitors.map { it.number }.toList()
                val hasDuplicateNumbers = numbers.size != numbers.toHashSet().size

                if (hasDuplicateNumbers) {
                    return@launch
                }

            } catch (ex: Exception) {
                _uiState.update { AddCompetitorsFromTableUiState.Failed(ex.message) }
            }
        }

        inputStream.close()
    }
}