package ejercicio1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 1 — Biblioteca: Publicacion, Libro, Revista")
class PublicacionTest {

    @Test
    @DisplayName("mostrarDetalle de Publicacion base incluye id, titulo y anio")
    fun publicacionBaseMuestraDetalle() {
        val p: Publicacion = Publicacion(1, "Obra generica", 2000)
        assertTrue(p.mostrarDetalle().contains("[1]"))
        assertTrue(p.mostrarDetalle().contains("Obra generica"))
        assertTrue(p.mostrarDetalle().contains("2000"))
    }

    @Test
    @DisplayName("Libro override de mostrarDetalle incluye autor y paginas")
    fun libroOverrideMuestraDetalle() {
        val libro = Libro(id = 10, titulo = "Kotlin in Action", anio = 2017,
            autor = "Dmitry Jemerov", paginas = 360)
        val detalle = libro.mostrarDetalle()
        assertTrue(detalle.contains("Kotlin in Action"))
        assertTrue(detalle.contains("Dmitry Jemerov"))
        assertTrue(detalle.contains("360"))
    }

    @Test
    @DisplayName("Revista override de mostrarDetalle incluye editor y numero")
    fun revistaOverrideMuestraDetalle() {
        val revista = Revista(id = 22, titulo = "National Geographic", anio = 2023,
            editor = "National Geographic Society", numero = 245)
        val detalle = revista.mostrarDetalle()
        assertTrue(detalle.contains("National Geographic"))
        assertTrue(detalle.contains("National Geographic Society"))
        assertTrue(detalle.contains("245"))
    }

    @Test
    @DisplayName("Polimorfismo: Libro y Revista son Publicacion")
    fun polimorfismoLibroYRevistaSonPublicacion() {
        val publicaciones: List<Publicacion> = listOf(
            Libro(1, "A", 2000, "Autor A", 100),
            Revista(2, "B", 2001, "Editor B", 5)
        )
        assertEquals(2, publicaciones.size)
        assertTrue(publicaciones.all { it.mostrarDetalle().isNotBlank() })
    }

    @Test
    @DisplayName("Libro expone sus propiedades especificas")
    fun libroExponePropiedades() {
        val libro = Libro(1, "Clean Code", 2008, "Robert C. Martin", 450)
        assertEquals("Robert C. Martin", libro.autor)
        assertEquals(450, libro.paginas)
        assertEquals(2008, libro.anio)
    }

    @Test
    @DisplayName("Revista expone sus propiedades especificas")
    fun revistaExponePropiedades() {
        val revista = Revista(1, "Time", 2024, "Time USA", 102)
        assertEquals("Time USA", revista.editor)
        assertEquals(102, revista.numero)
    }
}
