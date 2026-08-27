package cl.duoc.exercises._10_scope_functions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_scope_functionsTest {
    @Test fun `configurar`() {
        assertEquals("Hola Mundo", configurar().toString())
    }
    @Test fun `safe length`() {
        assertEquals(4, safeLength("hola"))
        assertEquals(0, safeLength(null))
    }
}
