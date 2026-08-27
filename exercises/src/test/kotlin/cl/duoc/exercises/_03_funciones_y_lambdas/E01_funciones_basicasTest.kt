package cl.duoc.exercises._03_funciones_y_lambdas
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_funciones_basicasTest {
    @Test fun `default`() = assertEquals("Hola, mundo", saludo())
    @Test fun `con nombre`() = assertEquals("Hola, Ana", saludo("Ana"))
    @Test fun `concatenar`() = assertEquals("a-b-c", concatenar("-", "a", "b", "c"))
    @Test fun `infix`() = assertEquals(15, 3 multiplicadoPor 5)
}
