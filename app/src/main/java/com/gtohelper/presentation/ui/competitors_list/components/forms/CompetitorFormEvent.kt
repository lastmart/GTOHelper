package com.gtohelper.presentation.ui.competitors_list.components.forms

import com.gtohelper.domain.models.Gender

sealed class CompetitorFormEvent {
    data object Submit : CompetitorFormEvent()
    data class UpdateName(val value: String) : CompetitorFormEvent()
    data class UpdateTeamName(val value: String) : CompetitorFormEvent()
    data class UpdateGender(val value: Gender) : CompetitorFormEvent()
    data class UpdateNumber(val value: Int) : CompetitorFormEvent()
    data class UpdateDegree(val value: Int) : CompetitorFormEvent()
}