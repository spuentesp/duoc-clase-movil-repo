import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertTrue

class GestorPedidosTest {

    private val catalogo = inicializarCatalogo().productos

    @Test
    fun `catalogo descarta el producto con precio negativo`() {
        val resultado = inicializarCatalogo()
        assertEquals(4, resultado.productos.size)
        assertEquals(1, resultado.errores.size)
    }

    @Test
    fun `producto con precio negativo lanza excepcion`() {
        assertFailsWith<IllegalArgumentException> { Comida("X", -1.0) }
    }

    @Test
    fun `precios del catalogo coinciden con el enunciado`() {
        val precios = catalogo.map { it.calcularPrecioFinal() }
        assertEquals(listOf(8_990.0, 15_990.0, 2_289.0, 3_887.0), precios)
        assertEquals(
            listOf(
                "1. Hamburguesa Clásica - $8,990",
                "2. Salmón Grillado (Premium) - $15,990",
                "3. Coca Cola (Mediano) - $2,289",
                "4. Jugo Natural (Grande) - $3,887"
            ),
            catalogo.mapIndexed { i, p -> "${i + 1}. $p" }
        )
    }

    @Test
    fun `ejemplo del enunciado - productos 1 y 3, cliente vip`() {
        val resumen = calcularResumen(parsearSeleccion("1,3", catalogo), TipoCliente.VIP)
        assertEquals(11_279.0, resumen.subtotal)
        assertEquals(1_128.0, resumen.descuentoCliente)
        assertEquals(0.0, resumen.descuentoPromocion)
        assertEquals(1_929.0, resumen.iva)
        assertEquals(12_080.0, resumen.total)
        assertEquals("$12,080", formatearPesos(resumen.total))
    }

    @Test
    fun `descuento segun tipo de cliente`() {
        assertEquals(500.0, calcularDescuentoCliente(10_000.0, TipoCliente.REGULAR))
        assertEquals(1_000.0, calcularDescuentoCliente(10_000.0, TipoCliente.VIP))
        assertEquals(1_500.0, calcularDescuentoCliente(10_000.0, TipoCliente.PREMIUM))
        assertEquals(TipoCliente.VIP, TipoCliente.desdeTexto(" VIP "))
        assertFailsWith<IllegalArgumentException> { TipoCliente.desdeTexto("oro") }
    }

    @Test
    fun `promocion de pedido grande aplica desde el umbral`() {
        assertEquals(0.0, calcularDescuentoPromocion(24_999.0))
        assertEquals(1_250.0, calcularDescuentoPromocion(25_000.0))
    }

    @Test
    fun `seleccion invalida lanza la excepcion adecuada`() {
        assertFailsWith<NumberFormatException> { parsearSeleccion("1,a", catalogo) }
        assertFailsWith<IndexOutOfBoundsException> { parsearSeleccion("9", catalogo) }
        assertFailsWith<IllegalArgumentException> { parsearSeleccion(" , ", catalogo) }
    }

    @Test
    fun `pedido bajo el minimo termina en estado Error`() = runBlocking {
        val pedido = Pedido().apply { agregar(catalogo[2]) } // solo una Coca Cola
        val estados = mutableListOf<EstadoPedido>()
        val final = procesarPedido(pedido, TipoCliente.REGULAR, tiempoPreparacionMs = 0) { estados += it }
        assertIs<EstadoPedido.Error>(final)
        assertEquals(listOf<EstadoPedido>(EstadoPedido.Pendiente), estados)
    }

    @Test
    fun `pedido valido pasa por Pendiente, En Preparacion y termina Listo`() = runBlocking {
        val pedido = Pedido().apply { agregarTodos(parsearSeleccion("1,3", catalogo)) }
        val estados = mutableListOf<EstadoPedido>()
        val final = procesarPedido(pedido, TipoCliente.VIP, tiempoPreparacionMs = 0) { estados += it }
        assertIs<EstadoPedido.Listo>(final)
        assertEquals(listOf(EstadoPedido.Pendiente, EstadoPedido.EnPreparacion), estados)
        assertEquals("Listo", final.etiqueta())
    }

    @Test
    fun `reporte de ventas agrega correctamente`() {
        val v1 = calcularResumen(parsearSeleccion("1,3", catalogo), TipoCliente.VIP)
        val v2 = calcularResumen(parsearSeleccion("2,2,4", catalogo), TipoCliente.PREMIUM)
        val reporte = generarReporteVentas(listOf(v1, v2))
        assertEquals(2, reporte.cantidadPedidos)
        assertEquals(v1.total + v2.total, reporte.totalVendido)
        assertEquals("Salmón Grillado (Premium)" to 2, reporte.unidadesPorProducto.first())
        assertTrue(v2.descuentoPromocion > 0)
    }

    @Test
    fun `promedio sin datos lanza ArithmeticException`() {
        assertFailsWith<ArithmeticException> { calcularPromedio(emptyList()) }
    }
}
