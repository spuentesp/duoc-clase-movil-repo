package cl.duoc.exercises._05_colecciones
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_list_set_mapTest {
    @Test fun `sin duplicados`() = assertEquals(listOf(1,2,3), sinDuplicados(listOf(1,2,2,3,1)))
    @Test fun `vacio`() = assertEquals(emptyList<Int>(), sinDuplicados(emptyList()))
    @Test fun `invertir`() {
        assertEquals(mapOf(1 to "a", 2 to "b"), invertirMap(mapOf("a" to 1, "b" to 2)))
    }
}
