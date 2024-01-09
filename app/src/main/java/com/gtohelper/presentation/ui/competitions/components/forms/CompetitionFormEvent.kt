package com.gtohelper.presentation.ui.competitions.components.forms

sealed class CompetitionFormEvent {

    data object SubmitForm : CompetitionFormEvent()
    data class UpdateName(val value: String) : CompetitionFormEvent()
    data class UpdateDescription(val value: String) : CompetitionFormEvent()
}