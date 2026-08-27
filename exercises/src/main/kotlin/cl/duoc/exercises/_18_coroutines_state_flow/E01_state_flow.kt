package cl.duoc.exercises._18_coroutines_state_flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * EJERCICIO: StateFlow y SharedFlow
 *
 * OBJETIVO: StateFlow es hot stream con un valor inicial; los nuevos
 * collectors reciben el último valor (conflated). Perfecto para UI state.
 *
 * INSTRUCCIONES:
 * 1. Implementa `Contador` con un `MutableStateFlow<Int>` interno y
 *    métodos `incrementar()`, `valor: StateFlow<Int>`.
 */
class Contador {
    private val _valor = MutableStateFlow(0)
    val valor: StateFlow<Int> = _valor.asStateFlow()
    fun incrementar(): Unit = TODO()
}
