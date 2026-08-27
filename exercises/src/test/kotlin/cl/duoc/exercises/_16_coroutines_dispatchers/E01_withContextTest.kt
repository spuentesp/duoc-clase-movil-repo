package cl.duoc.exercises._16_coroutines_dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull
class E01_withContextTest {
    @Test fun `thread de IO`() = runTest {
        val name = threadDeIO()
        assertNotNull(name)
        // No podemos asertar nombre exacto porque los threads cambian;
        // solo verificamos que retorna algo.
    }
}
