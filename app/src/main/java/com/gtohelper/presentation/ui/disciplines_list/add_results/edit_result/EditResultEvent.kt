package com.gtohelper.presentation.ui.disciplines_list.add_results.edit_result

import com.gtohelper.presentation.ui.disciplines_list.add_results.ModifyEvent

sealed class EditResultEvent {
    data object SubmitEdit : EditResultEvent()
    data object SubmitDelete : EditResultEvent()
    data class UpdateNumber(val value: Int) : EditResultEvent(), ModifyEvent
    data class UpdateResult(val value: Int) : EditResultEvent(), ModifyEvent
}