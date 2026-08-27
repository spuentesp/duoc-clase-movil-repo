package cl.duoc.exercises._07_data_classes_sealed

/**
 * EJERCICIO: Sealed class
 *
 * OBJETIVO: Modelar jerarquías cerradas. Útil para UI state y resultados.
 *
 * INSTRUCCIONES:
 * 1. Define sealed class `Resultado` con tres subclases: Exito(val valor: Int),
 *    Error(val mensaje: String), Pendiente.
 * 2. Implementa `describir(r: Resultado): String` que use when exhaustivo
 *    y retorne "Exito(<valor>)", "Error: <mensaje>", "Pendiente".
 */
sealed class Resultado {
    data class Exito(val valor: Int) : Resultado()
    data class Error(val mensaje: String) : Resultado()
    data object Pendiente : Resultado()
}

fun describir(r: Resultado): String = TODO()