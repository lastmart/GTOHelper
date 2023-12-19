package com.gtohelper.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gtohelper.data.FakeTables
import com.gtohelper.data.models.TablePreview
import com.gtohelper.domain.repository.CompetitorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val competitorRepository: CompetitorRepository
) : ViewModel() {

    private var _tablesLiveData = MutableLiveData<List<TablePreview>>()
    val tablesLiveData: LiveData<List<TablePreview>> get() = _tablesLiveData

    suspend fun getTables() {
        withContext(Dispatchers.Default) {
            val tables = FakeTables.fakeTables
            _tablesLiveData.postValue(tables)
        }
    }

    suspend fun searchTablesByName(name: String) {
        withContext(Dispatchers.Default) {
            val tables = FakeTables.fakeTables.filter { it.title.contains(name) }
            _tablesLiveData.postValue(tables)
        }
    }
}