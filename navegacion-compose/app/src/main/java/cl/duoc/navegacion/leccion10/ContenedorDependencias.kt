package cl.duoc.navegacion.leccion10

// =====================================================================
// LECCIÓN 10 — El contenedor de dependencias (DI manual)
//
// Inyección de dependencias = una clase NO crea lo que necesita; lo RECIBE
// (normalmente por su constructor).
//
//   ❌ Sin DI:  class ListaCursosViewModel : ViewModel() {
//                  private val repo = RepositorioCursosEnMemoria()   // acoplado, imposible de cambiar en tests
//              }
//
//   ✅ Con DI:  class ListaCursosViewModel(private val repo: RepositorioCursos) : ViewModel()
//
// ¿Y quién crea el repositorio y se lo pasa? Este contenedor: un único lugar
// que sabe construir los objetos de la app. Vive en NavegacionApp, así que
// todas las pantallas comparten las mismas instancias.
// =====================================================================

class ContenedorDependencias {

    // Una sola instancia para toda la app (by lazy = se crea al primer uso)
    val repositorioCursos: RepositorioCursos by lazy { RepositorioCursosEnMemoria() }

    // Si mañana existe una API real, solo cambia ESTA línea:
    // val repositorioCursos: RepositorioCursos by lazy { RepositorioCursosApi(retrofit) }
}
