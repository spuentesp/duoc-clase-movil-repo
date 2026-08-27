package com.example.baseproject.ui.screens.exercises.e07_network

import kotlinx.coroutines.delay

/**
 * Fake que simula una llamada de red con delay.
    Reemplaza Retrofit en este ejemplo para no agregar dependencias.
 */
data class Pokemon(val id: Int, val nombre: String)

class PokeApiFake {
    suspend fun fetchFirst(count: Int = 20): List<Pokemon> {
        delay(1500) // simula latencia de red
        return (1..count).map { Pokemon(it, "pokemon-$it") }
    }
}
