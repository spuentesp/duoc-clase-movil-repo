package cl.duoc.exercises._07_data_classes_sealed
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
class E01_data_classTest {
    @Test fun `swap`() = assertEquals(Punto(2, 1), Punto(1, 2).swap())
    @Test fun `equals`() = assertEquals(Punto(1, 2), Punto(1, 2))
    @Test fun `no equals`() = assertNotEquals(Punto(1, 2), Punto(2, 1))
    @Test fun `componentN`() {
        val (x, y) = Punto(7, 9)
        assertEquals(7, x)
        assertEquals(9, y)
    }
}