package cl.duoc.navegacion.leccion11

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// =====================================================================
// LECCIÓN 11 — Módulos de Hilt: le enseñan a Hilt lo que no puede deducir solo
//
// Este archivo reemplaza al ContenedorDependencias de la lección 10.
//
// @Module                         -> "aquí hay recetas para crear dependencias"
// @InstallIn(SingletonComponent)  -> viven tanto como la app
//
// Dos formas de entregar una dependencia:
//   @Binds    -> "cuando pidan la INTERFAZ X, entrega la implementación Y"
//                (Y ya tiene @Inject constructor)
//   @Provides -> "para crear X, ejecuta este código"
//                (clases de librerías, configuración, Retrofit, Room, etc.)
// =====================================================================

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuloRepositorios {

    @Binds
    @Singleton
    abstract fun vincularRepositorioTareas(impl: RepositorioTareasEnMemoria): RepositorioTareas
}

@Module
@InstallIn(SingletonComponent::class)
object ModuloConfiguracion {

    @Provides
    fun proveerConfiguracion(): ConfiguracionTareas =
        ConfiguracionTareas(tituloLista = "Mis tareas Duoc")

    // Ejemplo típico en un proyecto real (no se usa aquí):
    // @Provides @Singleton
    // fun proveerRetrofit(): Retrofit = Retrofit.Builder().baseUrl("https://...").build()
}
