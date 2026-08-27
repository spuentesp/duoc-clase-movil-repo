package cl.duoc.exercises._14_coroutines_basics
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_async_awaitTest {
    @Test fun `suma en paralelo`() = runTest {
        assertEquals(13, sumarParalelo(2, 3))  // 2*2 + 3*3 = 4 + 9
    }
}
