package ejercicio5

enum class TipoCuenta { CORRIENTE, AHORRO, CREDITO }

data class Cuenta(
    val titular: String,
    val saldo: Int,
    val tipo: TipoCuenta
)

class Banco(val cuentas: List<Cuenta>) {

    fun aplicarOperacion(op: (Cuenta) -> Cuenta): List<Cuenta> =
        cuentas.map(op)

    fun saldoTotal(): Int =
        cuentas.sumOf { it.saldo }

    fun cuentasPorTipo(): Map<TipoCuenta, List<Cuenta>> =
        cuentas.groupBy { it.tipo }

    fun saldosAcumuladosPorTipo(): Map<TipoCuenta, Int> =
        cuentas.groupBy { it.tipo }.mapValues { (_, lista) -> lista.sumOf { it.saldo } }
}