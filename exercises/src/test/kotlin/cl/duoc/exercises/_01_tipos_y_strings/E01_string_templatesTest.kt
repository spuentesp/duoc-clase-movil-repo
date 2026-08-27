package cl.duoc.exercises._01_tipos_y_strings
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_string_templatesTest {
    @Test fun `saludar`() = assertEquals("Hola, Ana!", saludar("Ana"))
    @Test fun `presentar`() = assertEquals("Soy Pedro y tengo 30 años", presentar("Pedro", 30))
}
