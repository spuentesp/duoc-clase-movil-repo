package cl.duoc.exercises._11_extension_functions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
class E01_extensionTest {
    @Test fun `segundo ok`() = assertEquals(2, listOf(1,2,3).segundo)
    @Test fun `segundo vacio`() = assertEquals(-1, emptyList<Int>().segundo)
    @Test fun `segundo uno`() = assertEquals(-1, listOf(1).segundo)
    @Test fun `palindromo`() = assertTrue("Anita lava la tina".esPalindromo())
    @Test fun `no palindromo`() = assertFalse("hola".esPalindromo())
}
