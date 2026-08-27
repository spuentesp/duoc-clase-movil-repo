package cl.duoc.exercises._08_interfaces_y_herencia
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_interfacesTest {
    @Test fun `circulo`() {
        val c = Circulo(2.0)
        assertEquals(Math.PI * 4, c.area, 0.0001)
        assertEquals("Forma con área ${c.area}", c.describir())
    }
    @Test fun `cuadrado`() {
        val q = Cuadrado(3.0)
        assertEquals(9.0, q.area, 0.0001)
    }
}