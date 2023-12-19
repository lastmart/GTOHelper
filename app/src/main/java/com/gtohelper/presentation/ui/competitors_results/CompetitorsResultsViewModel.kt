package com.gtohelper.presentation.ui.competitors_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.data.FakeCompetitors
import com.gtohelper.data.models.CompetitorResults
import com.gtohelper.domain.repository.CompetitorResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CompetitorsResultsViewModel @Inject constructor(
    val competitorResultsRepository: CompetitorResultsRepository
) : ViewModel() {
    private var _competitorsResultsLiveData = MutableLiveData<List<CompetitorResults>>()
    val competitorsResultsLiveData: LiveData<List<CompetitorResults>> get() = _competitorsResultsLiveData

    suspend fun getCompetitorsResults() {
        withContext(Dispatchers.IO) {
            val competitorsResults = FakeCompetitors.competitorsWithResults
            _competitorsResultsLiveData.postValue(competitorsResults)
        }
    }

    suspend fun searchCompetitorsResultsByName(name: String) {
        withContext(Dispatchers.IO) {
            val competitorsResults = FakeCompetitors.competitorsWithResults
                .filter { it.name.contains(name) }

            _competitorsResultsLiveData.postValue(competitorsResults)
        }
    }
}