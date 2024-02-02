package com.gtohelper.presentation.ui.disciplines_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.repository.CompetitionRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.usecases.DeleteCompetitionByIdUseCase
import com.gtohelper.domain.usecases.DownloadResultTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class DisciplinesListViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository,
    private val deleteCompetitionByIdUseCase: DeleteCompetitionByIdUseCase,
    private val competitionRepository: CompetitionRepository,
    private val downloadResultTableUseCase: DownloadResultTableUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val competitionId = savedStateHandle["competition_id"] ?: 0
    private var subDisciplineToDelete: SubDiscipline? = null
    var isDeleteSubDisciplineDialogShown by mutableStateOf(false)
        private set

    var isDeleteCompetitionDialogShown by mutableStateOf(false)
        private set

    var isSnackBarShown by mutableStateOf(false)
        private set

    val uiState: StateFlow<DisciplinesListUIState> =
        disciplineRepository
            .getSelectedDisciplines(competitionId)
            .map { disciplines ->
                DisciplinesListUIState(
                    disciplines.flatMap { discipline ->
                        discipline.subDisciplines.map { subDiscipline ->
                            SubDiscipline(
                                id = subDiscipline.id,
                                name = subDiscipline.name,
                                type = subDiscipline.type,
                                imageResource = discipline.imageResource
                            )
                        }
                    }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DisciplinesListUIState()
            )

    val competitionName: StateFlow<String> = competitionRepository
        .getFlowById(competitionId)
        .map { it?.name ?: "" }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed((5_000)),
            initialValue = ""
        )

    fun onSubDisciplineLongPressed(subDiscipline: SubDiscipline) {
        subDisciplineToDelete = subDiscipline
        isDeleteSubDisciplineDialogShown = true
    }

    fun onDismissDeleteSubDisciplineDialog() {
        isDeleteSubDisciplineDialogShown = false
    }

    fun downloadResultTable(
        competitionName: String,
        outputStream: OutputStream
    ) {
        viewModelScope.launch {

            launch(Dispatchers.IO) {
                downloadResultTableUseCase(
                    competitionName = competitionName,
                    competitionId = competitionId,
                    outputStream = outputStream,
                )
            }.join()

            onTableDownloaded()
        }
    }

    fun onTableDownloaded() {
        isSnackBarShown = true
    }

    fun onSnackBarDismiss() {
        isSnackBarShown = false
    }

    fun deleteSubDiscipline() {
        viewModelScope.launch(Dispatchers.IO) {
            subDisciplineToDelete?.let {
                disciplineRepository.deleteSubDisciplineFromSelectedByName(it.name, competitionId)
            }
        }
    }

    fun onDeleteCompetitionPressed() {
        isDeleteCompetitionDialogShown = true
    }

    fun onDismissDeleteCompetitionDialog() {
        isDeleteCompetitionDialogShown = false
    }

    fun deleteCompetition() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCompetitionByIdUseCase(competitionId)
        }
    }
}