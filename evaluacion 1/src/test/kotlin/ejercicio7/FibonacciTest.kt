package ejercicio7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 7 - Fibonacci: ciclos e iteraciones")
class FibonacciTest {

    private val gen = GeneradorFibonacci()

    @Test
    @DisplayName("primerosN(0) retorna lista vacia")
    fun primerosNCero() {
        assertEquals(emptyList<Long>(), gen.primerosN(0))
    }

    @Test
    @DisplayName("primerosN(1) retorna solo 0")
    fun primerosNUno() {
        assertEquals(listOf(0L), gen.primerosN(1))
    }

    @Test
    @DisplayName("primerosN(7) retorna [0, 1, 1, 2, 3, 5, 8]")
    fun primerosNSiete() {
        assertEquals(listOf(0L, 1L, 1L, 2L, 3L, 5L, 8L), gen.primerosN(7))
    }

    @Test
    @DisplayName("primerosN(10) produce 10 elementos")
    fun primerosNDiez() {
        assertEquals(10, gen.primerosN(10).size)
        assertEquals(34L, gen.primerosN(10).last())
    }

    @Test
    @DisplayName("sumaHasta(7) suma los primeros 7 terminos")
    fun sumaHastaSiete() {
        val esperado = 0L + 1L + 1L + 2L + 3L + 5L + 8L
        assertEquals(esperado, gen.sumaHasta(7))
    }

    @Test
    @DisplayName("menoresQue(100) produce los fibonacci menores a 100")
    fun menoresQueCien() {
        val resultado = gen.menoresQue(100)
        assertEquals(listOf(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L, 89L), resultado)
    }

    @Test
    @DisplayName("n negativo lanza IllegalArgumentException")
    fun nNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException::class.java) { gen.primerosN(-1) }
        assertThrows(IllegalArgumentException::class.java) { gen.sumaHasta(-5) }
    }

    @Test
    @DisplayName("primerosN genera una secuencia monotona despues del termino 2")
    fun secuenciaCrece() {
        val lista = gen.primerosN(15)
        for (i in 3 until lista.size) {
            assertTrue(lista[i] > lista[i - 1], "Pos $i no crece: ${lista[i]} <= ${lista[i - 1]}")
        }
    }
}
