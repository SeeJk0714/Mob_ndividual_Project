package com.example.mobindividualproject

import android.app.Application
import androidx.room.Room
import com.example.mobindividualproject.data.db.WordDatabase
import com.example.mobindividualproject.data.repository.WordRepo

class WordApp: Application() {
    lateinit var repo: WordRepo

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            this,
            WordDatabase::class.java,
            WordDatabase.NAME
        ).fallbackToDestructiveMigration().build()

       repo = WordRepo((db.workDao()))
    }
}