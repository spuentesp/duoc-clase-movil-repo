package cl.duoc.exercises._13_exception_handling

/**
 * EJERCICIO: Manejo de excepciones
 *
 * OBJETIVO: try/catch/finally, excepciones custom, runCatching.
 *
 * INSTRUCCIONES:
 * 1. Implementa `parsearEntero(s: String): Int` que retorne el entero
 *    parseado o 0 si la cadena no es válida. Usa `runCatching` y `.getOrDefault(0)`.
 * 2. Define `class SaldoInsuficienteException(val deficit: Double) : Exception(...)`.
 * 3. Implementa `retirar(saldo: Double, monto: Double): Double` que reste
 *    monto a saldo o lance SaldoInsuficienteException con el déficit.
 */
class SaldoInsuficienteException(val deficit: Double) :
    Exception("Saldo insuficiente: faltan ${deficit}")

fun parsearEntero(s: String): Int = TODO()

fun retirar(saldo: Double, monto: Double): Double = TODO()
