package cl.duoc.exercises._09_generics

/**
 * EJERCICIO: Generics con variance
 *
 * OBJETIVO: `out T` (covariance) permite Producer; `in T` (contravariance)
 * permite Consumer.
 *
 * INSTRUCCIONES:
 * 1. Implementa `primero(lista: List<T>): T?` que retorna el primer
 *    elemento o null si está vacía.
 * 2. Implementa `convertirAString(lista: List<out Any>): String` que
 *    une los elementos (covariance out Any permite leer).
 */
fun <T> primero(lista: List<T>): T? = TODO()
fun convertirAString(lista: List<out Any>): String = TODO()