package ejercicio3

import kotlinx.coroutines.delay

sealed class EstadoPago {
    object Pendiente : EstadoPago()
    data class Procesando(val id: Int) : EstadoPago()
    data class Aprobado(val id: Int, val monto: Int) : EstadoPago()
    data class Rechazado(val id: Int, val motivo: String) : EstadoPago()
}

class ProcesadorPagos {
    suspend fun procesarPago(id: Int, monto: Int): EstadoPago {
        delay(1500)
        return if (monto > 0) EstadoPago.Aprobado(id, monto)
        else EstadoPago.Rechazado(id, "Monto invalido")
    }
}

fun main() = kotlinx.coroutines.runBlocking {
    val p = ProcesadorPagos()
    when (val r = p.procesarPago(id = 1, monto = 1500)) {
        EstadoPago.Pendiente       -> println("Pago pendiente")
        is EstadoPago.Procesando   -> println("Procesando pago #${r.id}")
        is EstadoPago.Aprobado     -> println("Aprobado #${r.id} por \$${r.monto}")
        is EstadoPago.Rechazado    -> println("Rechazado #${r.id}: ${r.motivo}")
    }
}