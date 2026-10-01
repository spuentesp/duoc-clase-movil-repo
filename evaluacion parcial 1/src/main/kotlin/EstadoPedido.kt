// =====================================================================
// EstadoPedido.kt — Estados del procesamiento asíncrono (sealed class)
// (IE 1.3.3 clases selladas + when exhaustivo)
// =====================================================================

/**
 * Estados posibles de un pedido: Pendiente -> En Preparación -> Listo | Error.
 *
 * Al ser una sealed class, el compilador conoce TODOS los subtipos, por lo que
 * un `when` sobre EstadoPedido es exhaustivo sin necesidad de `else`.
 * Los estados que transportan información (Listo, Error) son data class.
 */
sealed class EstadoPedido {
    data object Pendiente : EstadoPedido()
    data object EnPreparacion : EstadoPedido()
    data class Listo(val resumen: ResumenPedido) : EstadoPedido()
    data class Error(val mensaje: String) : EstadoPedido()
}

/** Texto legible de cada estado, resuelto con un when exhaustivo. */
fun EstadoPedido.etiqueta(): String = when (this) {
    is EstadoPedido.Pendiente -> "Pendiente"
    is EstadoPedido.EnPreparacion -> "En Preparación"
    is EstadoPedido.Listo -> "Listo"
    is EstadoPedido.Error -> "Error"
}
