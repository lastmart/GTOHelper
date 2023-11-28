package com.gtohelper.presentation.ui.competitors_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.data.FakeCompetitors
import com.gtohelper.data.models.Competitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CompetitorsListViewModel : ViewModel() {
    private var _competitorsLiveData = MutableLiveData<List<Competitor>>()
    val competitorsLiveData: LiveData<List<Competitor>> get() = _competitorsLiveData

    suspend fun getCompetitors(amount: Int = 13) {
        withContext(Dispatchers.Default) {
            val competitors = FakeCompetitors.competitors
            _competitorsLiveData.postValue(competitors)
        }
    }

    suspend fun searchCompetitorsByName(name: String) {
        withContext(Dispatchers.Default) {
            val competitors = FakeCompetitors.competitors
                .filter { it.name.contains(name) }

            _competitorsLiveData.postValue(competitors)
        }
    }
}