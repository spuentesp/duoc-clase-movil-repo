package cl.duoc.navegacion.leccion11

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// =====================================================================
// LECCIÓN 11 — Capa de datos para Hilt
// =====================================================================

data class Tarea(val id: Int, val titulo: String, val completada: Boolean = false)

interface RepositorioTareas {
    /** Flujo observable: las pantallas se actualizan solas cuando cambia. */
    val tareas: StateFlow<List<Tarea>>
    fun alternarCompletada(id: Int)
}

/**
 * @Inject constructor() -> Hilt sabe CÓMO crear esta clase (no necesita módulo para eso).
 * @Singleton            -> Hilt crea UNA sola instancia para toda la app.
 *
 * Como es singleton, si marcas una tarea en el detalle, la lista ve el cambio:
 * ambas pantallas comparten el mismo repositorio.
 */
@Singleton
class RepositorioTareasEnMemoria @Inject constructor() : RepositorioTareas {

    private val _tareas = MutableStateFlow(
        listOf(
            Tarea(1, "Leer la guía de navegación"),
            Tarea(2, "Hacer el ejercicio de argumentos"),
            Tarea(3, "Probar Hilt en mi proyecto")
        )
    )
    override val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    override fun alternarCompletada(id: Int) {
        _tareas.update { lista ->
            lista.map { if (it.id == id) it.copy(completada = !it.completada) else it }
        }
    }
}

/** Una dependencia "simple" que no es nuestra clase: se entrega con @Provides (ver ModulosHilt.kt). */
data class ConfiguracionTareas(val tituloLista: String)
