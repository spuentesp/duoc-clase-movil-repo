package cl.duoc.exercises._03_funciones_y_lambdas

/**
 * EJERCICIO: Lambdas y funciones de orden superior
 *
 * OBJETIVO: Pasar comportamiento como parámetro. La base de map/filter/etc.
 *
 * INSTRUCCIONES:
 * 1. `aplicarDoble(lista, transform)` aplica transform a cada elemento y retorna el doble.
 * 2. `filtrar` retorna sublista de elementos que cumplen `predicado`.
 * 3. `reducirA(lista, inicial, op)` reduce la lista con op partiendo de inicial.
 */
fun aplicarDoble(lista: List<Int>, transform: (Int) -> Int): List<Int> = TODO()
fun filtrar(lista: List<Int>, predicado: (Int) -> Boolean): List<Int> = TODO()
fun reducirA(lista: List<Int>, inicial: Int, op: (Int, Int) -> Int): Int = TODO()
