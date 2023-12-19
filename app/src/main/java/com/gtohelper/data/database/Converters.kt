package com.gtohelper.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun toIdsJson(ids: List<Int>): String {
        return gson.toJson(ids)
    }

    @TypeConverter
    fun fromIdsJson(json: String): List<Int> {
        return gson.fromJson(
            json,
            object : TypeToken<List<Int>>() {}.type
        ) ?: emptyList()
    }
}