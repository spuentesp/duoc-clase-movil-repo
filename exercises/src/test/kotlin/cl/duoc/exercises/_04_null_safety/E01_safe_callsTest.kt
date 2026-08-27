package cl.duoc.exercises._04_null_safety
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_safe_callsTest {
    @Test fun `null es 0`() = assertEquals(0, longitudSegura(null))
    @Test fun `hola es 4`() = assertEquals(4, longitudSegura("hola"))
    @Test fun `vacio es 0`() = assertEquals(0, longitudSegura(""))
    @Test fun `primer char`() = assertEquals('A', primerCaracter("Ana"))
    @Test fun `null primer`() = assertEquals(null, primerCaracter(null))
    @Test fun `vacio primer`() = assertEquals(null, primerCaracter(""))
}
