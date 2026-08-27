package cl.duoc.exercises._02_control_de_flujo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E03_loopsTest {
    @Test fun `sumar pares hasta 10`() = assertEquals(30, sumarPares(10))
    @Test fun `sumar pares hasta 1`() = assertEquals(0, sumarPares(1))
    @Test fun `elemento en posicion`() = assertEquals(3, primerImparEnPosicion(listOf(1,2,3,4,5), 2))
    @Test fun `fuera de rango`() = assertEquals(-1, primerImparEnPosicion(listOf(1,2,3), 10))
}
