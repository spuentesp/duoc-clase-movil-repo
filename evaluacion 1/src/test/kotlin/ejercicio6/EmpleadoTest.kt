package ejercicio6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 6 - Nóminas: tipos, aritmética y condicionales")
class EmpleadoTest {

    @Test
    @DisplayName("tarifaPorHora depende del tipo de empleado")
    fun tarifaPorHoraSegunTipo() {
        assertEquals(8_000, Empleado("Ana", 100, TipoEmpleado.JUNIOR).tarifaPorHora)
        assertEquals(15_000, Empleado("Ana", 100, TipoEmpleado.SENIOR).tarifaPorHora)
        assertEquals(25_000, Empleado("Ana", 100, TipoEmpleado.GERENTE).tarifaPorHora)
    }

    @Test
    @DisplayName("bono es 0 cuando horasTrabajadas <= 160")
    fun bonoCeroSiNoHayHorasExtra() {
        val e = Empleado("Ana", 160, TipoEmpleado.JUNIOR)
        assertEquals(0, e.bono)
    }

    @Test
    @DisplayName("bono es 5000 por cada hora extra sobre 160")
    fun bonoPorHorasExtra() {
        val e = Empleado("Ana", 165, TipoEmpleado.JUNIOR)
        assertEquals(25_000, e.bono)
    }

    @Test
    @DisplayName("sueldoBruto es horas * tarifa + bono")
    fun sueldoBrutoCombina() {
        val e = Empleado("Ana", 165, TipoEmpleado.SENIOR)
        val esperado = 165 * 15_000 + 25_000
        assertEquals(esperado, e.calcularSueldoBruto())
    }

    @Test
    @DisplayName("descuento es 0 si bruto < 500000")
    fun descuentoCeroSiBrutoBajo() {
        val e = Empleado("Ana", 50, TipoEmpleado.JUNIOR)
        assertEquals(0, e.calcularDescuento())
    }

    @Test
    @DisplayName("descuento es 8% si bruto entre 500000 y 999999")
    fun descuentoOchoPorCiento() {
        val e = Empleado("Ana", 100, TipoEmpleado.JUNIOR)
        val bruto = e.calcularSueldoBruto()
        assertTrue(bruto in 500_000..999_999)
        assertEquals((bruto * 8) / 100, e.calcularDescuento())
    }

    @Test
    @DisplayName("descuento es 15% si bruto >= 1000000")
    fun descuentoQuincePorCiento() {
        val e = Empleado("Ana", 200, TipoEmpleado.GERENTE)
        val bruto = e.calcularSueldoBruto()
        assertTrue(bruto >= 1_000_000)
        assertEquals((bruto * 15) / 100, e.calcularDescuento())
    }

    @Test
    @DisplayName("sueldoNeto es bruto menos descuento")
    fun sueldoNeto() {
        val e = Empleado("Pedro", 200, TipoEmpleado.GERENTE)
        val bruto = e.calcularSueldoBruto()
        assertEquals(bruto - e.calcularDescuento(), e.calcularSueldoNeto())
    }

    @Test
    @DisplayName("horasTrabajadas negativas lanzan IllegalArgumentException")
    fun horasNegativasLanzanExcepcion() {
        assertThrows(IllegalArgumentException::class.java) {
            Empleado("Ana", -1, TipoEmpleado.JUNIOR)
        }
    }
}
