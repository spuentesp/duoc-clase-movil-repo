package cl.duoc.exercises._03_funciones_y_lambdas

/**
 * EJERCICIO: Funciones en Kotlin
 *
 * OBJETIVO: default args, named args, single-expression, vararg, infix.
 *
 * INSTRUCCIONES:
 * 1. `saludo` retorna "Hola, <nombre>" con argumento por defecto "mundo".
 * 2. `concatenar` recibe vararg de strings y los une con separador por defecto ",".
 * 3. `infix fun Int.multiplicadoPor(n: Int)` retorna this * n.
 */
fun saludo(nombre: String = "mundo"): String = TODO()
fun concatenar(separador: String = ",", vararg partes: String): String = TODO()
infix fun Int.multiplicadoPor(n: Int): Int = TODO()
