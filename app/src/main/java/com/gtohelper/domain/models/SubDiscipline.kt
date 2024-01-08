package com.gtohelper.domain.models

data class SubDiscipline(
    val id : Int = 0,
    val name: String,
    val imageResource: Int,
    val type: DisciplinePointType
)