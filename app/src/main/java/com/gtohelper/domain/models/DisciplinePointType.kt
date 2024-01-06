package com.gtohelper.domain.models

enum class DisciplinePointType{
    TIME,
    AMOUNT,
    BINARY;

    fun toReadable(value: Int) : String {
        return when (this) {
            TIME -> "${(value / 60).toString().padStart(2, '0')}:${(value % 60).toString().padStart(2, '0')}"
            AMOUNT -> value.toString()
            BINARY -> if (value != 0) "Зачет" else "Незачет"
        }
    }
}