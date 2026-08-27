package cl.duoc.exercises._14_coroutines_basics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * EJERCICIO: Coroutines básicas — launch
 *
 * OBJETIVO: Una coroutine no es un thread: es una unidad de suspensión
 * cooperativa. `launch` arranca coroutine que devuelve Job.
 *
 * INSTRUCCIONES:
 * 1. `lanzarYSaludar()` arranca una coroutine que duerme 100ms y
 *    luego imprime "Hola desde coroutine". Retorna Unit. Usa
 *    `runBlocking` para esperar.
 */
fun lanzarYSaludar(): Unit = TODO()
