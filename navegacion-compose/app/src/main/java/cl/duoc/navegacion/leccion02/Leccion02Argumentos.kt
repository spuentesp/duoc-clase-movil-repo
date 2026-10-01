package cl.duoc.navegacion.leccion02

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido

// =====================================================================
// LECCIÓN 2 — Pasar argumentos OBLIGATORIOS en la ruta
//
// La ruta se declara con "huecos" entre llaves:   "detalle/{id}/{nombre}"
// Y se navega rellenándolos:                      "detalle/2/Empanada"
//
// Regla de oro: pasa datos SIMPLES (un id, un texto corto), nunca objetos completos.
// La pantalla de destino usa el id para buscar el resto de la información.
// =====================================================================

data class Producto(val id: Int, val nombre: String, val precio: Int)

/** "Base de datos" de ejemplo. */
val PRODUCTOS = listOf(
    Producto(1, "Completo italiano", 2500),
    Producto(2, "Empanada de pino", 2200),
    Producto(3, "Café / té", 1200) // tiene "/" a propósito: hay que codificarlo
)

object Rutas02 {
    const val LISTA = "lista"

    // Patrón de la ruta con dos argumentos obligatorios
    const val DETALLE = "detalle/{id}/{nombre}"

    // Función que arma la ruta real. Uri.encode() protege textos con espacios,
    // "/" u otros caracteres especiales (sin esto "Café / té" rompería la ruta).
    fun detalle(id: Int, nombre: String) = "detalle/$id/${Uri.encode(nombre)}"
}

@Composable
fun Leccion02Argumentos(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas02.LISTA) {
        composable(Rutas02.LISTA) {
            PantallaLista(
                onElegir = { producto ->
                    navController.navigate(Rutas02.detalle(producto.id, producto.nombre))
                },
                onSalir = onSalir
            )
        }

        composable(
            route = Rutas02.DETALLE,
            // Declaramos el TIPO de cada argumento (por defecto todos son String)
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("nombre") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Leemos los argumentos desde el NavBackStackEntry de esta pantalla
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val nombre = backStackEntry.arguments?.getString("nombre").orEmpty()

            PantallaDetalle(id = id, nombre = nombre, onVolver = { navController.popBackStack() })
        }
    }
}

@Composable
private fun PantallaLista(onElegir: (Producto) -> Unit, onSalir: () -> Unit) {
    PantallaBase(titulo = "L2 · Productos", onVolver = onSalir) {
        Explicacion("Toca un producto: se navega a \"detalle/{id}/{nombre}\" con sus valores.")
        CodigoClave(
            """
            composable(
                route = "detalle/{id}/{nombre}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("nombre") { type = NavType.StringType }
                )
            ) { entry ->
                val id = entry.arguments?.getInt("id")
            }
            """
        )
        PRODUCTOS.forEach { producto ->
            BotonPrincipal("${producto.nombre} — $${producto.precio}", onClick = { onElegir(producto) })
        }
    }
}

@Composable
private fun PantallaDetalle(id: Int, nombre: String, onVolver: () -> Unit) {
    // Con el id buscamos el resto de los datos (no los pasamos por la ruta)
    val producto = PRODUCTOS.firstOrNull { it.id == id }

    PantallaBase(titulo = "L2 · Detalle", onVolver = onVolver) {
        ValorRecibido("id recibido = $id")
        ValorRecibido("nombre recibido = $nombre")
        Explicacion(
            "El precio NO viajó en la ruta: se buscó con el id. " +
                "Así, si el precio cambia en la base de datos, la pantalla siempre muestra el valor actual.\n\n" +
                "Precio encontrado: ${producto?.precio?.let { "$$it" } ?: "no existe"}"
        )
        CodigoClave("""navController.navigate("detalle/${'$'}id/${'$'}{Uri.encode(nombre)}")""")
    }
}
