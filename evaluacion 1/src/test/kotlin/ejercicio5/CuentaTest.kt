package ejercicio5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 5 - Cuentas bancarias: lambdas y funciones de orden superior")
class CuentaTest {

    private val muestra = listOf(
        Cuenta("Ana",   100_000, TipoCuenta.CORRIENTE),
        Cuenta("Pedro",  50_000, TipoCuenta.AHORRO),
        Cuenta("Luisa", 200_000, TipoCuenta.CORRIENTE),
        Cuenta("Sofia",  30_000, TipoCuenta.CREDITO)
    )

    @Test
    @DisplayName("saldoTotal suma el saldo de todas las cuentas")
    fun saldoTotalSumaTodas() {
        val b = Banco(muestra)
        assertEquals(100_000 + 50_000 + 200_000 + 30_000, b.saldoTotal())
    }

    @Test
    @DisplayName("cuentasPorTipo agrupa por TipoCuenta")
    fun cuentasPorTipoAgrupa() {
        val b = Banco(muestra)
        val grupos = b.cuentasPorTipo()
        assertEquals(2, grupos[TipoCuenta.CORRIENTE]?.size)
        assertEquals(1, grupos[TipoCuenta.AHORRO]?.size)
        assertEquals(1, grupos[TipoCuenta.CREDITO]?.size)
    }

    @Test
    @DisplayName("saldosAcumuladosPorTipo suma por tipo")
    fun saldosAcumuladosPorTipoSuma() {
        val b = Banco(muestra)
        val mapa = b.saldosAcumuladosPorTipo()
        assertEquals(300_000, mapa[TipoCuenta.CORRIENTE])
        assertEquals(50_000,  mapa[TipoCuenta.AHORRO])
        assertEquals(30_000,  mapa[TipoCuenta.CREDITO])
    }

    @Test
    @DisplayName("aplicarOperacion recibe una lambda y la aplica a cada cuenta")
    fun aplicarOperacionAplicaLambda() {
        val b = Banco(muestra)
        val duplicadas = b.aplicarOperacion { c -> c.copy(saldo = c.saldo * 2) }
        assertEquals(200_000, duplicadas.first { it.titular == "Ana" }.saldo)
        assertEquals(100_000, duplicadas.first { it.titular == "Pedro" }.saldo)
    }

    @Test
    @DisplayName("aplicarOperacion con lambda de identidad devuelve cuentas equivalentes")
    fun aplicarOperacionIdentidad() {
        val b = Banco(muestra)
        val mismo = b.aplicarOperacion { it }
        assertEquals(muestra, mismo)
    }

    @Test
    @DisplayName("Banco con cuentas vacias tiene saldoTotal 0")
    fun bancoVacio() {
        val b = Banco(emptyList())
        assertEquals(0, b.saldoTotal())
        assertTrue(b.cuentasPorTipo().isEmpty())
        assertTrue(b.saldosAcumuladosPorTipo().isEmpty())
    }
}