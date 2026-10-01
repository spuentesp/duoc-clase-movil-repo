package cl.duoc.navegacion.leccion04

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 4 — Navegación TYPE-SAFE (forma recomendada desde Navigation 2.8)
//
// En vez de Strings, cada ruta es una CLASE marcada con @Serializable:
//   - Pantalla sin argumentos -> data object
//   - Pantalla con argumentos -> data class (sus propiedades SON los argumentos)
//
// Ventajas frente a las lecciones 2 y 3:
//   ✔ El compilador revisa los tipos: no puedes mandar un String donde va un Int.
//   ✔ No hay que escribir "detalle/{id}", ni navArgument, ni Uri.encode.
//   ✔ Los argumentos opcionales son simplemente parámetros con valor por defecto.
//
// Requisitos en build.gradle.kts:
//   plugins { id("org.jetbrains.kotlin.plugin.serialization") }
//   implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:<versión>")
// =====================================================================

/** Los enums también se pueden usar como argumento. */
@Serializable
enum class Categoria { COMIDA, BEBIDA, POSTRE }

// ---- Rutas ----

@Serializable
data object Carta

@Serializable
data class DetalleItem(
    val id: Int,                                 // obligatorio
    val nombre: String,                          // obligatorio (los espacios y "/" no son problema)
    val categoria: Categoria = Categoria.COMIDA, // opcional: tiene valor por defecto
    val descuento: Double? = null                // opcional: puede ser null
)

@Composable
fun Leccion04TypeSafe(onSalir: () -> Unit) {
    val navController = rememberNavController()

    // startDestination recibe un OBJETO, no un String
    NavHost(navController = navController, startDestination = Carta) {

        // composable<Tipo> registra la pantalla para esa clase de ruta
        composable<Carta> {
            PantallaCarta(
                onAbrir = { ruta -> navController.navigate(ruta) }, // ruta es un DetalleItem
                onSalir = onSalir
            )
        }

        composable<DetalleItem> { backStackEntry ->
            // toRoute<T>() reconstruye el objeto con todos sus argumentos, ya tipados
            val detalle: DetalleItem = backStackEntry.toRoute()
            PantallaDetalleItem(detalle = detalle, onVolver = { navController.popBackStack() })
        }
    }
}

@Composable
private fun PantallaCarta(onAbrir: (DetalleItem) -> Unit, onSalir: () -> Unit) {
    PantallaBase(titulo = "L4 · Carta (type-safe)", onVolver = onSalir) {
        Explicacion("Cada botón navega con un objeto DetalleItem. Compara con la lección 2: ya no hay Strings.")
        CodigoClave(
            """
            @Serializable
            data class DetalleItem(
                val id: Int,
                val nombre: String,
                val categoria: Categoria = Categoria.COMIDA,
                val descuento: Double? = null
            )

            navController.navigate(DetalleItem(id = 7, nombre = "Sopaipilla"))
            """
        )
        BotonPrincipal("Sopaipilla (solo obligatorios)") {
            onAbrir(DetalleItem(id = 7, nombre = "Sopaipilla"))
        }
        BotonPrincipal("Mote con huesillo (bebida)") {
            onAbrir(DetalleItem(id = 8, nombre = "Mote con huesillo", categoria = Categoria.BEBIDA))
        }
        BotonPrincipal("Leche asada (postre, 20% dcto.)") {
            onAbrir(DetalleItem(id = 9, nombre = "Leche asada", categoria = Categoria.POSTRE, descuento = 0.2))
        }
    }
}

@Composable
private fun PantallaDetalleItem(detalle: DetalleItem, onVolver: () -> Unit) {
    PantallaBase(titulo = "L4 · Detalle", onVolver = onVolver) {
        ValorRecibido("id = ${detalle.id}")
        ValorRecibido("nombre = ${detalle.nombre}")
        ValorRecibido("categoria = ${detalle.categoria}")
        ValorRecibido("descuento = ${detalle.descuento ?: "sin descuento"}")
        Explicacion(
            "Todos los valores llegaron con su tipo correcto (Int, String, enum, Double?).\n\n" +
                "¿Y pasar un objeto completo (ej. un Producto)? Se puede con un NavType personalizado, " +
                "pero NO se recomienda: pasa el id y busca el objeto en el repositorio/ViewModel " +
                "(lecciones 7, 10 y 11)."
        )
        CodigoClave(
            """
            composable<DetalleItem> { entry ->
                val detalle: DetalleItem = entry.toRoute()
            }
            """
        )
    }
}
