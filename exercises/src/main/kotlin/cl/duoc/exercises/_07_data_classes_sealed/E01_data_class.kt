package cl.duoc.exercises._07_data_classes_sealed

/**
 * EJERCICIO: Data class
 *
 * OBJETIVO: equals/hashCode/copy/toString/componentN automáticos.
 *
 * INSTRUCCIONES:
 * 1. Implementa la data class `Punto(x: Int, y: Int)`.
 * 2. Implementa `swap()` que retorna un Punto con x e y intercambiados.
 *    Usa `copy()`.
 */
data class Punto(val x: Int, val y: Int) {
    fun swap(): Punto = TODO()
}