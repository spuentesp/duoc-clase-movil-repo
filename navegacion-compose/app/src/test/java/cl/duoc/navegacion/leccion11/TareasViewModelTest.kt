package cl.duoc.navegacion.leccion11

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.testing.invoke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Los ViewModels de Hilt son clases normales: en un test se construyen "a mano"
 * pasando las dependencias que queramos. No hace falta Hilt para probarlos.
 *
 * Robolectric se usa solo porque toRoute() lee un Bundle de Android.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class TareasViewModelTest {

    @Before
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `la lista usa la configuracion inyectada`() {
        val vm = ListaTareasViewModel(RepositorioTareasEnMemoria(), ConfiguracionTareas("Título de prueba"))
        assertEquals("Título de prueba", vm.titulo)
        assertEquals(3, vm.tareas.value.size)
    }

    @Test
    fun `el detalle lee el id de la ruta desde SavedStateHandle`() = runTest {
        val repo = RepositorioTareasEnMemoria()
        // SavedStateHandle(route = ...) viene de navigation-testing: simula la navegación
        val vm = DetalleTareaViewModel(repo, SavedStateHandle(route = DetalleTarea(tareaId = 2)))
        // stateIn(WhileSubscribed) solo se actualiza si alguien lo observa (como la pantalla)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { vm.tarea.collect {} }

        assertEquals(2, vm.tareaId)
        assertEquals(false, vm.tarea.value?.completada)

        vm.alternar()
        assertEquals(true, vm.tarea.value?.completada)
    }

    @Test
    fun `dos ViewModels con el mismo repositorio ven los mismos cambios`() = runTest {
        val repoCompartido = RepositorioTareasEnMemoria()   // lo que hace @Singleton
        val lista = ListaTareasViewModel(repoCompartido, ConfiguracionTareas("x"))
        val detalle = DetalleTareaViewModel(repoCompartido, SavedStateHandle(route = DetalleTarea(1)))

        detalle.alternar()

        assertEquals(true, lista.tareas.value.first { it.id == 1 }.completada)
    }
}
