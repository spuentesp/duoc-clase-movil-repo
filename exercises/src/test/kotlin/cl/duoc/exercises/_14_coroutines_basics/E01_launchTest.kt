package cl.duoc.exercises._14_coroutines_basics
import org.junit.jupiter.api.Test
class E01_launchTest {
    @Test fun `lanza y espera`() {
        // No hay assert: si no se lanza excepción, runBlocking ejecutó la coroutine.
        lanzarYSaludar()
    }
}
