package cl.duoc.exercises._15_coroutines_structured
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_supervisorScopeTest {
    @Test fun `hermano sobrevive`() = runBlocking {
        val result = supervisarHermanos()
        assertEquals(listOf("B"), result)
    }
}
