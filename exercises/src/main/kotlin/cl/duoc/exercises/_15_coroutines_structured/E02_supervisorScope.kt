package cl.duoc.exercises._15_coroutines_structured

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.runBlocking

/**
 * EJERCICIO: supervisorScope vs coroutineScope
 *
 * OBJETIVO: En `coroutineScope`, una excepción en un hijo cancela a todos.
 * En `supervisorScope`, los hermanos sobreviven.
 *
 * INSTRUCCIONES:
 * 1. `supervisarHermanos()` lanza dos coroutines dentro de
 *    `supervisorScope`. La primera falla con RuntimeException("A").
 *    La segunda termina OK y agrega "B" a una MutableList.
 *    Retorna la lista.
 */
fun supervisarHermanos(): List<String> = TODO()
