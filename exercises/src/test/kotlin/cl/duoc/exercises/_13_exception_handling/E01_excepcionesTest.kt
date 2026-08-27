package cl.duoc.exercises._13_exception_handling
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
class E01_excepcionesTest {
    @Test fun `parsear ok`() = assertEquals(42, parsearEntero("42"))
    @Test fun `parsear invalido`() = assertEquals(0, parsearEntero("abc"))
    @Test fun `retirar ok`() = assertEquals(50.0, retirar(100.0, 50.0))
    @Test fun `retirar insuficiente`() {
        val ex = assertThrows(SaldoInsuficienteException::class.java) { retirar(30.0, 50.0) }
        assertEquals(20.0, ex.deficit, 0.0001)
    }
}
