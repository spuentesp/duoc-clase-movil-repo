package cl.duoc.navegacion.leccion08

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 8 — Grafos anidados + ViewModel compartido entre pantallas
//
// navigation<Grafo>(startDestination = ...) { ... } agrupa varias pantallas
// relacionadas (un "flujo"): registro, checkout, onboarding, etc.
//
// Ventajas:
//   ✔ Organiza el NavHost por funcionalidades.
//   ✔ Se puede navegar al grafo completo: navigate(GrafoRegistro) abre su primera pantalla.
//   ✔ Se puede sacar el flujo entero de la pila: popUpTo<GrafoRegistro> { inclusive = true }.
//   ✔ El grafo tiene su propio NavBackStackEntry -> un ViewModel asociado al grafo
//     es COMPARTIDO por todas sus pantallas y se destruye al salir del flujo.
// =====================================================================

@Serializable data object Bienvenida

// El grafo anidado también es una ruta
@Serializable data object GrafoRegistro
@Serializable data object PasoNombre
@Serializable data object PasoCorreo
@Serializable data object PasoConfirmar

@Serializable data class RegistroExitoso(val nombre: String)

/** Estado del formulario, compartido por los 3 pasos del registro. */
class RegistroViewModel : ViewModel() {
    var nombre by mutableStateOf("")
    var correo by mutableStateOf("")

    val nombreValido get() = nombre.isNotBlank()
    val correoValido get() = "@" in correo && "." in correo.substringAfter("@")
}

/**
 * Obtiene un ViewModel asociado al GRAFO PADRE de esta pantalla, no a la pantalla.
 * Todas las pantallas del mismo grafo reciben la MISMA instancia.
 */
@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.viewModelDelGrafo(navController: NavController): VM {
    val entradaDelPadre = remember(this) {
        val idDelPadre = checkNotNull(destination.parent) { "La pantalla no está dentro de un grafo" }.id
        navController.getBackStackEntry(idDelPadre)
    }
    return viewModel(viewModelStoreOwner = entradaDelPadre)
}

@Composable
fun Leccion08GrafosAnidados(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Bienvenida) {

        composable<Bienvenida> {
            PantallaBase(titulo = "L8 · Bienvenida", onVolver = onSalir) {
                Explicacion("Navegamos al GRAFO, no a una pantalla: se abre su startDestination (PasoNombre).")
                CodigoClave(
                    """
                    navigation<GrafoRegistro>(startDestination = PasoNombre) {
                        composable<PasoNombre> { ... }
                        composable<PasoCorreo> { ... }
                        composable<PasoConfirmar> { ... }
                    }

                    navController.navigate(GrafoRegistro)
                    """
                )
                BotonPrincipal("Crear cuenta") { navController.navigate(GrafoRegistro) }
            }
        }

        // ---- Grafo anidado: el flujo de registro ----
        navigation<GrafoRegistro>(startDestination = PasoNombre) {

            composable<PasoNombre> { entry ->
                val vm: RegistroViewModel = entry.viewModelDelGrafo(navController)
                PantallaBase(titulo = "L8 · Paso 1 de 3", onVolver = { navController.popBackStack() }) {
                    Text("¿Cómo te llamas?")
                    OutlinedTextField(value = vm.nombre, onValueChange = { vm.nombre = it }, label = { Text("Nombre") })
                    BotonPrincipal("Siguiente", enabled = vm.nombreValido) { navController.navigate(PasoCorreo) }
                }
            }

            composable<PasoCorreo> { entry ->
                val vm: RegistroViewModel = entry.viewModelDelGrafo(navController)
                PantallaBase(titulo = "L8 · Paso 2 de 3", onVolver = { navController.popBackStack() }) {
                    Text("Hola ${vm.nombre}, ¿cuál es tu correo?")
                    OutlinedTextField(value = vm.correo, onValueChange = { vm.correo = it }, label = { Text("Correo") })
                    BotonPrincipal("Siguiente", enabled = vm.correoValido) { navController.navigate(PasoConfirmar) }
                }
            }

            composable<PasoConfirmar> { entry ->
                val vm: RegistroViewModel = entry.viewModelDelGrafo(navController)
                PantallaBase(titulo = "L8 · Paso 3 de 3", onVolver = { navController.popBackStack() }) {
                    ValorRecibido("Nombre: ${vm.nombre}")
                    ValorRecibido("Correo: ${vm.correo}")
                    Explicacion(
                        "Los datos de los pasos 1 y 2 NO viajaron como argumentos: los tres pasos " +
                            "comparten el mismo RegistroViewModel, asociado a GrafoRegistro."
                    )
                    CodigoClave(
                        """
                        val padre = remember(entry) {
                            navController.getBackStackEntry<GrafoRegistro>()
                        }
                        val vm: RegistroViewModel = viewModel(padre)
                        """
                    )
                    BotonPrincipal("Confirmar registro") {
                        navController.navigate(RegistroExitoso(vm.nombre)) {
                            // Saca TODO el flujo de registro de la pila (y destruye su ViewModel)
                            popUpTo<GrafoRegistro> { inclusive = true }
                        }
                    }
                }
            }
        }

        composable<RegistroExitoso> { entry ->
            val ruta: RegistroExitoso = entry.toRoute()
            PantallaBase(titulo = "L8 · Listo", onVolver = { navController.popBackStack() }) {
                ValorRecibido("¡Bienvenido/a, ${ruta.nombre}!")
                Explicacion(
                    "Presiona atrás: vuelves a Bienvenida, no al paso 3, porque usamos " +
                        "popUpTo<GrafoRegistro> { inclusive = true }. Si creas otra cuenta, " +
                        "el formulario aparece vacío: el ViewModel del grafo se destruyó."
                )
            }
        }
    }
}
