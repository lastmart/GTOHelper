package com.gtohelper.domain.models

import java.time.LocalTime

data class SportResult(
    val name: String,
    val competitorId: Int,
    val resultTime: LocalTime?,
    val resultAmount: Double?
)