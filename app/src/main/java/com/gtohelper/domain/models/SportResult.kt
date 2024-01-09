package com.gtohelper.domain.models

data class SportResult(
    val id: Int? = null,
    val disciplineId: Int,
    val competitionId: Int,
    val value: Int,
    val competitorId: Int,
)


