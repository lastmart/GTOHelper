package com.gtohelper.presentation.ui.disciplines_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.data.database.discipline.DisciplineDao
import com.gtohelper.data.database.relations.SubDisciplineWithCompetitorsWithResults
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.SubDiscipline
import com.gtohelper.domain.repository.CompetitorRepository
import com.gtohelper.domain.repository.DisciplineRepository
import com.gtohelper.domain.usecases.DeleteCompetitionByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisciplinesListViewModel @Inject constructor(
    private val disciplineRepository: DisciplineRepository,
    private val deleteCompetitionByIdUseCase: DeleteCompetitionByIdUseCase,
    private val competitorRepository: CompetitorRepository,
    private val disciplinesDao: DisciplineDao,
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

    suspend fun getCompetitors(): List<Competitor> {
        return competitorRepository.getCompetitionAllCompetitors(competitionId)
    }

    suspend fun getDisciplinesWithCompetitorsWithResults(): List<SubDisciplineWithCompetitorsWithResults> {
        return disciplinesDao.getDisciplineWithCompetitorsWithResults()
    }

    fun onSubDisciplineLongPressed(subDiscipline: SubDiscipline) {
        subDisciplineToDelete = subDiscipline
        isDeleteSubDisciplineDialogShown = true
    }

    fun onDismissDeleteSubDisciplineDialog() {
        isDeleteSubDisciplineDialogShown = false
    }

    fun onTableSaved() {
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