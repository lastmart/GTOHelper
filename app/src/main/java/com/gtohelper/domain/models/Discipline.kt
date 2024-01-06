package com.gtohelper.domain.models

open class Discipline(
    val name: String,
    val imageResource: Int,
    val subDisciplines: List<Discipline> = emptyList(),
    val type: DisciplinePointType
) {

    override fun toString(): String {
        return "name: [$name], imageResource: [$imageResource], subDisciplines: [${subDisciplines.map { it.toString() }}]"
    }
}