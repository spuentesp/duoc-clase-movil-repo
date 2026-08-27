package cl.duoc.exercises._09_generics
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
class E01_genericsTest {
    @Test fun `primero`() = assertEquals(1, primero(listOf(1,2,3)))
    @Test fun `vacio`() = assertNull(primero(emptyList<Int>()))
    @Test fun `convertir`() = assertEquals("a-b-c", convertirAString(listOf("a","b","c")))
}