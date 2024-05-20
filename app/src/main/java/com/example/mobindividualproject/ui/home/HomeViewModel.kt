package com.example.mobindividualproject.ui.home

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.Format

class HomeViewModel(
    private val repo: WordRepo
): ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks


    fun getAllTasks() {
        viewModelScope.launch {
            val filteredTasks = repo.getAllTasks().map {
                it.filter { it.status == false }
            }.first()
            _tasks.value = filteredTasks
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    (this[APPLICATION_KEY] as WordApp).repo
                )
            }
        }
    }
}