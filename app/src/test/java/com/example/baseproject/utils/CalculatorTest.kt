package com.example.baseproject.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

/**
 * Pruebas unitarias de ejemplo para la clase Calculator
 *
 * Esta clase demuestra cómo escribir pruebas unitarias en Android:
 * - @Before: Se ejecuta antes de cada test
 * - @Test: Marca un método como una prueba unitaria
 * - assertEquals: Verifica que dos valores sean iguales
 * - assertThrows: Verifica que se lance una excepción
 */
class CalculatorTest {

    private lateinit var calculator: Calculator

    /**
     * Se ejecuta antes de cada prueba
     */
    @Before
    fun setUp() {
        calculator = Calculator()
    }

    /**
     * Prueba simple de suma
     */
    @Test
    fun `add dos numeros positivos devuelve la suma correcta`() {
        // Given (Dado)
        val a = 5
        val b = 3

        // When (Cuando)
        val result = calculator.add(a, b)

        // Then (Entonces)
        assertEquals(8, result)
    }

    /**
     * Prueba de suma con números negativos
     */
    @Test
    fun `add con numeros negativos funciona correctamente`() {
        val result = calculator.add(-5, -3)
        assertEquals(-8, result)
    }

    /**
     * Prueba de resta
     */
    @Test
    fun `subtract funciona correctamente`() {
        val result = calculator.subtract(10, 3)
        assertEquals(7, result)
    }

    /**
     * Prueba de multiplicación
     */
    @Test
    fun `multiply funciona correctamente`() {
        val result = calculator.multiply(4, 5)
        assertEquals(20, result)
    }

    /**
     * Prueba de división normal
     */
    @Test
    fun `divide funciona correctamente`() {
        val result = calculator.divide(10, 2)
        assertEquals(5, result)
    }

    /**
     * Prueba que verifica que se lance una excepción al dividir por cero
     */
    @Test
    fun `divide por cero lanza IllegalArgumentException`() {
        assertThrows(IllegalArgumentException::class.java) {
            calculator.divide(10, 0)
        }
    }
}
