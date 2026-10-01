package cl.duoc.navegacion.leccion10

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * El beneficio de la inyección de dependencias: el ViewModel se prueba con un
 * repositorio FALSO, sin red, sin Android y sin esperar demoras reales.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ListaCursosViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    /** Repositorio falso: devuelve datos fijos o lanza un error. */
    private class RepositorioFalso(private val falla: Boolean = false) : RepositorioCursos {
        val cursos = listOf(Curso(1, "TEST101", "Curso de prueba", 5))
        override suspend fun obtenerCursos(): List<Curso> =
            if (falla) throw IllegalStateException("Sin conexión") else cursos
        override suspend fun obtenerCurso(id: Int): Curso? = cursos.firstOrNull { it.id == id }
    }

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `carga los cursos del repositorio inyectado`() = runTest(dispatcher) {
        val repo = RepositorioFalso()
        val vm = ListaCursosViewModel(repo)
        assertEquals(EstadoCursos.Cargando, vm.estado.value)

        advanceUntilIdle()

        assertEquals(EstadoCursos.Exito(repo.cursos), vm.estado.value)
    }

    @Test
    fun `muestra error si el repositorio falla`() = runTest(dispatcher) {
        val vm = ListaCursosViewModel(RepositorioFalso(falla = true))
        advanceUntilIdle()
        assertEquals(EstadoCursos.Error("Sin conexión"), vm.estado.value)
    }

    @Test
    fun `el detalle busca el curso con el id recibido`() = runTest(dispatcher) {
        val vm = DetalleCursoViewModel(RepositorioFalso(), cursoId = 1)
        advanceUntilIdle()
        assertEquals("TEST101", vm.curso.value?.sigla)
        assertEquals(false, vm.cargando.value)
    }
}
