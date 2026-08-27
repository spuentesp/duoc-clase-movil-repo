package cl.duoc.exercises._14_coroutines_basics

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * EJERCICIO: async / await
 *
 * OBJETIVO: async retorna Deferred<T>; await() obtiene el valor.
 * Útil para paralelizar tareas independientes.
 *
 * INSTRUCCIONES:
 * 1. `sumarParalelo(a: Int, b: Int): Int` calcula a*2 y b*3 en paralelo
 *    usando `coroutineScope { async { ... }; async { ... } }` y retorna
 *    la suma de los dos resultados.
 */
suspend fun sumarParalelo(a: Int, b: Int): Int = TODO()
