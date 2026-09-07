package ejercicio1

open class Publicacion(
    val id: Int,
    val titulo: String,
    val anio: Int
) {
    open fun mostrarDetalle(): String = "[$id] $titulo ($anio)"
}

class Libro(
    id: Int,
    titulo: String,
    anio: Int,
    val autor: String,
    val paginas: Int
) : Publicacion(id, titulo, anio) {
    override fun mostrarDetalle(): String =
        "${super.mostrarDetalle()} - $autor, $paginas pags."
}

class Revista(
    id: Int,
    titulo: String,
    anio: Int,
    val editor: String,
    val numero: Int
) : Publicacion(id, titulo, anio) {
    override fun mostrarDetalle(): String =
        "${super.mostrarDetalle()} - Revista $editor #$numero"
}
