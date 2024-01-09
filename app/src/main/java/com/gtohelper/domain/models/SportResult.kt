package com.gtohelper.domain.models

import java.time.LocalDateTime

data class SportResult(
    val id: Int? = null,
    val disciplineId: Int,
    val competitionId: Int,
    val value: Int,
    val competitorId: Int,
    val timeStamp: LocalDateTime = LocalDateTime.now(),
)


