package ejercicio4

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 4 - Reservas de hotel: excepciones personalizadas")
class HabitacionTest {

    @Test
    @DisplayName("reservar con noches > 0 y habitacion existente crea la reserva")
    fun reservarConNochesPositivasYHabitacionExistente() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        val r = g.reservar("Ana", 101, 2)
        assertEquals("Ana", r.huesped)
        assertEquals(101, r.habitacion.numero)
        assertEquals(2, r.noches)
    }

    @Test
    @DisplayName("reservar con noches <= 0 lanza FechaInvalidaException")
    fun reservarConNochesInvalidasLanzaExcepcion() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        assertThrows(FechaInvalidaException::class.java) {
            g.reservar("Ana", 101, 0)
        }
        assertThrows(FechaInvalidaException::class.java) {
            g.reservar("Ana", 101, -3)
        }
    }

    @Test
    @DisplayName("reservar en habitacion inexistente lanza HabitacionNoDisponibleException")
    fun reservarEnHabitacionInexistenteLanzaExcepcion() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        assertThrows(HabitacionNoDisponibleException::class.java) {
            g.reservar("Ana", 999, 2)
        }
    }

    @Test
    @DisplayName("mensajes de las excepciones no son vacios")
    fun mensajesDeExcepcionesNoSonVacios() {
        val e1 = FechaInvalidaException("noches debe ser > 0")
        val e2 = HabitacionNoDisponibleException("no existe")
        assertTrue(e1.message!!.isNotBlank())
        assertTrue(e2.message!!.isNotBlank())
    }

    @Test
    @DisplayName("reservasPorHuesped filtra por nombre")
    fun reservasPorHuespedFiltra() {
        val g = GestorReservas()
        g.registrarHabitacion(Habitacion(101, "Simple", 30000))
        g.registrarHabitacion(Habitacion(102, "Doble",  50000))
        g.reservar("Ana", 101, 2)
        g.reservar("Ana", 102, 1)
        g.reservar("Pedro", 101, 3)
        assertEquals(2, g.reservasPorHuesped("Ana").size)
        assertEquals(1, g.reservasPorHuesped("Pedro").size)
        assertEquals(0, g.reservasPorHuesped("Luisa").size)
    }
}
