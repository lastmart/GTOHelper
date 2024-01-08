package com.gtohelper.domain.models


enum class DisciplinePointType {
    SHORT_TIME, // ss:ms
    LONG_TIME,  // mm:ss
    AMOUNT;     // just number

    fun toReadable(value: Int): String {
        return when (this) {
            SHORT_TIME -> {
                val duration = ShortDuration.fromMillis(value)
                return "${duration.seconds}.${duration.deciSeconds}"
            }

            LONG_TIME -> {
                val duration = LongDuration.fromMillis(value)
                val hours = duration.hours.toString().padStart(2, '0')
                val minutes = duration.minutes.toString().padStart(2, '0')
                val seconds = duration.seconds.toString().padStart(2, '0')
                return "$hours:$minutes:${seconds}"
            }

            AMOUNT -> value.toString()
        }
    }
}