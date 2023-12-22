package com.gtohelper.domain.models

open class Discipline(
    val imageResource: Int,
    val name: String,
    val subDisciplines: List<SubDiscipline> = emptyList()
) {
    init {
        subDisciplines.forEach { it.parentDiscipline = name }
    }
}

data class SubDiscipline(
    val imageResource: Int,
    val name: String,
    var parentDiscipline: String? = null
)