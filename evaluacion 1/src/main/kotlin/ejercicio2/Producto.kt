package ejercicio2

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val categoria: String,
    val stock: Int
)

class Inventario(val productos: List<Producto>) {

    fun valorTotal(): Int = productos.sumOf { it.precio * it.stock }

    fun conStockBajo(umbral: Int = 5): List<Producto> =
        productos.filter { it.stock < umbral }

    fun agruparPorCategoria(): Map<String, List<Producto>> =
        productos.groupBy { it.categoria }

    fun topMasCaros(n: Int): List<Producto> =
        productos.sortedByDescending { it.precio }.take(n)
}
