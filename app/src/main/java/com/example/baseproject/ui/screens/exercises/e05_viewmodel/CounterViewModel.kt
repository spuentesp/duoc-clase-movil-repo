package com.example.baseproject.ui.screens.exercises.e05_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel de la mini-app 5. Sobrevive a cambios de configuración
    (rotación) gracias a su scope atado al ciclo de vida.
 */
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        viewModelScope.launch { _count.value += 1 }
    }

    fun decrement() {
        viewModelScope.launch { _count.value -= 1 }
    }
}
