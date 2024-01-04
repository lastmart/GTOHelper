package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.data.database.sport_result.SportResultDao
import com.gtohelper.data.database.sport_result.SportResultEntity
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.domain.repository.CompetitorResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CompetitorsResultsViewModel @Inject constructor(
//    val competitorResultsRepository: CompetitorResultsRepository,
//    val sportResultDao: SportResultDao
) : ViewModel() {
    private var _competitorsResultsLiveData = MutableLiveData<List<CompetitorResults>>()
    val competitorsResultsLiveData: LiveData<List<CompetitorResults>> get() = _competitorsResultsLiveData

    init{
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