package cl.duoc.exercises._00_variables

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class E01_var_valTest {
    @Test
    fun `sumar funciona`() {
        assertEquals(5, sumar(2, 3))
        assertEquals(0, sumar(-5, 5))
    }

    @Test
    fun `contador inmutable retorna par`() {
        assertEquals(Pair(0, 1), contadorInmutable(0))
        assertEquals(Pair(7, 8), contadorInmutable(7))
    }
}
