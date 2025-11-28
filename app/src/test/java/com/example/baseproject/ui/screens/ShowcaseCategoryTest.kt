package com.example.baseproject.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TouchApp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Pruebas unitarias para la data class ShowcaseCategory
 *
 * Demuestra cómo probar:
 * - Data classes
 * - Igualdad de objetos
 * - Propiedades de data classes
 * - Funciones generadas automáticamente (copy, equals, hashCode)
 */
class ShowcaseCategoryTest {

    @Test
    fun `crear ShowcaseCategory con todos los parametros funciona correctamente`() {
        // Given
        val title = "Buttons"
        val icon = Icons.Default.TouchApp
        val route = "buttons"
        val description = "Botones y variantes"

        // When
        val category = ShowcaseCategory(
            title = title,
            icon = icon,
            route = route,
            description = description
        )

        // Then
        assertEquals(title, category.title)
        assertEquals(icon, category.icon)
        assertEquals(route, category.route)
        assertEquals(description, category.description)
    }

    @Test
    fun `dos ShowcaseCategory con los mismos valores son iguales`() {
        // Given
        val category1 = ShowcaseCategory(
            title = "Buttons",
            icon = Icons.Default.TouchApp,
            route = "buttons",
            description = "Botones y variantes"
        )

        val category2 = ShowcaseCategory(
            title = "Buttons",
            icon = Icons.Default.TouchApp,
            route = "buttons",
            description = "Botones y variantes"
        )

        // Then
        assertEquals(category1, category2)
        assertEquals(category1.hashCode(), category2.hashCode())
    }

    @Test
    fun `dos ShowcaseCategory con valores diferentes no son iguales`() {
        // Given
        val category1 = ShowcaseCategory(
            title = "Buttons",
            icon = Icons.Default.TouchApp,
            route = "buttons",
            description = "Botones y variantes"
        )

        val category2 = ShowcaseCategory(
            title = "Cards",
            icon = Icons.Default.TouchApp,
            route = "cards",
            description = "Tarjetas y contenedores"
        )

        // Then
        assertNotEquals(category1, category2)
    }

    @Test
    fun `copy de ShowcaseCategory funciona correctamente`() {
        // Given
        val original = ShowcaseCategory(
            title = "Buttons",
            icon = Icons.Default.TouchApp,
            route = "buttons",
            description = "Botones y variantes"
        )

        // When
        val modified = original.copy(title = "Modified Buttons")

        // Then
        assertEquals("Modified Buttons", modified.title)
        assertEquals(original.icon, modified.icon)
        assertEquals(original.route, modified.route)
        assertEquals(original.description, modified.description)
        assertNotEquals(original, modified)
    }

    @Test
    fun `showcaseCategories lista contiene 11 categorias`() {
        // Then
        assertEquals(11, showcaseCategories.size)
    }

    @Test
    fun `showcaseCategories lista contiene categoria de Buttons`() {
        // When
        val buttonsCategory = showcaseCategories.find { it.route == "buttons" }

        // Then
        assertEquals("Buttons", buttonsCategory?.title)
        assertEquals("buttons", buttonsCategory?.route)
    }
}
