package com.gtohelper.domain.models

data class SportResult(
    val id: Int? = null,
    val sportName: String,
    val competitionId: Int,
    val value: Int,
    val competitorId: Int,
)


