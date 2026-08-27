package cl.duoc.exercises._02_control_de_flujo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_when_expresionesTest {
    @Test fun `suspenso`() = assertEquals("Suspenso", clasificarNota(3))
    @Test fun `aprobado`() = assertEquals("Aprobado", clasificarNota(5))
    @Test fun `notable`() = assertEquals("Notable", clasificarNota(7))
    @Test fun `sobresaliente`() = assertEquals("Sobresaliente", clasificarNota(10))
    @Test fun `invalida`() = assertEquals("Inválida", clasificarNota(11))
}
