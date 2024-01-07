package com.gtohelper.domain.models


enum class DisciplinePointType {
    SHORT_TIME, // ss:ms
    LONG_TIME, // mm:ss
    AMOUNT; // just number

    fun toReadable(value: Int): String {
        return when (this) {
            SHORT_TIME -> """
                ${(value / 60).toString().padStart(2, '0')}:
                ${(value / 600).toString().padStart(2, '0')}
                """

            LONG_TIME -> {
                """
                ${(value / 60).toString().padStart(2, '0')}:
                ${(value % 60).toString().padStart(2, '0')}
                """
            }
            AMOUNT -> value.toString()
        }
    }
}