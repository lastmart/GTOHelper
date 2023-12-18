package com.gtohelper.domain.models

class CompetitorResults(
    val CompetitorId: Int,
    //первый ключ - вид спорт, второй ключ - результат -> очки
    var dictSportNormative: MutableMap<String, MutableMap<String, Double>>,
    var TotalPoints: Double
)

