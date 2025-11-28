package com.example.baseproject.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pruebas unitarias para StringValidator
 *
 * Demuestra cómo probar:
 * - Funciones de validación
 * - Regex patterns
 * - Casos edge (bordes)
 * - Casos válidos e inválidos
 */
class StringValidatorTest {

    // ========== PRUEBAS DE EMAIL ==========

    @Test
    fun `email valido retorna true`() {
        assertTrue(StringValidator.isValidEmail("usuario@ejemplo.com"))
        assertTrue(StringValidator.isValidEmail("test.user@dominio.cl"))
        assertTrue(StringValidator.isValidEmail("user+tag@gmail.com"))
    }

    @Test
    fun `email sin arroba retorna false`() {
        assertFalse(StringValidator.isValidEmail("usuarioejemplo.com"))
    }

    @Test
    fun `email sin dominio retorna false`() {
        assertFalse(StringValidator.isValidEmail("usuario@"))
    }

    @Test
    fun `email vacio retorna false`() {
        assertFalse(StringValidator.isValidEmail(""))
        assertFalse(StringValidator.isValidEmail("   "))
    }

    // ========== PRUEBAS DE CONTRASEÑA ==========

    @Test
    fun `contrasena valida retorna true`() {
        assertTrue(StringValidator.isValidPassword("Password123"))
        assertTrue(StringValidator.isValidPassword("MiClave2024"))
    }

    @Test
    fun `contrasena sin mayuscula retorna false`() {
        assertFalse(StringValidator.isValidPassword("password123"))
    }

    @Test
    fun `contrasena sin minuscula retorna false`() {
        assertFalse(StringValidator.isValidPassword("PASSWORD123"))
    }

    @Test
    fun `contrasena sin numero retorna false`() {
        assertFalse(StringValidator.isValidPassword("PasswordABC"))
    }

    @Test
    fun `contrasena menor a 8 caracteres retorna false`() {
        assertFalse(StringValidator.isValidPassword("Pass123"))
    }

    // ========== PRUEBAS DE TELÉFONO CHILENO ==========

    @Test
    fun `telefono chileno valido retorna true`() {
        assertTrue(StringValidator.isValidChileanPhone("+56912345678"))
        assertTrue(StringValidator.isValidChileanPhone("912345678"))
        assertTrue(StringValidator.isValidChileanPhone("+56 9 1234 5678"))
    }

    @Test
    fun `telefono sin codigo de area retorna false`() {
        assertFalse(StringValidator.isValidChileanPhone("12345678"))
    }

    @Test
    fun `telefono con longitud incorrecta retorna false`() {
        assertFalse(StringValidator.isValidChileanPhone("+5691234567")) // muy corto
        assertFalse(StringValidator.isValidChileanPhone("+569123456789")) // muy largo
    }

    // ========== PRUEBAS DE RUT CHILENO ==========

    @Test
    fun `RUT valido retorna true`() {
        assertTrue(StringValidator.isValidRUT("12345678-5"))
        assertTrue(StringValidator.isValidRUT("11111111-1"))
        assertTrue(StringValidator.isValidRUT("12.345.678-5"))
    }

    @Test
    fun `RUT con digito verificador incorrecto retorna false`() {
        assertFalse(StringValidator.isValidRUT("12345678-9"))
        assertFalse(StringValidator.isValidRUT("11111111-2"))
    }

    @Test
    fun `RUT con formato invalido retorna false`() {
        assertFalse(StringValidator.isValidRUT("123"))
        assertFalse(StringValidator.isValidRUT(""))
        assertFalse(StringValidator.isValidRUT("abcdefgh-1"))
    }

    @Test
    fun `RUT con K como verificador funciona`() {
        // RUT válido con K
        assertTrue(StringValidator.isValidRUT("12345678-K"))
    }

    // ========== PRUEBAS DE NOMBRE DE USUARIO ==========

    @Test
    fun `username valido retorna true`() {
        assertTrue(StringValidator.isValidUsername("usuario123"))
        assertTrue(StringValidator.isValidUsername("user_name"))
        assertTrue(StringValidator.isValidUsername("abc"))
    }

    @Test
    fun `username que empieza con numero retorna false`() {
        assertFalse(StringValidator.isValidUsername("123usuario"))
    }

    @Test
    fun `username muy corto retorna false`() {
        assertFalse(StringValidator.isValidUsername("ab"))
    }

    @Test
    fun `username muy largo retorna false`() {
        assertFalse(StringValidator.isValidUsername("a".repeat(21)))
    }

    @Test
    fun `username con caracteres especiales retorna false`() {
        assertFalse(StringValidator.isValidUsername("user@name"))
        assertFalse(StringValidator.isValidUsername("user-name"))
        assertFalse(StringValidator.isValidUsername("user.name"))
    }
}
