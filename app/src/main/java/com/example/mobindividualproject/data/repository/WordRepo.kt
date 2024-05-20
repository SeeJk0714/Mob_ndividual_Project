package com.example.mobindividualproject.data.repository

import com.example.mobindividualproject.data.db.WordDao
import com.example.mobindividualproject.data.model.Task
import kotlinx.coroutines.flow.Flow

class WordRepo (
    private val dao: WordDao
) {
    fun getAllTasks(): Flow<List<Task>> {
       return dao.getAll()
    }

    fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    fun addTask(task: Task) {
        dao.addTask(task)
    }

    fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    fun getAllCompletedTasks(): Flow<List<Task>> {
        return dao.getAllCompletedTasks()
    }
}