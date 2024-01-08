package com.gtohelper.domain.models

data class Competitor(
    val id: Int,
    val number: Int,
    val competitionId : Int,
    val name: String,
    val gender: Gender,
    val teamName: String,
    val degree: Int,
)