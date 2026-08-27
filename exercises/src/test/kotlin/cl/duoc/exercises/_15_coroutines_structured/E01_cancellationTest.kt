package cl.duoc.exercises._15_coroutines_structured
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
class E01_cancellationTest {
    @Test fun `job se cancela`() = runBlocking {
        val job = cancelarAntesDe()
        job.join()
        assertTrue(job.isCancelled)
    }
}
