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
enum class Gender(val string: String){
    FEMALE("female"), MALE("male");
}

fun calculateDegree(age: Int): Int {
    return when (age) {
        in 3..7 -> 1
        in 8..9 -> 2
        in 10..11 -> 3
        in 12..13 -> 4
        in 14..15 -> 5
        in 16..17 -> 6
        in 18..19 -> 7
        in 20..24 -> 8
        in 25..29 -> 9
        in 30..34 -> 10
        in 35..39 -> 11
        in 40..44 -> 12
        in 45..49 -> 13
        in 50..54 -> 14
        in 55..59 -> 15
        in 60..64 -> 16
        in 65..69 -> 17
        in 70..120 -> 18
        else -> -1
    }
}