package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.domain.models.Competitor
import com.gtohelper.domain.models.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompetitorsResultsViewModel @Inject constructor(
//    val competitorResultsRepository: CompetitorResultsRepository,
//    val sportResultDao: SportResultDao
) : ViewModel() {
    val uiState: StateFlow<CompetitorsResultsUIState> = flow {
        emit(
            CompetitorsResultsUIState(
                competitorsResults = listOf(
                    Competitor(
                        id = 7,
                        number = 33,
                        competitionId = 2,
                        name = "Хруст Дмит Ром",
                        gender = Gender.MALE,
                        teamName = "Кроссфит",
                        degree = 6
                    ) to 350,

                    Competitor(
                        id = 8,
                        number = 31,
                        competitionId = 2,
                        name = "Хрусталев Дмитрий Романович",
                        gender = Gender.FEMALE,
                        teamName = "Гандбол",
                        degree = 7
                    ) to 72,

                    Competitor(
                        id = 9,
                        number = 32,
                        competitionId = 2,
                        name = "Хрусталев Дмитрий Романовичabcdefgh",
                        gender = Gender.MALE,
                        teamName = "Вьетнам",
                        degree = 8
                    ) to 9,
                )
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CompetitorsResultsUIState(listOf())
    )

    //   private var _competitorsResultsLiveData = MutableLiveData<List<CompetitorResults>>()
    //   val competitorsResultsLiveData: LiveData<List<CompetitorResults>> get() = _competitorsResultsLiveData

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            sportResultDao.upsertResult(SportResultEntity("Бег 60 м", 2, LocalTime.of(0, 1, 5, 1_000_000 * 9), null))
//            println(sportResultDao.getAllResults()[0].resultTime?.hour)
//            println(sportResultDao.getAllResults()[0].resultTime?.minute)
//            println(sportResultDao.getAllResults()[0].resultTime?.second)
//        }
    }

    suspend fun getCompetitorsResults() {
//        withContext(Dispatchers.IO) {
//            val competitorsResults = FakeCompetitors.competitorsWithResults
//            _competitorsResultsLiveData.postValue(competitorsResults)
//        }
    }

    suspend fun searchCompetitorsResultsByName(name: String) {
//        withContext(Dispatchers.IO) {
//            val competitorsResults = FakeCompetitors.competitorsWithResults
//                .filter { it.name.contains(name) }
//
//            _competitorsResultsLiveData.postValue(competitorsResults)
//        }
    }
}