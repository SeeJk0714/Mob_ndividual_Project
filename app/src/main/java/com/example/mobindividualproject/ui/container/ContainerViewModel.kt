package com.example.mobindividualproject.ui.container

import android.text.Spannable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobindividualproject.WordApp
import com.example.mobindividualproject.data.model.Task
import com.example.mobindividualproject.data.repository.WordRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ContainerViewModel(
    private val repo: WordRepo
): ViewModel() {
    val finish: MutableSharedFlow<Unit> = MutableSharedFlow()

    companion object {
        val Factory = viewModelFactory {
            initializer {
                ContainerViewModel((this[APPLICATION_KEY] as WordApp).repo)
            }
        }
    }

}