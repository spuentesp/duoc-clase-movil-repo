package com.example.baseproject.ui.screens.exercises.e06_repository

/**
 * Interfaz de fuente de datos. Permite cambiar la implementación
    (fake en memoria, real con red, test) sin tocar la UI ni el ViewModel.
 */
interface CounterRepository {
    suspend fun getValue(): Int
    suspend fun saveValue(value: Int)
}

class InMemoryCounterRepository(private val initial: Int = 0) : CounterRepository {
    private var stored: Int = initial
    override suspend fun getValue(): Int = stored
    override suspend fun saveValue(value: Int) { stored = value }
}
