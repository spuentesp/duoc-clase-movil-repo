package com.example.baseproject.utils

/**
 * Clase utilitaria para validar strings comunes en aplicaciones
 */
object StringValidator {

    /**
     * Valida si un email tiene un formato válido
     */
    fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }

    /**
     * Valida si una contraseña cumple con los requisitos mínimos:
     * - Al menos 8 caracteres
     * - Al menos una letra mayúscula
     * - Al menos una letra minúscula
     * - Al menos un número
     */
    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false

        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return hasUpperCase && hasLowerCase && hasDigit
    }

    /**
     * Valida si un teléfono chileno tiene un formato válido
     * Acepta formatos: +56912345678, 912345678, +56 9 1234 5678
     */
    fun isValidChileanPhone(phone: String): Boolean {
        val cleanPhone = phone.replace(Regex("[\\s-]"), "")

        // Con código de país
        val withCountryCode = "^\\+569\\d{8}$".toRegex()
        // Sin código de país
        val withoutCountryCode = "^9\\d{8}$".toRegex()

        return withCountryCode.matches(cleanPhone) || withoutCountryCode.matches(cleanPhone)
    }

    /**
     * Valida si un RUT chileno es válido
     * Formato aceptado: 12345678-9 o 12.345.678-9
     */
    fun isValidRUT(rut: String): Boolean {
        val cleanRut = rut.replace(Regex("[.-]"), "")

        if (cleanRut.length < 2) return false

        val rutDigits = cleanRut.dropLast(1)
        val verifier = cleanRut.last().toString()

        if (!rutDigits.all { it.isDigit() }) return false

        return calculateRutVerifier(rutDigits) == verifier
    }

    /**
     * Calcula el dígito verificador de un RUT
     */
    private fun calculateRutVerifier(rutDigits: String): String {
        var sum = 0
        var multiplier = 2

        for (i in rutDigits.length - 1 downTo 0) {
            sum += rutDigits[i].toString().toInt() * multiplier
            multiplier = if (multiplier == 7) 2 else multiplier + 1
        }

        val remainder = 11 - (sum % 11)

        return when (remainder) {
            11 -> "0"
            10 -> "K"
            else -> remainder.toString()
        }
    }

    /**
     * Valida si un nombre de usuario es válido:
     * - 3-20 caracteres
     * - Solo letras, números y guiones bajos
     * - Debe empezar con una letra
     */
    fun isValidUsername(username: String): Boolean {
        if (username.length !in 3..20) return false
        val usernameRegex = "^[a-zA-Z][a-zA-Z0-9_]*$".toRegex()
        return usernameRegex.matches(username)
    }
}
