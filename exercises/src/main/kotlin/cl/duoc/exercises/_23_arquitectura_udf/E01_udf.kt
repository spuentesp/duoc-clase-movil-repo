package cl.duoc.exercises._23_arquitectura_udf

// ============================================================
// POR QUÉ Unidirectional Data Flow (UDF)
// ============================================================
// Flujo de datos en una sola dirección: View -> ViewModel (eventos),
// ViewModel -> View (estado). La View no modifica el estado directamente.
// Por qué:
//   - Testeable: dado un evento, espero un estado.
//   - Predecible: el estado solo cambia cuando el ViewModel lo emite.
//   - Sin "fuentes de verdad" múltiples.
//
// DEMO: contador bidireccional vs UDF.

class ContadorBidireccional {
    var count = 0  // mutable, accesible desde la View
    fun increment() { count++ }
}

// En UDF el estado está en el ViewModel y la View solo lo lee.

fun main() {
    val c = ContadorBidireccional()
    c.count = 100  // la View puede meter la mano: mal
    c.increment()
    println(c.count)  // 101
}
