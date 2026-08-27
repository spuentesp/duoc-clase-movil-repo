package cl.duoc.exercises._18_coroutines_state_flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_state_flowTest {
    @Test fun `incrementar`() = runTest {
        val c = Contador()
        assertEquals(0, c.valor.value)
        c.incrementar()
        c.incrementar()
        c.incrementar()
        assertEquals(3, c.valor.value)
    }
}
