package com.gtohelper.domain.models

open class Discipline(
    val imageResource: Int,
    val name: String,
    val subDisciplines: List<Discipline> = emptyList(),
    var isSelected: Boolean = false
)