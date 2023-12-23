package com.gtohelper.presentation.ui.competitor_creation

import com.gtohelper.domain.models.Gender
import com.gtohelper.presentation.components.forms.AppForm
import com.gtohelper.presentation.components.forms.ValidationError

data class CompetitorCreationForm(
    val name: String = "",
    val teamName: String = "",
    val number: Int = 0,
    val gender: Gender = Gender.MALE,
    val degree: Int = 1,
) : AppForm {

    override fun validate(): ValidationError? {
        return "Fuck me"
    }
}