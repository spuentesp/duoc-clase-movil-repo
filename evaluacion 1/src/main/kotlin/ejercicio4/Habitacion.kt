package ejercicio4

class FechaInvalidaException(msg: String) : Exception(msg)

class HabitacionNoDisponibleException(msg: String) : Exception(msg)

data class Habitacion(
    val numero: Int,
    val tipo: String,
    val precioNoche: Int
)

data class Reserva(
    val huesped: String,
    val habitacion: Habitacion,
    val noches: Int
)

class GestorReservas {
    private val habitaciones = mutableListOf<Habitacion>()
    private val reservas = mutableListOf<Reserva>()

    fun registrarHabitacion(h: Habitacion) {
        habitaciones += h
    }

    fun reservar(huesped: String, numero: Int, noches: Int): Reserva {
        if (noches <= 0) {
            throw FechaInvalidaException("Noches debe ser mayor a 0 (recibido: $noches)")
        }
        val habitacion = habitaciones.firstOrNull { it.numero == numero }
            ?: throw HabitacionNoDisponibleException("Habitacion $numero no existe o no esta disponible")
        return Reserva(huesped, habitacion, noches).also { reservas += it }
    }

    fun reservasPorHuesped(nombre: String): List<Reserva> =
        reservas.filter { it.huesped == nombre }
}
