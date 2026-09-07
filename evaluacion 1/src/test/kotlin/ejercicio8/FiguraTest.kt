package ejercicio8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sqrt

@DisplayName("Ejercicio 8 - Figuras geometricas: constructores y propiedades")
class FiguraTest {

    private val tolerancia = 1e-9

    @Test
    @DisplayName("Circulo expone color, area y perimetro correctos")
    fun circuloAreaYPerimetro() {
        val c = Figura.Circulo("rojo", radio = 2.0)
        assertEquals("rojo", c.color)
        assertEquals(PI * 4.0, c.area(), tolerancia)
        assertEquals(2 * PI * 2.0, c.perimetro(), tolerancia)
    }

    @Test
    @DisplayName("Circulo con constructor sin radio usa radio = 1.0 por defecto")
    fun circuloConstructorSecundario() {
        val c = Figura.Circulo("azul")
        assertEquals(1.0, c.radio)
        assertEquals(PI, c.area(), tolerancia)
    }

    @Test
    @DisplayName("Rectangulo expone ancho, alto, area y perimetro")
    fun rectanguloAreaYPerimetro() {
        val r = Figura.Rectangulo("verde", ancho = 3.0, alto = 4.0)
        assertEquals(12.0, r.area(), tolerancia)
        assertEquals(14.0, r.perimetro(), tolerancia)
        assertTrue(!r.esCuadrado)
    }

    @Test
    @DisplayName("Rectangulo con constructor lado unico es cuadrado")
    fun rectanguloConstructorSecundario() {
        val r = Figura.Rectangulo("amarillo", lado = 5.0)
        assertEquals(5.0, r.ancho)
        assertEquals(5.0, r.alto)
        assertTrue(r.esCuadrado)
    }

    @Test
    @DisplayName("TrianguloEquilatero calcula area y perimetro correctos")
    fun trianguloAreaYPerimetro() {
        val t = Figura.TrianguloEquilatero("negro", lado = 2.0)
        val areaEsperada = (sqrt(3.0) / 4.0) * 4.0
        assertEquals(areaEsperada, t.area(), tolerancia)
        assertEquals(6.0, t.perimetro(), tolerancia)
    }

    @Test
    @DisplayName("TrianguloEquilatero con lado <= 0 lanza IllegalArgumentException")
    fun trianguloLadoInvalido() {
        assertThrows(IllegalArgumentException::class.java) {
            Figura.TrianguloEquilatero("negro", lado = 0.0)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Figura.TrianguloEquilatero("negro", lado = -1.0)
        }
    }

    @Test
    @DisplayName("Cada figura expone su toString con su tipo y propiedades")
    fun toStringIncluyeTipoYPropiedades() {
        val c = Figura.Circulo("rojo", 2.0)
        val r = Figura.Rectangulo("verde", 3.0, 4.0)
        val t = Figura.TrianguloEquilatero("negro", 2.0)
        assertTrue(c.toString().contains("Circulo"))
        assertTrue(c.toString().contains("2.0"))
        assertTrue(r.toString().contains("Rectangulo"))
        assertTrue(t.toString().contains("TrianguloEquilatero"))
    }
}
