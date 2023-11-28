package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.data.FakeCompetitors
import com.gtohelper.data.models.CompetitorResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CompetitorsResultsViewModel : ViewModel() {
    private var _competitorsResultsLiveData = MutableLiveData<List<CompetitorResults>>()
    val competitorsResultsLiveData: LiveData<List<CompetitorResults>> get() = _competitorsResultsLiveData

    suspend fun getCompetitorsResults() {
        withContext(Dispatchers.Default) { // TODO coroutine context
            val competitorsResults = FakeCompetitors.competitorsWithResults
            _competitorsResultsLiveData.postValue(competitorsResults)
        }
    }

    suspend fun searchCompetitorsResultsByName(name: String) {
        withContext(Dispatchers.Default) {
            val competitorsResults = FakeCompetitors.competitorsWithResults
                .filter { it.name.contains(name) }

            _competitorsResultsLiveData.postValue(competitorsResults)
        }
    }
}