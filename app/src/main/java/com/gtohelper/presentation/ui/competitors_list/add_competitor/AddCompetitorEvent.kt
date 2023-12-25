package com.gtohelper.presentation.ui.competitors_list.add_competitor

import com.gtohelper.domain.models.Gender

sealed class AddCompetitorEvent {
    data object Submit : AddCompetitorEvent()
    data class UpdateName(val value: String) : AddCompetitorEvent()
    data class UpdateTeamName(val value: String) : AddCompetitorEvent()
    data class UpdateGender(val value: Gender) : AddCompetitorEvent()
    data class UpdateNumber(val value: Int) : AddCompetitorEvent()
    data class UpdateDegree(val value: Int) : AddCompetitorEvent()
}