package cl.duoc.navegacion.leccion11

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.navegacion.ui.BotonPrincipal
import cl.duoc.navegacion.ui.CodigoClave
import cl.duoc.navegacion.ui.Explicacion
import cl.duoc.navegacion.ui.PantallaBase
import cl.duoc.navegacion.ui.ValorRecibido
import kotlinx.serialization.Serializable

// =====================================================================
// LECCIÓN 11 — Inyección de dependencias con HILT + navegación
//
// Pasos para usar Hilt en un proyecto (todos hechos en este proyecto):
//   1. Plugins: com.google.dagger.hilt.android + com.google.devtools.ksp
//      Dependencias: hilt-android, ksp(hilt-android-compiler), hilt-navigation-compose
//   2. @HiltAndroidApp en la clase Application   (NavegacionApp.kt)
//   3. @AndroidEntryPoint en la Activity           (MainActivity.kt)
//   4. @Module para interfaces / clases externas   (ModulosHilt.kt)
//   5. @HiltViewModel + @Inject constructor        (TareasViewModels.kt)
//   6. hiltViewModel() en la pantalla              (este archivo)
//
// Dentro de un NavHost, hiltViewModel() asocia el ViewModel a la entrada de la
// pila, igual que viewModel(): se destruye al hacer popBackStack.
// =====================================================================

@Serializable data object ListaTareas
@Serializable data class DetalleTarea(val tareaId: Int)

@Composable
fun Leccion11Hilt(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListaTareas) {
        composable<ListaTareas> {
            PantallaListaTareas(
                onAbrir = { id -> navController.navigate(DetalleTarea(id)) },
                onSalir = onSalir
            )
        }
        composable<DetalleTarea> {
            PantallaDetalleTarea(onVolver = { navController.popBackStack() })
        }
    }
}

@Composable
private fun PantallaListaTareas(
    onAbrir: (Int) -> Unit,
    onSalir: () -> Unit,
    viewModel: ListaTareasViewModel = hiltViewModel()   // <- sin Factory
) {
    val tareas by viewModel.tareas.collectAsStateWithLifecycle()

    PantallaBase(titulo = "L11 · ${viewModel.titulo}", onVolver = onSalir) {
        Explicacion(
            "El título viene de ConfiguracionTareas (@Provides) y las tareas del " +
                "RepositorioTareas (@Binds). La pantalla solo llama a hiltViewModel()."
        )
        CodigoClave(
            """
            @HiltViewModel
            class ListaTareasViewModel @Inject constructor(
                private val repositorio: RepositorioTareas,
                configuracion: ConfiguracionTareas
            ) : ViewModel()

            val vm: ListaTareasViewModel = hiltViewModel()
            """
        )
        tareas.forEach { tarea ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(checked = tarea.completada, onCheckedChange = { viewModel.alternar(tarea.id) })
                Text(tarea.titulo, modifier = Modifier.weight(1f))
                TextButton(onClick = { onAbrir(tarea.id) }) { Text("Ver") }
            }
        }
    }
}

@Composable
private fun PantallaDetalleTarea(
    onVolver: () -> Unit,
    viewModel: DetalleTareaViewModel = hiltViewModel()
) {
    val tarea by viewModel.tarea.collectAsStateWithLifecycle()

    PantallaBase(titulo = "L11 · Tarea #${viewModel.tareaId}", onVolver = onVolver) {
        val t = tarea
        if (t == null) {
            Text("La tarea no existe")
        } else {
            ValorRecibido(t.titulo)
            ValorRecibido(if (t.completada) "Estado: completada ✔" else "Estado: pendiente")
            BotonPrincipal(if (t.completada) "Marcar como pendiente" else "Marcar como completada", onClick = viewModel::alternar)
        }
        Explicacion(
            "Marca la tarea y vuelve: la lista ya muestra el cambio. Las dos pantallas " +
                "tienen ViewModels distintos, pero Hilt les inyectó el MISMO repositorio (@Singleton)."
        )
        CodigoClave(
            """
            @HiltViewModel
            class DetalleTareaViewModel @Inject constructor(
                private val repositorio: RepositorioTareas,
                savedStateHandle: SavedStateHandle
            ) : ViewModel() {
                val tareaId = savedStateHandle
                    .toRoute<DetalleTarea>().tareaId
            }
            """
        )
    }
}
