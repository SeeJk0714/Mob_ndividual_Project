package com.example.mobindividualproject.ui.show

import android.text.Spannable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobindividualproject.WordApp
import com.example.mobindividualproject.data.model.Task
import com.example.mobindividualproject.data.repository.WordRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ShowViewModel(
    private val repo: WordRepo
): ViewModel() {
    private val _task: MutableLiveData<Task> = MutableLiveData()
    val task: LiveData<Task> = _task
    val title: MutableLiveData<String> = MutableLiveData()
    val mean: MutableLiveData<String> = MutableLiveData()
    val synonym: MutableLiveData<String> = MutableLiveData()
    val detail: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(repo.getTaskById(id))
        }
    }

    fun completeTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val completedTask = task.value!!.copy(status = true)
            repo.updateTask(completedTask)
            finish.emit(Unit)
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteTask(task.value!!)
            finish.emit(Unit)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                ShowViewModel(
                        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WordApp).repo
                )
            }
        }
    }
}