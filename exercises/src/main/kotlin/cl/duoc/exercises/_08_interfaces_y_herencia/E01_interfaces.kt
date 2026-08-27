package cl.duoc.exercises._08_interfaces_y_herencia

/**
 * EJERCICIO: Interfaces y polimorfismo
 *
 * OBJETIVO: Definir contratos con default methods. Las clases que la
 * implementan pueden override.
 *
 * INSTRUCCIONES:
 * 1. Define interface `Forma` con propiedad abstracta `area: Double` y
 *    método `describir(): String` que retorna "Forma con área <area>".
 * 2. Implementa `Circulo(radio: Double)` y `Cuadrado(lado: Double)`.
 */
interface Forma {
    val area: Double
    fun describir(): String = "Forma con área ${area}"
}

class Circulo(val radio: Double) : Forma {
    override val area: Double = TODO()
}

class Cuadrado(val lado: Double) : Forma {
    override val area: Double = TODO()
}