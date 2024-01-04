package com.gtohelper.domain.models

data class SportResult(
    val id: Int? = null,
    val sportName: String,
    val competitionId: Int,
    val value: Long,
    val competitorNumber: Int,
)


