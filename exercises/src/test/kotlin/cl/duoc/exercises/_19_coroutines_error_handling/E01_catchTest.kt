package cl.duoc.exercises._19_coroutines_error_handling
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_catchTest {
    @Test fun `catch recupera`() = runTest {
        val result = flowSeguro().toList()
        assertEquals(listOf("1", "2", "error"), result)
    }
}
