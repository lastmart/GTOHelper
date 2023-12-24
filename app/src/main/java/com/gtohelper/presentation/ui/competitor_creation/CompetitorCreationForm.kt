package com.gtohelper.presentation.ui.competitor_creation

import com.gtohelper.domain.models.Gender

data class CompetitorCreationForm(
    val name: String = "",
    val teamName: String = "",
    val number: Int = 0,
    val gender: Gender = Gender.MALE,
    val degree: Int = 1,
)