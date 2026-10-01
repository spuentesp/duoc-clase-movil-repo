package cl.duoc.navegacion.leccion06

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.BotonSecundario
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 6 — Devolver un resultado a la pantalla ANTERIOR
//
// Ida (A -> B):    argumentos de la ruta (lecciones 2 a 4).
// Vuelta (B -> A): cada pantalla de la pila tiene un savedStateHandle (un "buzón").
//   B escribe en el buzón de A:  previousBackStackEntry?.savedStateHandle?.set("clave", valor)
//   A lee su propio buzón:       backStackEntry.savedStateHandle.getStateFlow("clave", inicial)
// =====================================================================

const val CLAVE_SALSA = "salsa"
const val CLAVE_NOMBRE = "nombre"

@Serializable data object Pedido
@Serializable data object ElegirSalsa
@Serializable data class EditarNombre(val nombreActual: String)

private val SALSAS = listOf("Pebre", "Mayonesa casera", "Ají verde", "Chancho en piedra")

@Composable
fun Leccion06DevolverResultados(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pedido) {

        composable<Pedido> { backStackEntry ->
            // Pantalla A: observa su propio savedStateHandle.
            // Cuando B escribe un valor, esta pantalla se recompone sola.
            val buzon = backStackEntry.savedStateHandle
            val salsa by buzon.getStateFlow<String?>(CLAVE_SALSA, null).collectAsStateWithLifecycle()
            val nombre by buzon.getStateFlow(CLAVE_NOMBRE, "Invitado").collectAsStateWithLifecycle()

            PantallaPedido(
                salsa = salsa,
                nombre = nombre,
                onElegirSalsa = { navController.navigate(ElegirSalsa) },
                // Ida con argumento + vuelta con resultado
                onEditarNombre = { navController.navigate(EditarNombre(nombreActual = nombre)) },
                onSalir = onSalir
            )
        }

        composable<ElegirSalsa> {
            PantallaElegirSalsa(
                onElegir = { elegida ->
                    // Pantalla B: deja el resultado en el buzón de la pantalla anterior...
                    navController.previousBackStackEntry?.savedStateHandle?.set(CLAVE_SALSA, elegida)
                    // ...y vuelve
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable<EditarNombre> { backStackEntry ->
            val ruta: EditarNombre = backStackEntry.toRoute()
            PantallaEditarNombre(
                nombreInicial = ruta.nombreActual,
                onGuardar = { nuevo ->
                    navController.previousBackStackEntry?.savedStateHandle?.set(CLAVE_NOMBRE, nuevo)
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PantallaPedido(
    salsa: String?,
    nombre: String,
    onElegirSalsa: () -> Unit,
    onEditarNombre: () -> Unit,
    onSalir: () -> Unit
) {
    PantallaBase(titulo = "L6 · Mi pedido", onVolver = onSalir) {
        ValorRecibido("Cliente: $nombre")
        ValorRecibido("Salsa: ${salsa ?: "sin elegir"}")
        Explicacion("Elige una salsa o cambia el nombre: la otra pantalla devolverá el resultado aquí.")
        CodigoClave(
            """
            // En la pantalla que DEVUELVE:
            navController.previousBackStackEntry
                ?.savedStateHandle?.set("salsa", elegida)
            navController.popBackStack()

            // En la pantalla que RECIBE:
            val salsa by entry.savedStateHandle
                .getStateFlow<String?>("salsa", null)
                .collectAsStateWithLifecycle()
            """
        )
        BotonPrincipal("Elegir salsa", onClick = onElegirSalsa)
        BotonPrincipal("Cambiar nombre", onClick = onEditarNombre)
    }
}

@Composable
private fun PantallaElegirSalsa(onElegir: (String) -> Unit, onCancelar: () -> Unit) {
    PantallaBase(titulo = "L6 · Elige una salsa", onVolver = onCancelar) {
        Text("Al tocar una opción se devuelve el resultado y se vuelve atrás.")
        SALSAS.forEach { salsa -> BotonPrincipal(salsa) { onElegir(salsa) } }
    }
}

@Composable
private fun PantallaEditarNombre(nombreInicial: String, onGuardar: (String) -> Unit, onCancelar: () -> Unit) {
    // rememberSaveable: el texto sobrevive a la rotación de pantalla
    var texto by rememberSaveable { mutableStateOf(nombreInicial) }

    PantallaBase(titulo = "L6 · Editar nombre", onVolver = onCancelar) {
        Text("El valor actual llegó como argumento de la ruta: EditarNombre(nombreActual = \"$nombreInicial\")")
        OutlinedTextField(value = texto, onValueChange = { texto = it }, label = { Text("Nombre") })
        BotonPrincipal("Guardar", enabled = texto.isNotBlank()) { onGuardar(texto.trim()) }
        BotonSecundario("Cancelar (no devuelve nada)", onCancelar)
    }
}
