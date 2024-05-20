package com.example.mobindividualproject.ui.add

import android.text.Spannable.Factory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobindividualproject.WordApp
import com.example.mobindividualproject.data.model.Task
import com.example.mobindividualproject.data.repository.WordRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AddViewModel(
    private val repo: WordRepo
): ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val mean: MutableLiveData<String> = MutableLiveData()
    val synonym: MutableLiveData<String> = MutableLiveData()
    val detail: MutableLiveData<String> = MutableLiveData()
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    fun submit() {
        if (title.value != null && mean.value != null && synonym.value != null && detail.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.addTask(Task(title = title.value!!, mean = mean.value!!, synonym = synonym.value!!, detail = detail.value!!))
            }
            viewModelScope.launch {
                finish.emit(Unit)
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                AddViewModel(
                    (this[APPLICATION_KEY] as WordApp).repo
                )
            }
        }
    }
}