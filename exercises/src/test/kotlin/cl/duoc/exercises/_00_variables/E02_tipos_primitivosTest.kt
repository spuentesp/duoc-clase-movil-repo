package cl.duoc.exercises._00_variables

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class E02_tipos_primitivosTest {
    @Test
    fun `detecta Int`() = assertEquals("Int", describirTipo(5))
    @Test
    fun `detecta Long`() = assertEquals("Long", describirTipo(5L))
    @Test
    fun `detecta Double`() = assertEquals("Double", describirTipo(3.14))
    @Test
    fun `detecta Boolean`() = assertEquals("Boolean", describirTipo(true))
}
