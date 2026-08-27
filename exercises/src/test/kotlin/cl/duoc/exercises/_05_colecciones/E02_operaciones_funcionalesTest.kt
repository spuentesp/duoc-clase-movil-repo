package cl.duoc.exercises._05_colecciones
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_operaciones_funcionalesTest {
    @Test fun `top3`() = assertEquals(listOf(9, 7, 5), top3MasGrandes(listOf(1,5,3,9,7,2)))
    @Test fun `agrupar`() = assertEquals(
        mapOf("pares" to listOf(2,4), "impares" to listOf(1,3)),
        agruparPorParidad(listOf(1,2,3,4))
    )
    @Test fun `positivos`() = assertEquals(true, sonTodosPositivos(listOf(1,2,3)))
    @Test fun `no positivos`() = assertEquals(false, sonTodosPositivos(listOf(1,-2,3)))
}
