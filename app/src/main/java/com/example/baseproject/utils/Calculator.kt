package com.example.baseproject.utils

/**
 * Una clase simple de calculadora para demostrar pruebas unitarias
 */
class Calculator {
    /**
     * Suma dos números
     */
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    /**
     * Resta dos números
     */
    fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    /**
     * Multiplica dos números
     */
    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    /**
     * Divide dos números
     * @throws IllegalArgumentException si el divisor es cero
     */
    fun divide(a: Int, b: Int): Int {
        if (b == 0) {
            throw IllegalArgumentException("No se puede dividir por cero")
        }
        return a / b
    }
}
