// =====================================================================
// Main.kt — Punto de entrada y capa de presentación (consola)
// Flujo: catálogo -> selección -> cálculo -> proceso asíncrono -> resumen
// (IE 1.2.4 iteración/presentación, IE 1.3.3 runBlocking, IE 1.3.4 try-catch)
// =====================================================================

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("=== SISTEMA FOODEXPRESS ===")

    val (catalogo, errores) = inicializarCatalogo()
    errores.forEach { println("[Aviso] $it") }

    // Historial de la sesión para el reporte de ventas
    val ventas = mutableListOf<ResumenPedido>()

    do {
        mostrarCatalogo(catalogo)

        val seleccion = leerSeleccion(catalogo) ?: break
        val tipoCliente = leerTipoCliente() ?: break

        val pedido = Pedido().apply { agregarTodos(seleccion) }

        println()
        println("Procesando pedido...")
        val estadoFinal = procesarPedido(pedido, tipoCliente) { estado ->
            println("Estado: ${estado.etiqueta()}")
        }

        // when exhaustivo sobre la sealed class: no requiere else
        when (estadoFinal) {
            is EstadoPedido.Listo -> {
                mostrarResumen(estadoFinal.resumen)
                ventas += estadoFinal.resumen
            }
            is EstadoPedido.Error -> println("No se pudo procesar el pedido: ${estadoFinal.mensaje}")
            EstadoPedido.Pendiente, EstadoPedido.EnPreparacion ->
                println("El pedido quedó sin finalizar")
        }
        println()
        println("Estado final: ${estadoFinal.etiqueta()}")
    } while (preguntarOtroPedido())

    mostrarConsultasCatalogo(catalogo)
    mostrarReporteVentas(ventas)
    println()
    println("Gracias por usar FoodExpress.")
}

// ---------------------------------------------------------------------
// Entrada de datos (con reintentos ante errores, sin detener el programa)
// ---------------------------------------------------------------------

/** Pide la selección hasta que sea válida. Retorna null si se cierra la entrada estándar. */
fun leerSeleccion(catalogo: List<Producto>): List<Producto>? {
    while (true) {
        println()
        print("Seleccione productos (números separados por coma): ")
        val entrada = readlnOrNull() ?: return null
        try {
            return parsearSeleccion(entrada, catalogo)
        } catch (e: NumberFormatException) {
            println("Entrada inválida: ${e.message}. Intente nuevamente.")
        } catch (e: IndexOutOfBoundsException) {
            println("Selección fuera de rango: ${e.message}. Intente nuevamente.")
        } catch (e: IllegalArgumentException) {
            println("${e.message}. Intente nuevamente.")
        }
    }
}

/** Pide el tipo de cliente hasta que sea válido. Retorna null si se cierra la entrada estándar. */
fun leerTipoCliente(): TipoCliente? {
    while (true) {
        print("Cliente tipo (regular/vip/premium): ")
        val entrada = readlnOrNull() ?: return null
        try {
            return TipoCliente.desdeTexto(entrada)
        } catch (e: IllegalArgumentException) {
            println("${e.message}. Intente nuevamente.")
        }
    }
}

fun preguntarOtroPedido(): Boolean {
    println()
    print("¿Desea realizar otro pedido? (s/n): ")
    val respuesta = readlnOrNull()?.trim()?.lowercase() ?: return false
    return respuesta == "s" || respuesta == "si" || respuesta == "sí"
}

// ---------------------------------------------------------------------
// Presentación (iteración sobre colecciones)
// ---------------------------------------------------------------------

fun mostrarCatalogo(catalogo: List<Producto>) {
    println("Catálogo disponible:")
    catalogo.forEachIndexed { indice, producto ->
        println("${indice + 1}. $producto")
    }
}

fun mostrarResumen(resumen: ResumenPedido) {
    println()
    println("=== RESUMEN DEL PEDIDO ===")
    for (producto in resumen.items) {
        println("- ${producto.descripcion()}: ${formatearPesos(producto.calcularPrecioFinal())}")
    }
    println("Subtotal: ${formatearPesos(resumen.subtotal)}")
    val tipo = resumen.tipoCliente
    println("Descuento ${tipo.etiqueta} (${formatearPorcentaje(tipo.porcentajeDescuento)}): -${formatearPesos(resumen.descuentoCliente)}")
    if (resumen.descuentoPromocion > 0) {
        println("Promoción pedido grande (${formatearPorcentaje(PORCENTAJE_PROMO_PEDIDO_GRANDE)}): -${formatearPesos(resumen.descuentoPromocion)}")
    }
    println("IVA (${formatearPorcentaje(IVA)}): ${formatearPesos(resumen.iva)}")
    println("TOTAL: ${formatearPesos(resumen.total)}")
    val minutos = resumen.items.sumOf { it.tiempoPreparacion() }
    println("Tiempo estimado de preparación: $minutos min")
}

fun mostrarConsultasCatalogo(catalogo: List<Producto>) {
    println()
    println("=== CONSULTAS SOBRE EL CATÁLOGO ===")
    println("Comidas: " + filtrarPorCategoria(catalogo, Categoria.COMIDA).joinToString { it.descripcion() })
    println("Bebidas: " + filtrarPorCategoria(catalogo, Categoria.BEBIDA).joinToString { it.descripcion() })
    println("Platos premium: " + filtrarPremium(catalogo).joinToString { it.nombre })
    println("Productos hasta $3,000: " + filtrarPorPrecioMaximo(catalogo, 3_000.0).joinToString { it.descripcion() })
    println("Precios con IVA incluido:")
    preciosConIva(catalogo).forEach { (descripcion, precio) ->
        println("  · $descripcion: ${formatearPesos(precio)}")
    }
}

fun mostrarReporteVentas(ventas: List<ResumenPedido>) {
    println()
    println("=== REPORTE DE VENTAS DE LA SESIÓN ===")
    try {
        val reporte = generarReporteVentas(ventas)
        println("Pedidos completados: ${reporte.cantidadPedidos}")
        println("Total vendido: ${formatearPesos(reporte.totalVendido)}")
        println("Ticket promedio: ${formatearPesos(reporte.ticketPromedio)}")
        println("Descuentos otorgados: ${formatearPesos(reporte.totalDescuentos)}")
        println("Ventas por tipo de cliente:")
        reporte.totalPorTipoCliente.forEach { (tipo, total) ->
            println("  · ${tipo.etiqueta}: ${formatearPesos(total)}")
        }
        println("Ventas por categoría (sin IVA ni descuentos):")
        reporte.ventasPorCategoria.forEach { (categoria, total) ->
            println("  · ${categoria.etiqueta}: ${formatearPesos(total)}")
        }
        println("Unidades vendidas por producto:")
        reporte.unidadesPorProducto.forEach { (producto, unidades) ->
            println("  · $producto: $unidades")
        }
    } catch (e: ArithmeticException) {
        // Sin pedidos completados no se puede calcular el promedio: se informa y se continúa
        println("Sin ventas registradas: ${e.message}")
    }
}
