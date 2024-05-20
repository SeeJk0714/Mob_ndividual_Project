package com.example.mobindividualproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val mean: String,
    val synonym: String,
    val detail: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val status: Boolean? = false,
    val priority: Byte = 0
)