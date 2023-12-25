package com.gtohelper.presentation.ui.competitors_list.add_competitor

import com.gtohelper.domain.models.Gender

data class AddCompetitorFormState(
    val name: String = "",
    val nameError: String? = null,
    val teamName: String = "",
    val teamNameError: String? = null,
    val number: Int = 0,
    val numberError: String? = null,
    val degree: Int = 0,
    val degreeError: String? = null,
    val gender: Gender = Gender.MALE,
)