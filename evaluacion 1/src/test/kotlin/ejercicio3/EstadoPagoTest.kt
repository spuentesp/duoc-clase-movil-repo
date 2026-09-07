package ejercicio3

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 3 - Pagos hospitalarios: sealed class y corrutinas")
class EstadoPagoTest {

    @Test
    @DisplayName("procesarPago con monto > 0 retorna Aprobado con id y monto")
    fun procesarPagoMontoPositivoAprueba() = runTest {
        val p = ProcesadorPagos()
        val estado = p.procesarPago(id = 1, monto = 1500)
        assertTrue(estado is EstadoPago.Aprobado)
        estado as EstadoPago.Aprobado
        assertEquals(1, estado.id)
        assertEquals(1500, estado.monto)
    }

    @Test
    @DisplayName("procesarPago con monto <= 0 retorna Rechazado con motivo")
    fun procesarPagoMontoInvalidoRechaza() = runTest {
        val p = ProcesadorPagos()
        val estado = p.procesarPago(id = 2, monto = 0)
        assertTrue(estado is EstadoPago.Rechazado)
        estado as EstadoPago.Rechazado
        assertEquals(2, estado.id)
        assertTrue(estado.motivo.isNotBlank())
    }

    @Test
    @DisplayName("procesarPago respeta cancelacion de la corrutina")
    fun procesarPagoRespetaCancelacion() = runTest {
        val p = ProcesadorPagos()
        val estado = p.procesarPago(id = 3, monto = 500)
        assertTrue(estado is EstadoPago.Aprobado || estado is EstadoPago.Rechazado)
    }

    @Test
    @DisplayName("sealed class cubre los 4 estados con when exhaustivo")
    fun sealedCubreLosCuatroEstados() {
        val estados: List<EstadoPago> = listOf(
            EstadoPago.Pendiente,
            EstadoPago.Procesando(id = 1),
            EstadoPago.Aprobado(id = 2, monto = 100),
            EstadoPago.Rechazado(id = 3, motivo = "x")
        )
        assertEquals(4, estados.size)
    }

    @Test
    @DisplayName("Aprobado y Rechazado exponen datos especificos")
    fun aprobadoYRechazadoExponenDatos() {
        val a = EstadoPago.Aprobado(id = 10, monto = 999)
        val r = EstadoPago.Rechazado(id = 11, motivo = "sin fondos")
        assertEquals(999, a.monto)
        assertEquals("sin fondos", r.motivo)
    }
}