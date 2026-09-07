package ejercicio6

enum class TipoEmpleado { JUNIOR, SENIOR, GERENTE }

class Empleado(
    val nombre: String,
    val horasTrabajadas: Int,
    val tipo: TipoEmpleado
) {
    init {
        require(horasTrabajadas >= 0) { "Horas no puede ser negativo (recibido: $horasTrabajadas)" }
    }

    val tarifaPorHora: Int = when (tipo) {
        TipoEmpleado.JUNIOR  -> 8_000
        TipoEmpleado.SENIOR  -> 15_000
        TipoEmpleado.GERENTE -> 25_000
    }

    val bono: Int = if (horasTrabajadas > 160) (horasTrabajadas - 160) * 5_000 else 0

    fun calcularSueldoBruto(): Int = horasTrabajadas * tarifaPorHora + bono

    fun calcularDescuento(): Int {
        val bruto = calcularSueldoBruto()
        return when {
            bruto < 500_000   -> 0
            bruto < 1_000_000 -> (bruto * 8) / 100
            else              -> (bruto * 15) / 100
        }
    }

    fun calcularSueldoNeto(): Int = calcularSueldoBruto() - calcularDescuento()
}
