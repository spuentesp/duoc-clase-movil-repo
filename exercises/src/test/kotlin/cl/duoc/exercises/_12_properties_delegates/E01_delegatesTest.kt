package cl.duoc.exercises._12_properties_delegates
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_delegatesTest {
    @Test fun `lazy`() = assertEquals("computado", valorGrande)
    @Test fun `vetoable`() {
        val c = Config()
        c.contador = 5
        assertEquals(5, c.contador)
        c.contador = -1
        assertEquals(5, c.contador) // veto
    }
    @Test fun `observable`() {
        val c = Config()
        c.nombre = "Ana"
        c.nombre = "Beto"
        assertEquals(listOf("Ana", "Beto"), c.log)
    }
}
