package cl.duoc.exercises._02_control_de_flujo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_when_sin_argumentoTest {
    @Test fun `positivo`() = assertEquals("Número positivo", describir(5))
    @Test fun `negativo`() = assertEquals("Número negativo", describir(-3.14))
    @Test fun `cero`() = assertEquals("Cero", describir(0))
    @Test fun `string vacio`() = assertEquals("Texto vacío", describir(""))
    @Test fun `string`() = assertEquals("Texto", describir("hola"))
    @Test fun `bool`() = assertEquals("Boolean", describir(true))
    @Test fun `otro`() = assertEquals("Otro", describir(listOf(1, 2)))
}
