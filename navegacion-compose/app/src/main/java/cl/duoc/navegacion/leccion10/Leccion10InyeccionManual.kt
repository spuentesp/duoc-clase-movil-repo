package cl.duoc.navegacion.leccion10

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
// LECCIÓN 10 — Inyección de dependencias MANUAL + navegación
//
// Recorrido completo:
//   Pantalla --pide--> viewModel(factory = X.Factory)
//   Factory  --toma--> repositorio del ContenedorDependencias (en NavegacionApp)
//            --toma--> argumentos de la ruta (SavedStateHandle)
//   ViewModel --usa--> RepositorioCursos (interfaz)
//
// Archivos de esta lección:
//   RepositorioCursos.kt       -> interfaz + implementación
//   ContenedorDependencias.kt  -> quién construye las dependencias
//   CursosViewModels.kt        -> ViewModels con constructor + Factory
//   (test) ListaCursosViewModelTest.kt -> el ViewModel probado con un repositorio falso
// =====================================================================

@Serializable data object ListaCursos
@Serializable data class DetalleCurso(val cursoId: Int)

@Composable
fun Leccion10InyeccionManual(onSalir: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ListaCursos) {
        composable<ListaCursos> {
            PantallaListaCursos(
                onAbrir = { id -> navController.navigate(DetalleCurso(id)) },
                onSalir = onSalir
            )
        }
        composable<DetalleCurso> {
            PantallaDetalleCurso(onVolver = { navController.popBackStack() })
        }
    }
}

@Composable
private fun PantallaListaCursos(
    onAbrir: (Int) -> Unit,
    onSalir: () -> Unit,
    viewModel: ListaCursosViewModel = viewModel(factory = ListaCursosViewModel.Factory)
) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

    PantallaBase(titulo = "L10 · Cursos (DI manual)", onVolver = onSalir) {
        Explicacion(
            "El ViewModel recibe el repositorio por su constructor. La pantalla no sabe " +
                "de dónde vienen los datos: solo pide el ViewModel con su Factory."
        )
        CodigoClave(
            """
            class ListaCursosViewModel(
                private val repositorio: RepositorioCursos
            ) : ViewModel()

            viewModel(factory = ListaCursosViewModel.Factory)
            """
        )
        when (val e = estado) {
            is EstadoCursos.Cargando -> CircularProgressIndicator()
            is EstadoCursos.Error -> {
                Text("Error: ${e.mensaje}")
                BotonPrincipal("Reintentar", onClick = viewModel::cargar)
            }
            is EstadoCursos.Exito -> e.cursos.forEach { curso ->
                BotonPrincipal("${curso.sigla} — ${curso.nombre}") { onAbrir(curso.id) }
            }
        }
    }
}

@Composable
private fun PantallaDetalleCurso(
    onVolver: () -> Unit,
    viewModel: DetalleCursoViewModel = viewModel(factory = DetalleCursoViewModel.Factory)
) {
    val curso by viewModel.curso.collectAsStateWithLifecycle()
    val cargando by viewModel.cargando.collectAsStateWithLifecycle()

    PantallaBase(titulo = "L10 · Curso #${viewModel.cursoId}", onVolver = onVolver) {
        val c = curso
        when {
            cargando -> CircularProgressIndicator()
            c == null -> Text("El curso no existe")
            else -> {
                ValorRecibido(c.sigla)
                ValorRecibido(c.nombre)
                Text("Créditos: ${c.creditos}")
            }
        }
        Explicacion(
            "La Factory juntó dos cosas: el repositorio (del contenedor) y el cursoId " +
                "(de la ruta). Ambos llegaron al ViewModel por su constructor."
        )
        CodigoClave(
            """
            initializer {
                val app = this[APPLICATION_KEY] as NavegacionApp
                val ruta = createSavedStateHandle()
                    .toRoute<DetalleCurso>()
                DetalleCursoViewModel(
                    app.contenedor.repositorioCursos,
                    ruta.cursoId
                )
            }
            """
        )
    }
}
