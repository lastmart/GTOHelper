package com.gtohelper.data

import com.gtohelper.data.models.TablePreview

class FakeTables {

    companion object {
        private val classes = listOf("1 \"A\"", "11 \"B\"", "Детский сад")
        private val descriptions =
            listOf("Эстафета", "Сдача нормативов", "ГТО", "Экзамен по кроссфиту")

        val fakeTables = (0..15).map {
            TablePreview(
                classes[it % classes.count()],
                description = descriptions[it % descriptions.count()]
            )
        }
    }
}