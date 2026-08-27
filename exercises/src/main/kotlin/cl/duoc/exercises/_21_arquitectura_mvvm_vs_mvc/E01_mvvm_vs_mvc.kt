package cl.duoc.exercises._21_arquitectura_mvvm_vs_mvc

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ============================================================
// POR QUÉ MVVM y no MVC
// ============================================================
// MVC clásico (web): el Controller recibe requests, modifica el Model,
// elige la View. La View puede leer el Model directamente.
// MVVM: la View solo observa el ViewModel. La View es pasiva.
// Diferencia clave: en MVVM, el sentido de dependencias va
// View -> ViewModel -> Model. La View NUNCA toca el Model.
//
// DEMO: TaskList con MVC vs MVVM.

data class Task(val id: Int, val titulo: String)

// --- MVC: View lee Model directamente ---
class TaskListModelMVC {
    private val tasks = mutableListOf<Task>()
    fun add(task: Task) { tasks.add(task) }
    fun getAll(): List<Task> = tasks
}

// --- MVVM: ViewModel expone StateFlow; View solo observa ---

class TaskListViewModel {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    fun add(task: Task) { _tasks.value = _tasks.value + task }
}

fun main() {
    println("=== MVC ===")
    val mvc = TaskListModelMVC()
    mvc.add(Task(1, "Aprender MVC"))
    println(mvc.getAll())

    println("=== MVVM ===")
    val mvvm = TaskListViewModel()
    mvvm.add(Task(1, "Aprender MVVM"))
    println(mvvm.tasks.value)
}
