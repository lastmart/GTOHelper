package com.gtohelper.presentation.ui.competitors_list.components.forms

import com.gtohelper.domain.models.Gender

data class CompetitorFormState(
    val name: String = "",
    val nameError: String? = null,
    val teamName: String = "",
    val teamNameError: String? = null,
    val number: Int? = null,
    val numberError: String? = null,
    val degree: Int = 1,
    val degreeError: String? = null,
    val gender: Gender = Gender.MALE,
)