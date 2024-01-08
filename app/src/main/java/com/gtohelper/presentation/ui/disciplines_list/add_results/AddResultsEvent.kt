package com.gtohelper.presentation.ui.disciplines_list.add_results

interface ModifyEvent {

}

sealed class AddResultsEvent {

    data object SaveResult : AddResultsEvent()
    data object ClearResult : AddResultsEvent()
    data class SearchResult(val query: String) : AddResultsEvent()
    data class UpdateNumber(val value: Int) : AddResultsEvent(), ModifyEvent
    data class UpdateResult(val value: Int) : AddResultsEvent(), ModifyEvent
}