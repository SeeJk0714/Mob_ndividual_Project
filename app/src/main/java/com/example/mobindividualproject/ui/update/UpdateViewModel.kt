package com.example.mobindividualproject.ui.update

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
import java.time.LocalDateTime

class UpdateViewModel(
    private val repo: WordRepo
): ViewModel() {
    private val _task: MutableLiveData<Task> = MutableLiveData()
    val task: LiveData<Task> = _task
    val title: MutableLiveData<String> = MutableLiveData()
    val mean: MutableLiveData<String> = MutableLiveData()
    val synonym: MutableLiveData<String> = MutableLiveData()
    val detail: MutableLiveData<String> = MutableLiveData()
    val showSnackbar: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun getTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _task.postValue(repo.getTaskById(id))
        }
    }

    fun setTask(task: Task) {
        task?.let {
            title.value = it.title
            mean.value = it.mean
            synonym.value = it.synonym
            detail.value = it.detail
        }
    }

    fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.value.isNullOrEmpty() || mean.value.isNullOrEmpty() || synonym.value.isNullOrEmpty() || detail.value.isNullOrEmpty()) {
                showSnackbar.postValue("The fields can't be empty!")
            }

            val task = task.value
            if (task != null) {
                repo.updateTask(task.copy(
                    title = title.value!!,
                    mean = mean.value!!,
                    synonym = synonym.value!!,
                    detail = detail.value!!,
                    date = LocalDateTime.now()
                    )
                )
                showSnackbar.postValue("Updated Successful!")
            }
            finish.emit(Unit)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                UpdateViewModel(
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WordApp).repo
                )
            }
        }
    }
}