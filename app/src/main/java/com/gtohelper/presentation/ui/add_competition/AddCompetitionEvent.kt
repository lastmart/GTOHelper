package com.gtohelper.presentation.ui.add_competition

sealed class AddCompetitionEvent {

    data object SubmitForm : AddCompetitionEvent()
    data class UpdateName(val value: String) : AddCompetitionEvent()
    data class UpdateDescription(val value: String) : AddCompetitionEvent()
}