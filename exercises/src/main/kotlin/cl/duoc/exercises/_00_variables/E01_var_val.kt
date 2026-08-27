package cl.duoc.exercises._00_variables

/**
 * EJERCICIO: var vs val
 *
 * OBJETIVO: Diferenciar variables mutables (var) de inmutables (val).
 *
 * POR QUÉ IMPORTA: en Kotlin se prefiere val por defecto. Menos
 * mutación = menos bugs. var se reserva para cuando realmente
 * necesitas reasignar.
 *
 * INSTRUCCIONES:
 * 1. Implementa `sumar` que retorne la suma de a + b. Usa val internamente.
 * 2. Implementa `contadorInmutable` que retorne un Pair(val actual: Int, val incrementado: Int)
 *    sin mutar ninguna variable.
 *
 * DIFICULTAD: 🟢
 *
 * TEST: ./gradlew :test --tests "*_00_variables*E01*"
 */
fun sumar(a: Int, b: Int): Int = TODO()

fun contadorInmutable(actual: Int): Pair<Int, Int> = TODO()
