package cl.duoc.exercises._06_clases_y_objetos
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_clase_basicaTest {
    @Test fun `presentar`() = assertEquals("Soy Ana y tengo 30 años", Persona("Ana", 30).presentarse())
    @Test fun `propiedades`() {
        val p = Persona("Beto", 25)
        assertEquals("Beto", p.nombre)
        assertEquals(25, p.edad)
    }
}