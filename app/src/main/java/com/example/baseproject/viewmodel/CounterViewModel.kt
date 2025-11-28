package com.example.baseproject.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel de ejemplo que gestiona un contador simple
 *
 * Demuestra:
 * - Gestión de estado con StateFlow
 * - Lógica de negocio simple
 * - Funciones que modifican el estado
 */
class CounterViewModel : ViewModel() {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    /**
     * Incrementa el contador en 1
     */
    fun increment() {
        _count.value++
        updateMessage()
    }

    /**
     * Decrementa el contador en 1, no permite valores negativos
     */
    fun decrement() {
        if (_count.value > 0) {
            _count.value--
            updateMessage()
        }
    }

    /**
     * Reinicia el contador a 0
     */
    fun reset() {
        _count.value = 0
        _message.value = "Contador reiniciado"
    }

    /**
     * Establece un valor específico al contador
     */
    fun setCount(value: Int) {
        if (value >= 0) {
            _count.value = value
            updateMessage()
        }
    }

    /**
     * Multiplica el contador por un valor
     */
    fun multiplyBy(multiplier: Int) {
        _count.value *= multiplier
        updateMessage()
    }

    /**
     * Actualiza el mensaje basado en el valor actual del contador
     */
    private fun updateMessage() {
        _message.value = when {
            _count.value == 0 -> "El contador está en cero"
            _count.value < 10 -> "Contador bajo"
            _count.value < 50 -> "Contador medio"
            else -> "Contador alto"
        }
    }
}
