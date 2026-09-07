package ejercicio2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ejercicio 2 - Tienda online: Producto e Inventario")
class ProductoTest {

    private val muestra = listOf(
        Producto(1, "Audifonos", 15000, "Tecnologia", 8),
        Producto(2, "Mouse",     8000,  "Tecnologia", 3),
        Producto(3, "Silla",     45000, "Hogar",       12),
        Producto(4, "Lampara",   12000, "Hogar",       2),
        Producto(5, "Cuaderno",  1500,  "Oficina",     40)
    )

    @Test
    @DisplayName("valorTotal suma precio por stock de cada producto")
    fun valorTotalSumaPrecioPorStock() {
        val inv = Inventario(muestra)
        val esperado = 15000 * 8 + 8000 * 3 + 45000 * 12 + 12000 * 2 + 1500 * 40
        assertEquals(esperado, inv.valorTotal())
    }

    @Test
    @DisplayName("conStockBajo retorna productos con stock menor al umbral")
    fun conStockBajoRetornaPorDebajoDeUmbral() {
        val inv = Inventario(muestra)
        val bajos = inv.conStockBajo(umbral = 5)
        assertEquals(2, bajos.size)
        assertTrue(bajos.all { it.stock < 5 })
        assertTrue(bajos.map { it.id }.containsAll(listOf(2, 4)))
    }

    @Test
    @DisplayName("agruparPorCategoria devuelve un map por categoria")
    fun agruparPorCategoriaDevuelveMap() {
        val inv = Inventario(muestra)
        val grupos = inv.agruparPorCategoria()
        assertEquals(2, grupos["Tecnologia"]?.size)
        assertEquals(2, grupos["Hogar"]?.size)
        assertEquals(1, grupos["Oficina"]?.size)
    }

    @Test
    @DisplayName("topMasCaros retorna los N mas caros ordenados descendente")
    fun topMasCarosRetornaNMasCaros() {
        val inv = Inventario(muestra)
        val top = inv.topMasCaros(2)
        assertEquals(2, top.size)
        assertEquals(45000, top[0].precio)
        assertEquals(15000, top[1].precio)
    }

    @Test
    @DisplayName("Inventario vacio tiene valorTotal 0 y grupos vacios")
    fun inventarioVacio() {
        val inv = Inventario(emptyList())
        assertEquals(0, inv.valorTotal())
        assertEquals(emptyList<Producto>(), inv.conStockBajo())
        assertTrue(inv.agruparPorCategoria().isEmpty())
    }
}
