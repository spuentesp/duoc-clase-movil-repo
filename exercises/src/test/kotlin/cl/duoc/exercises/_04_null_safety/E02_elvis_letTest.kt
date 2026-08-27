package cl.duoc.exercises._04_null_safety
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_elvis_letTest {
    @Test fun `string`() = assertEquals("hola", comoString("hola"))
    @Test fun `int como string`() = assertEquals("", comoString(5))
    @Test fun `null como string`() = assertEquals("", comoString(null))
    @Test fun `lista`() = assertEquals(3, safeCastLista(listOf(1,2,3)))
    @Test fun `no lista`() = assertEquals(-1, safeCastLista("hola"))
}
