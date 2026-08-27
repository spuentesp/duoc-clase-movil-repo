package cl.duoc.exercises._15_coroutines_structured

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * EJERCICIO: Cancelación cooperativa
 *
 * OBJETIVO: Las coroutines no se cancelan a la fuerza; chequean
 * `isActive` o llaman a funciones suspendibles que la cancelan
 * (delay, yield). Si tu coroutine hace CPU-bound sin chequear, no se cancela.
 *
 * INSTRUCCIONES:
 * 1. `cancelarAntesDe(): Job` lanza una coroutine que duerme 1000ms
 *    y luego imprime "terminó". Tras 100ms, cancela el Job retornado.
 *    Usa `runBlocking` para esperar.
 */
fun cancelarAntesDe(): Job = TODO()
