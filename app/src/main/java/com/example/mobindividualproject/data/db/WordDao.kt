package com.example.mobindividualproject.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobindividualproject.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM Task WHERE status = 0 ORDER BY title DESC")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTaskById(id: Int): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("SELECT * FROM Task WHERE status = 1 ORDER BY title DESC")
    fun getAllCompletedTasks(): Flow<List<Task>>

}