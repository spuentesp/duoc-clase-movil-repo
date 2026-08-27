package cl.duoc.exercises._03_funciones_y_lambdas
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_lambdasTest {
    @Test fun `aplicar doble`() = assertEquals(listOf(2,4,6), aplicarDoble(listOf(1,2,3)) { it * 2 })
    @Test fun `filtrar`() = assertEquals(listOf(2,4), filtrar(listOf(1,2,3,4)) { it % 2 == 0 })
    @Test fun `reducir`() = assertEquals(15, reducirA(listOf(1,2,3,4,5), 0) { acc, x -> acc + x })
}
