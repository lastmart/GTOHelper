package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtohelper.data.database.sport.SportDao
import com.gtohelper.data.database.sport.SportResultEntity
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.domain.repository.CompetitorResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CompetitorsResultsViewModel @Inject constructor(
    val competitorResultsRepository: CompetitorResultsRepository,
    val sportDao: SportDao
) : ViewModel() {
    private var _competitorsResultsLiveData = MutableLiveData<List<CompetitorResults>>()
    val competitorsResultsLiveData: LiveData<List<CompetitorResults>> get() = _competitorsResultsLiveData

    init{
        viewModelScope.launch(Dispatchers.IO) {
            sportDao.upsertSport(SportResultEntity("Бег 60 м", 2, LocalTime.of(0, 1, 5, 1_000_000 * 9), null))
            println(sportDao.getSports()[0].resultTime?.hour)
            println(sportDao.getSports()[0].resultTime?.minute)
            println(sportDao.getSports()[0].resultTime?.second)
        }
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