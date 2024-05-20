package com.example.mobindividualproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mobindividualproject.data.model.Converter
import com.example.mobindividualproject.data.model.Task

@Database(entities = [Task::class], version = 2)
@TypeConverters(Converter::class)

abstract class WordDatabase: RoomDatabase() {
    abstract fun workDao(): WordDao

    companion object {
        const val NAME = "my_work_database"
    }
}