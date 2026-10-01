package cl.duoc.navegacion.leccion11

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// =====================================================================
// LECCIÓN 11 — ViewModels con Hilt
//
// @HiltViewModel + @Inject constructor(...)  -> Hilt crea el ViewModel y le
// entrega todo lo que pide el constructor. ¡No hay que escribir Factory!
// (compara con los companion object Factory de la lección 10)
//
// Hilt también entrega el SavedStateHandle con los argumentos de la ruta.
// =====================================================================

@HiltViewModel
class ListaTareasViewModel @Inject constructor(
    private val repositorio: RepositorioTareas,   // Hilt usa el @Binds del módulo
    configuracion: ConfiguracionTareas            // Hilt usa el @Provides del módulo
) : ViewModel() {

    val titulo: String = configuracion.tituloLista
    val tareas: StateFlow<List<Tarea>> = repositorio.tareas

    fun alternar(id: Int) = repositorio.alternarCompletada(id)
}

@HiltViewModel
class DetalleTareaViewModel @Inject constructor(
    private val repositorio: RepositorioTareas,
    savedStateHandle: SavedStateHandle            // trae los argumentos de la ruta
) : ViewModel() {

    val tareaId: Int = savedStateHandle.toRoute<DetalleTarea>().tareaId

    val tarea: StateFlow<Tarea?> = repositorio.tareas
        .map { lista -> lista.firstOrNull { it.id == tareaId } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = repositorio.tareas.value.firstOrNull { it.id == tareaId }
        )

    fun alternar() = repositorio.alternarCompletada(tareaId)
}
