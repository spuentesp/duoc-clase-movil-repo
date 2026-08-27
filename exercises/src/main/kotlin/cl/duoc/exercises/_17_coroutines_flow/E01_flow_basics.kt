package cl.duoc.exercises._17_coroutines_flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * EJERCICIO: Flow fundamentals
 *
 * OBJETIVO: Flow es cold stream: cada collect() dispara el producer.
 * `emit(value)` produce un valor.
 *
 * INSTRUCCIONES:
 * 1. `numerosFlow(n: Int): Flow<Int>` retorna un flow que emite
 *    los números de 1 a n con `delay(50)` entre cada uno.
 * 2. `sumarFlow(n: Int): Int` consume el flow y retorna la suma.
 */
fun numerosFlow(n: Int): Flow<Int> = TODO()
suspend fun sumarFlow(n: Int): Int = TODO()
