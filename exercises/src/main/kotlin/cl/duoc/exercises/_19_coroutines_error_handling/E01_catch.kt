package cl.duoc.exercises._19_coroutines_error_handling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * EJERCICIO: Manejo de errores en coroutines y Flow
 *
 * OBJETIVO: En launch, try/catch dentro de la coroutine.
 * En async, await() lanza. En Flow, usar `catch` operator.
 *
 * INSTRUCCIONES:
 * 1. `flowConError(): Flow<Int>` emite 1, 2, y luego lanza RuntimeException("boom").
 * 2. `flowSeguro(): Flow<String>` consume flowConError y usa `.catch`
 *    para emitir "error" en lugar de propagar la excepción.
 */
fun flowConError(): Flow<Int> = TODO()
fun flowSeguro(): Flow<String> = TODO()
