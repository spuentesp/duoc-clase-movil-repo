package cl.duoc.navegacion.leccion05

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.BotonSecundario
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 5 — Controlar la pila de navegación (back stack)
//
// navigate(ruta) { ... } acepta opciones (NavOptions):
//   popUpTo<Ruta> { inclusive = true }  -> antes de navegar, saca pantallas de la pila
//   launchSingleTop = true              -> no apila un duplicado si ya está arriba
//
// Caso clásico: Login.
//   - Al entrar, Login debe desaparecer de la pila (si no, "atrás" vuelve al login).
//   - Al cerrar sesión, se borra TODO y se vuelve al Login.
// =====================================================================

@Serializable data object Login
@Serializable data object Home
@Serializable data object Perfil
@Serializable data object Ajustes

@Composable
fun Leccion05PilaDeNavegacion(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            PantallaPila("L5 · Login", navController, onVolver = onSalir) {
                Explicacion("Al entrar usamos popUpTo<Login> { inclusive = true }: Login sale de la pila.")
                CodigoClave(
                    """
                    navController.navigate(Home) {
                        popUpTo<Login> { inclusive = true }
                    }
                    """
                )
                BotonPrincipal("Iniciar sesión") {
                    navController.navigate(Home) {
                        popUpTo<Login> { inclusive = true }
                    }
                }
            }
        }

        composable<Home> {
            PantallaPila("L5 · Home", navController, onVolver = onSalir) {
                Explicacion(
                    "Presiona \"atrás\": sales de la lección y no vuelves al Login, porque ya no está en la pila.\n\n" +
                        "Prueba los dos botones de \"Home otra vez\" y mira cómo cambia la pila."
                )
                BotonPrincipal("Ir a Perfil") { navController.navigate(Perfil) }
                BotonSecundario("Home otra vez (apila un duplicado)") { navController.navigate(Home) }
                BotonSecundario("Home otra vez con launchSingleTop") {
                    navController.navigate(Home) { launchSingleTop = true }
                }
            }
        }

        composable<Perfil> {
            PantallaPila("L5 · Perfil", navController, onVolver = { navController.popBackStack() }) {
                BotonPrincipal("Ir a Ajustes") { navController.navigate(Ajustes) }
            }
        }

        composable<Ajustes> {
            PantallaPila("L5 · Ajustes", navController, onVolver = { navController.popBackStack() }) {
                CodigoClave(
                    """
                    // Volver hasta Home (sin sacarlo)
                    navController.popBackStack<Home>(inclusive = false)

                    // Cerrar sesión: borrar TODA la pila
                    navController.navigate(Login) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                    """
                )
                BotonPrincipal("Volver directo a Home") {
                    navController.popBackStack<Home>(inclusive = false)
                }
                BotonPrincipal("Cerrar sesión") {
                    navController.navigate(Login) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
        }
    }
}

/** Pantalla genérica de esta lección: muestra siempre el estado actual de la pila. */
@Composable
private fun PantallaPila(
    titulo: String,
    navController: NavController,
    onVolver: () -> Unit,
    contenido: @Composable () -> Unit
) {
    PantallaBase(titulo = titulo, onVolver = onVolver) {
        PilaActual(navController)
        contenido()
    }
}

/**
 * Dibuja la pila actual (de abajo hacia arriba). SOLO para fines didácticos:
 * currentBackStack es una API interna de la librería, no la uses en una app real.
 */
@SuppressLint("RestrictedApi")
@Composable
fun PilaActual(navController: NavController) {
    val pila by navController.currentBackStack.collectAsState()
    val nombres = pila
        .filter { it.destination !is NavGraph }              // el grafo raíz no es una pantalla
        .map { it.destination.route.orEmpty().substringAfterLast('.') }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Text(
            "Pila: " + nombres.joinToString(" → "),
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.titleSmall
        )
    }
}
