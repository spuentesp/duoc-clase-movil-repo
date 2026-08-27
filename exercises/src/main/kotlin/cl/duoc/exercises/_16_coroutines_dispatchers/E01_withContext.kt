package cl.duoc.exercises._16_coroutines_dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * EJERCICIO: withContext y Dispatchers
 *
 * OBJETIVO: Dispatchers.Main/IO/Default. withContext cambia de thread
 * sin necesidad de callback hell.
 *
 * INSTRUCCIONES:
 * 1. `threadDeIO(): String` retorna el nombre del thread desde
 *    `Dispatchers.IO`. Usa `withContext(Dispatchers.IO) { Thread.currentThread().name }`.
 */
suspend fun threadDeIO(): String = TODO()
