package com.gtohelper.domain.models

data class Competitor(
    val nameCompetitor: String,
    val age: Int,
    val gender: String,
    val nameTeam: String,
    val participantNumber: Int,
    val degree: Int = calculateDegree(age)
)
enum class Gender(val string: String){
    FEMALE("female"), MALE("male");
}

fun calculateDegree(age:Int): Int{
    return when {
        age in 8..9 -> 2
        age in 10..11 -> 3
        age in 12..13 -> 4
        age in 14..15 -> 5
        age in 16..17 -> 6
        age in 18..19 -> 7
        age in 20..24 -> 8
        age in 25..29 -> 9
        age in 30..34 -> 10
        age in 35..39 -> 11
        age in 40..44 -> 12
        age in 45..49 -> 13
        age in 50..54 -> 14
        age in 55..59 -> 15
        age in 60..64 -> 16
        age in 65..69 -> 17
        age in 70..120 -> 18
        else -> -1
    }
}