package cl.duoc.exercises._06_clases_y_objetos
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
class E02_init_blocksTest {
    @Test fun `saldo positivo`() = assertEquals(100.0, CuentaBancaria(100.0).saldo)
    @Test fun `sin argumentos`() = assertEquals(0.0, CuentaBancaria().saldo)
    @Test fun `saldo negativo lanza`() = assertThrows(IllegalArgumentException::class.java) { CuentaBancaria(-10.0) }
}