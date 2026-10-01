package cl.duoc.navegacion.leccion10

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import cl.duoc.navegacion.NavegacionApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// =====================================================================
// LECCIÓN 10 — ViewModels con dependencias en el constructor
//
// Problema: viewModel() por defecto solo sabe crear ViewModels con constructor
// vacío (o con SavedStateHandle). Si el constructor pide un repositorio, hay que
// darle una FACTORY que explique cómo construirlo.
//
// Solución: viewModelFactory { initializer { ... } } dentro del companion object.
// =====================================================================

/** Estados posibles de la pantalla de lista. */
sealed interface EstadoCursos {
    data object Cargando : EstadoCursos
    data class Exito(val cursos: List<Curso>) : EstadoCursos
    data class Error(val mensaje: String) : EstadoCursos
}

class ListaCursosViewModel(
    private val repositorio: RepositorioCursos   // <- inyectado por constructor
) : ViewModel() {

    private val _estado = MutableStateFlow<EstadoCursos>(EstadoCursos.Cargando)
    val estado: StateFlow<EstadoCursos> = _estado.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _estado.value = EstadoCursos.Cargando
            _estado.value = try {
                EstadoCursos.Exito(repositorio.obtenerCursos())
            } catch (e: Exception) {
                EstadoCursos.Error(e.message ?: "Error desconocido")
            }
        }
    }

    companion object {
        /** Factory: saca el repositorio del contenedor que vive en NavegacionApp. */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NavegacionApp
                ListaCursosViewModel(repositorio = app.contenedor.repositorioCursos)
            }
        }
    }
}

class DetalleCursoViewModel(
    private val repositorio: RepositorioCursos,  // <- dependencia
    val cursoId: Int                             // <- argumento de navegación
) : ViewModel() {

    private val _curso = MutableStateFlow<Curso?>(null)
    val curso: StateFlow<Curso?> = _curso.asStateFlow()

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        viewModelScope.launch {
            _curso.value = repositorio.obtenerCurso(cursoId)
            _cargando.value = false
        }
    }

    companion object {
        /**
         * La factory combina DOS fuentes:
         *  - la dependencia, desde el contenedor;
         *  - el argumento de la ruta, desde createSavedStateHandle().toRoute().
         * El ViewModel recibe valores simples, por lo que es fácil de testear.
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NavegacionApp
                val ruta = createSavedStateHandle().toRoute<DetalleCurso>()
                DetalleCursoViewModel(app.contenedor.repositorioCursos, ruta.cursoId)
            }
        }
    }
}
