// =====================================================================
// Producto.kt — Modelo de datos: clase base y clases derivadas
// (IE 1.1.1 tipos de datos, IE 1.3.1 herencia, IE 1.3.2 polimorfismo)
// =====================================================================

/** Categorías del catálogo. Un enum evita errores de tipeo con Strings. */
enum class Categoria(val etiqueta: String) {
    COMIDA("Comida"),
    BEBIDA("Bebida")
}

/**
 * Tamaños de bebida y su factor sobre el precio base.
 * Factores deducidos del ejemplo del enunciado:
 *   Coca Cola $1.990 (Mediano) -> $2.289  => x1.15
 *   Jugo Natural $2.990 (Grande) -> $3.887 => x1.30
 */
enum class Tamano(val etiqueta: String, val factorPrecio: Double) {
    PEQUENO("Pequeño", 1.00),
    MEDIANO("Mediano", 1.15),
    GRANDE("Grande", 1.30);

    companion object {
        /** Convierte un texto ("mediano", "Grande"...) a Tamano; lanza excepción si no existe. */
        fun desdeTexto(texto: String): Tamano =
            entries.firstOrNull { it.name.equals(normalizar(texto), ignoreCase = true) }
                ?: throw IllegalArgumentException("Tamaño de bebida desconocido: '$texto'")

        private fun normalizar(texto: String) = texto.trim().replace("ñ", "n").replace("Ñ", "N")
    }
}

/**
 * Clase base ABIERTA (open) para todos los productos del restaurante.
 *
 * - Propiedades inmutables (val): un producto del catálogo no cambia durante el pedido.
 * - precioBase es Double (nunca Int) porque representa dinero y se multiplica por
 *   factores/porcentajes.
 * - El bloque init valida los datos: un precio negativo lanza IllegalArgumentException,
 *   que luego se captura con try-catch al construir el catálogo.
 */
open class Producto(
    val nombre: String,
    val precioBase: Double,
    val categoria: Categoria,
    val tiempoPreparacionMin: Int
) {
    init {
        require(nombre.isNotBlank()) { "El nombre del producto no puede estar vacío" }
        require(precioBase > 0.0) { "Precio inválido para '$nombre': $precioBase (debe ser mayor a 0)" }
        require(tiempoPreparacionMin >= 0) { "Tiempo de preparación inválido para '$nombre'" }
    }

    /** Precio final a cobrar. Las subclases lo sobrescriben si su precio varía. */
    open fun calcularPrecioFinal(): Double = precioBase

    /** Texto para mostrar en catálogo y resumen. Las subclases agregan su detalle. */
    open fun descripcion(): String = nombre

    /** Tiempo estimado de preparación (minutos). */
    open fun tiempoPreparacion(): Int = tiempoPreparacionMin

    override fun toString(): String = "${descripcion()} - ${formatearPesos(calcularPrecioFinal())}"
}

/**
 * Comida (plato principal). Puede ser premium.
 * Los parámetros se pasan al constructor primario de la superclase.
 *
 * Polimorfismo: un plato premium se identifica en la descripción y requiere
 * más tiempo de preparación (emplatado especial). Su precio diferenciado ya viene
 * en el catálogo (Salmón Grillado $15.990), tal como muestra el ejemplo de ejecución.
 */
class Comida(
    nombre: String,
    precioBase: Double,
    val esPremium: Boolean = false,
    tiempoPreparacionMin: Int = 15
) : Producto(nombre, precioBase, Categoria.COMIDA, tiempoPreparacionMin) {

    override fun descripcion(): String =
        if (esPremium) "$nombre (Premium)" else nombre

    override fun tiempoPreparacion(): Int =
        if (esPremium) tiempoPreparacionMin + MINUTOS_EXTRA_PREMIUM else tiempoPreparacionMin

    companion object {
        const val MINUTOS_EXTRA_PREMIUM: Int = 10
    }
}

/**
 * Bebida con tamaño. Polimorfismo: sobrescribe calcularPrecioFinal()
 * aplicando el factor del tamaño sobre el precio base.
 */
class Bebida(
    nombre: String,
    precioBase: Double,
    val tamano: Tamano = Tamano.MEDIANO,
    tiempoPreparacionMin: Int = 2
) : Producto(nombre, precioBase, Categoria.BEBIDA, tiempoPreparacionMin) {

    override fun calcularPrecioFinal(): Double =
        redondearPesos(precioBase * tamano.factorPrecio)

    override fun descripcion(): String = "$nombre (${tamano.etiqueta})"
}
