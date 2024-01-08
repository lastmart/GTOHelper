package com.gtohelper.domain.models

data class LongDuration(
    val seconds: Int,
    val minutes: Int,
    val hours: Int,
) {
    fun toMillis(): Int {
        return seconds * 1000 + minutes * 60 * 1000 + hours * 3600 * 1000
    }
    companion object{
        fun fromMillis(value: Int) : LongDuration{
            val inSeconds = value / 1000

            return LongDuration(
                hours = inSeconds / 3600,
                minutes = inSeconds % 3600 / 60,
                seconds = inSeconds % 60
            )
        }
    }
}