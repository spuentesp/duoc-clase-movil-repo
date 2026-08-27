package cl.duoc.exercises._17_coroutines_flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_flow_basicsTest {
    @Test fun `suma flow`() = runTest {
        assertEquals(15, sumarFlow(5)) // 1+2+3+4+5
    }
}
