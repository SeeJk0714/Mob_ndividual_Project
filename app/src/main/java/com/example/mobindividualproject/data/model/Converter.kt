package com.example.mobindividualproject.data.model

import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converter {
    @TypeConverter
    fun fromTimestemp(value: String?): LocalDateTime? = value?.let {
        LocalDateTime.parse(it)
    }

    @TypeConverter
    fun dateToTimestemp(date: LocalDateTime?): String? = date?.toString()
}