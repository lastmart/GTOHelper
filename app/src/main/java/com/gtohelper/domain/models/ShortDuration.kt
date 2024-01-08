package com.gtohelper.domain.models

data class ShortDuration(
    val seconds: Int,
    val deciSeconds : Int,
){

    fun toMillis(): Int {
        return seconds * 1000 + deciSeconds * 100
    }
    companion object {
        fun fromMillis(value: Int) : ShortDuration {
            return ShortDuration(
                deciSeconds = value % 1000 / 100,
                seconds = value / 1000
            )
        }
    }
}