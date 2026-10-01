package cl.duoc.navegacion.leccion10

import kotlinx.coroutines.delay

// =====================================================================
// LECCIÓN 10 — Capa de datos
//
// El ViewModel dependerá de la INTERFAZ RepositorioCursos, no de una clase concreta.
// Así podemos cambiar de dónde vienen los datos (memoria, API, base de datos,
// un "fake" para tests) sin tocar el ViewModel ni las pantallas.
// =====================================================================

data class Curso(val id: Int, val sigla: String, val nombre: String, val creditos: Int)

/** Contrato: QUÉ se puede hacer con los cursos (no dice CÓMO). */
interface RepositorioCursos {
    suspend fun obtenerCursos(): List<Curso>
    suspend fun obtenerCurso(id: Int): Curso?
}

/** Implementación real de ejemplo: datos en memoria con una demora que simula la red. */
class RepositorioCursosEnMemoria(
    private val demoraMs: Long = 600
) : RepositorioCursos {

    private val cursos = listOf(
        Curso(1, "DSY1105", "Desarrollo de Aplicaciones Móviles", 10),
        Curso(2, "PGY3121", "Programación de Base de Datos", 8),
        Curso(3, "ASY4131", "Arquitectura de Software", 8)
    )

    override suspend fun obtenerCursos(): List<Curso> {
        delay(demoraMs)
        return cursos
    }

    override suspend fun obtenerCurso(id: Int): Curso? {
        delay(demoraMs)
        return cursos.firstOrNull { it.id == id }
    }
}
