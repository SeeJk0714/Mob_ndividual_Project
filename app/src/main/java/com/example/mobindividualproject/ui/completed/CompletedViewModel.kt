package com.example.mobindividualproject.ui.completed

import android.text.Spannable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobindividualproject.WordApp
import com.example.mobindividualproject.data.model.Task
import com.example.mobindividualproject.data.repository.WordRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CompletedViewModel(
    private val repo: WordRepo
): ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    fun getAllCompletedTasks () {
        viewModelScope.launch {
            val filteredTasks = repo.getAllCompletedTasks().map {
                it.filter { it.status == true }
            }.first()
            _tasks.value = filteredTasks
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CompletedViewModel((this[APPLICATION_KEY] as WordApp).repo)
            }
        }
    }
}