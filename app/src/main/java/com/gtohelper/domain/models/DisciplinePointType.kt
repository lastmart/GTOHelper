package com.gtohelper.domain.models

import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField


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

fun convertIntToSportResult(discipline: SubDiscipline, sportResult: SportResult): Any {
    return when (discipline.type) {
        DisciplinePointType.SHORT_TIME -> {
            val shortDuration = ShortDuration.fromMillis(sportResult.value)

            convertRunTime(
                time = "${shortDuration.seconds}.${shortDuration.deciSeconds}"
            )
        }

        DisciplinePointType.LONG_TIME -> {
            val longDuration = LongDuration.fromMillis(sportResult.value)
            LocalTime.of(longDuration.hours, longDuration.minutes, longDuration.seconds)
        }

        DisciplinePointType.AMOUNT -> sportResult.value.toDouble()
    }
}

fun convertRunTime(time: String): LocalTime {
    var changeTime = time
    if ("." !in time) {
        changeTime = "$time.0"
    }
    val seconds = changeTime.split(".")[0]
    val milliseconds = changeTime.split(".")[1]
    val time =
        "00:${if (seconds.length == 1) "0$seconds" else seconds}.${if (milliseconds.length == 1) "${milliseconds}0" else milliseconds}"
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("mm:ss.SS")
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .toFormatter()
    return LocalTime.parse(time, formatter)
}