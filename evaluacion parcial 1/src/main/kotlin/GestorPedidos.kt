// =====================================================================
// GestorPedidos.kt — Lógica de negocio (sin println: la presentación vive en Main.kt)
// (IE 1.1.2 cálculos, IE 1.1.3 condicionales, IE 1.2.1 funciones,
//  IE 1.2.2 colecciones, IE 1.2.3 orden superior, IE 1.3.3 corutinas,
//  IE 1.3.4 excepciones)
// =====================================================================

import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.roundToLong

// ---------------------------------------------------------------------
// Constantes de negocio (val inmutables, Double para porcentajes y dinero)
// ---------------------------------------------------------------------
const val IVA: Double = 0.19
const val PEDIDO_MINIMO: Double = 5_000.0
const val UMBRAL_PROMO_PEDIDO_GRANDE: Double = 25_000.0
const val PORCENTAJE_PROMO_PEDIDO_GRANDE: Double = 0.05
const val TIEMPO_PREPARACION_MS: Long = 3_000L

// ---------------------------------------------------------------------
// Tipos de cliente y su descuento
// ---------------------------------------------------------------------
enum class TipoCliente(val etiqueta: String, val porcentajeDescuento: Double) {
    REGULAR("Regular", 0.05),
    VIP("VIP", 0.10),
    PREMIUM("Premium", 0.15);

    companion object {
        /** Determina el tipo de cliente a partir del texto ingresado (condicional con when). */
        fun desdeTexto(texto: String): TipoCliente = when (texto.trim().lowercase()) {
            "regular" -> REGULAR
            "vip" -> VIP
            "premium" -> PREMIUM
            else -> throw IllegalArgumentException(
                "Tipo de cliente inválido: '$texto' (use regular, vip o premium)"
            )
        }
    }
}

// ---------------------------------------------------------------------
// Catálogo
// ---------------------------------------------------------------------

/** Datos crudos de un producto, tal como vendrían de un archivo/BD (pueden ser inválidos). */
data class DatosProducto(
    val tipo: String,
    val nombre: String,
    val precio: Double,
    val premium: Boolean = false,
    val tamano: String = "mediano"
)

/** Datos de prueba del enunciado + un registro inválido para demostrar el manejo de errores. */
val DATOS_CATALOGO: List<DatosProducto> = listOf(
    DatosProducto("comida", "Hamburguesa Clásica", 8_990.0, premium = false),
    DatosProducto("comida", "Salmón Grillado", 15_990.0, premium = true),
    DatosProducto("bebida", "Coca Cola", 1_990.0, tamano = "mediano"),
    DatosProducto("bebida", "Jugo Natural", 2_990.0, tamano = "grande"),
    DatosProducto("comida", "Plato Fantasma", -4_990.0) // precio negativo: debe descartarse
)

/** Resultado de construir el catálogo: productos válidos + errores encontrados. */
data class ResultadoCatalogo(val productos: List<Producto>, val errores: List<String>)

/** Crea un Producto concreto (Comida o Bebida) según el tipo. Lanza excepción si los datos son inválidos. */
fun crearProducto(datos: DatosProducto): Producto = when (datos.tipo.lowercase()) {
    "comida" -> Comida(datos.nombre, datos.precio, datos.premium)
    "bebida" -> Bebida(datos.nombre, datos.precio, Tamano.desdeTexto(datos.tamano))
    else -> throw IllegalArgumentException("Tipo de producto desconocido: '${datos.tipo}'")
}

/**
 * Inicializa el catálogo. Cada producto se crea dentro de un try-catch:
 * un dato inválido (ej. precio negativo) se reporta y se descarta,
 * pero la aplicación continúa funcionando con el resto.
 */
fun inicializarCatalogo(datos: List<DatosProducto> = DATOS_CATALOGO): ResultadoCatalogo {
    val errores = mutableListOf<String>()
    val productos = datos.mapNotNull { dato ->
        try {
            crearProducto(dato)
        } catch (e: IllegalArgumentException) {
            errores += "Producto descartado: ${e.message}"
            null
        }
    }
    return ResultadoCatalogo(productos, errores)
}

// ---------------------------------------------------------------------
// Pedido (colección mutable encapsulada; hacia afuera se expone inmutable)
// ---------------------------------------------------------------------
class Pedido {
    private val _items = mutableListOf<Producto>()
    val items: List<Producto> get() = _items

    fun agregar(producto: Producto) {
        _items.add(producto)
    }

    fun agregarTodos(productos: List<Producto>) {
        _items.addAll(productos)
    }

    fun estaVacio(): Boolean = _items.isEmpty()
}

/**
 * Convierte la entrada "1,3" en la lista de productos seleccionados.
 * Lanza NumberFormatException si hay valores no numéricos e
 * IndexOutOfBoundsException si un número no existe en el catálogo.
 */
fun parsearSeleccion(entrada: String, catalogo: List<Producto>): List<Producto> {
    val numeros = entrada.split(",")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { it.toIntOrNull() ?: throw NumberFormatException("'$it' no es un número válido") }

    if (numeros.isEmpty()) throw IllegalArgumentException("Debe seleccionar al menos un producto")

    return numeros.map { numero ->
        catalogo.getOrNull(numero - 1)
            ?: throw IndexOutOfBoundsException("El producto $numero no existe (opciones: 1 a ${catalogo.size})")
    }
}

// ---------------------------------------------------------------------
// Cálculos (funciones puras, una responsabilidad cada una)
// ---------------------------------------------------------------------

/** Pesos chilenos no tienen decimales: se redondea al peso más cercano. */
fun redondearPesos(monto: Double): Double = monto.roundToLong().toDouble()

/** Subtotal = suma de precios finales (polimórficos) de los productos. */
fun calcularSubtotal(items: List<Producto>): Double =
    items.sumOf { it.calcularPrecioFinal() }

/** Descuento según tipo de cliente. */
fun calcularDescuentoCliente(subtotal: Double, tipoCliente: TipoCliente): Double =
    redondearPesos(subtotal * tipoCliente.porcentajeDescuento)

/** Promoción especial: 5% adicional si el subtotal alcanza el umbral de pedido grande. */
fun calcularDescuentoPromocion(subtotal: Double): Double =
    if (subtotal >= UMBRAL_PROMO_PEDIDO_GRANDE) redondearPesos(subtotal * PORCENTAJE_PROMO_PEDIDO_GRANDE)
    else 0.0

/** IVA sobre el monto neto (después de descuentos). */
fun calcularIva(montoNeto: Double): Double = redondearPesos(montoNeto * IVA)

/** Valida reglas mínimas del pedido; lanza IllegalStateException si no se cumplen. */
fun validarPedido(items: List<Producto>, subtotal: Double) {
    check(items.isNotEmpty()) { "El pedido no tiene productos" }
    check(subtotal >= PEDIDO_MINIMO) {
        "El subtotal ${formatearPesos(subtotal)} no alcanza el pedido mínimo de ${formatearPesos(PEDIDO_MINIMO)}"
    }
}

/** Desglose completo de un pedido. */
data class ResumenPedido(
    val items: List<Producto>,
    val tipoCliente: TipoCliente,
    val subtotal: Double,
    val descuentoCliente: Double,
    val descuentoPromocion: Double,
    val iva: Double,
    val total: Double
) {
    val montoNeto: Double get() = subtotal - descuentoCliente - descuentoPromocion
}

/** Orquesta todos los cálculos del pedido en orden: subtotal -> descuentos -> IVA -> total. */
fun calcularResumen(items: List<Producto>, tipoCliente: TipoCliente): ResumenPedido {
    val subtotal = calcularSubtotal(items)
    validarPedido(items, subtotal)

    val descuentoCliente = calcularDescuentoCliente(subtotal, tipoCliente)
    val descuentoPromocion = calcularDescuentoPromocion(subtotal)
    val neto = subtotal - descuentoCliente - descuentoPromocion
    if (neto < 0) throw ArithmeticException("El monto neto no puede ser negativo")

    val iva = calcularIva(neto)
    return ResumenPedido(
        items = items,
        tipoCliente = tipoCliente,
        subtotal = subtotal,
        descuentoCliente = descuentoCliente,
        descuentoPromocion = descuentoPromocion,
        iva = iva,
        total = neto + iva
    )
}

// ---------------------------------------------------------------------
// Procesamiento asíncrono (corutina)
// ---------------------------------------------------------------------

/**
 * Simula la preparación del pedido en la cocina.
 * - Notifica los estados intermedios (Pendiente, En Preparación) mediante un callback.
 * - delay() suspende la corutina SIN bloquear el hilo (a diferencia de Thread.sleep).
 * - Retorna el estado final: Listo con el resumen, o Error con el motivo.
 */
suspend fun procesarPedido(
    pedido: Pedido,
    tipoCliente: TipoCliente,
    tiempoPreparacionMs: Long = TIEMPO_PREPARACION_MS,
    alCambiarEstado: (EstadoPedido) -> Unit = {}
): EstadoPedido {
    alCambiarEstado(EstadoPedido.Pendiente)
    return try {
        val resumen = calcularResumen(pedido.items, tipoCliente)
        alCambiarEstado(EstadoPedido.EnPreparacion)
        delay(tiempoPreparacionMs)
        EstadoPedido.Listo(resumen)
    } catch (e: IllegalStateException) {
        EstadoPedido.Error(e.message ?: "Pedido inválido")
    } catch (e: ArithmeticException) {
        EstadoPedido.Error("Error de cálculo: ${e.message}")
    }
}

// ---------------------------------------------------------------------
// Consultas y reportes (funciones de orden superior)
// ---------------------------------------------------------------------

fun filtrarPorCategoria(catalogo: List<Producto>, categoria: Categoria): List<Producto> =
    catalogo.filter { it.categoria == categoria }

fun filtrarPremium(catalogo: List<Producto>): List<Producto> =
    catalogo.filterIsInstance<Comida>().filter { it.esPremium }

fun filtrarPorPrecioMaximo(catalogo: List<Producto>, maximo: Double): List<Producto> =
    catalogo.filter { it.calcularPrecioFinal() <= maximo }

/** Transforma el catálogo en pares (descripción, precio con IVA). */
fun preciosConIva(catalogo: List<Producto>): List<Pair<String, Double>> =
    catalogo.map { it.descripcion() to redondearPesos(it.calcularPrecioFinal() * (1 + IVA)) }

/** Promedio seguro: lanza ArithmeticException si no hay datos (evita dividir por cero). */
fun calcularPromedio(valores: List<Double>): Double {
    if (valores.isEmpty()) throw ArithmeticException("No hay datos para calcular el promedio")
    return valores.sum() / valores.size
}

/** Reporte agregado de todas las ventas de la sesión. */
data class ReporteVentas(
    val cantidadPedidos: Int,
    val totalVendido: Double,
    val ticketPromedio: Double,
    val totalPorTipoCliente: Map<TipoCliente, Double>,
    val unidadesPorProducto: List<Pair<String, Int>>,
    val ventasPorCategoria: Map<Categoria, Double>,
    val totalDescuentos: Double
)

fun generarReporteVentas(ventas: List<ResumenPedido>): ReporteVentas {
    val todosLosItems = ventas.flatMap { it.items }
    return ReporteVentas(
        cantidadPedidos = ventas.size,
        totalVendido = ventas.sumOf { it.total },
        ticketPromedio = calcularPromedio(ventas.map { it.total }),
        totalPorTipoCliente = ventas.groupBy { it.tipoCliente }
            .mapValues { (_, pedidos) -> pedidos.sumOf { it.total } },
        unidadesPorProducto = todosLosItems.groupingBy { it.descripcion() }
            .eachCount()
            .toList()
            .sortedByDescending { it.second },
        ventasPorCategoria = todosLosItems.groupBy { it.categoria }
            .mapValues { (_, productos) -> productos.sumOf { it.calcularPrecioFinal() } },
        totalDescuentos = ventas.sumOf { it.descuentoCliente + it.descuentoPromocion }
    )
}

// ---------------------------------------------------------------------
// Formato
// ---------------------------------------------------------------------

/** Formatea un monto como en el enunciado: 11279.0 -> "$11,279". */
fun formatearPesos(monto: Double): String = "$" + String.format(Locale.US, "%,.0f", monto)

/** Formatea un porcentaje: 0.10 -> "10%". */
fun formatearPorcentaje(porcentaje: Double): String = "${(porcentaje * 100).roundToLong()}%"
