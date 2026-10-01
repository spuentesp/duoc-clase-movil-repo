# Guía de Hilt: qué es y cómo se usa

## 1. El problema que resuelve

Una clase casi nunca trabaja sola: un ViewModel necesita un repositorio, el
repositorio necesita una API o una base de datos, etc. A eso que necesita se le
llama **dependencia**.

Hay dos formas de conseguir una dependencia:

```kotlin
// ❌ La clase la CREA ella misma
class CursosViewModel : ViewModel() {
    private val repo = RepositorioCursosEnMemoria()
}

// ✅ La clase la RECIBE por el constructor  ← esto es "inyección de dependencias"
class CursosViewModel(private val repo: RepositorioCursos) : ViewModel()
```

La segunda forma es mejor porque:

- **Se puede cambiar la implementación** (memoria → API → base de datos) sin tocar el ViewModel.
- **Se puede testear** pasando un repositorio falso (ver `ListaCursosViewModelTest.kt`).
- **Se comparten instancias**: todas las pantallas usan el mismo repositorio.

Pero alguien tiene que crear el repositorio y pasárselo al ViewModel. En la
**lección 10** lo hacemos a mano con un `ContenedorDependencias` y una `Factory`
por cada ViewModel. Funciona, pero en una app grande se vuelve mucho código repetido.

## 2. ¿Qué es Hilt?

**Hilt** es la librería oficial de Google para hacer inyección de dependencias en
Android. Está construida sobre **Dagger**.

La idea es simple: tú marcas tus clases con **anotaciones** (`@Inject`, `@Module`…)
y Hilt **genera automáticamente el código** que crea los objetos y los conecta.
Ese código se genera al compilar (con KSP), así que los errores aparecen al
compilar y no cuando la app ya está corriendo.

> En resumen: Hilt hace por ti lo que en la lección 10 hacían el
> `ContenedorDependencias` y las `Factory`.

## 3. Las anotaciones, una por una

| Anotación | Dónde va | Qué le dice a Hilt |
| --- | --- | --- |
| `@HiltAndroidApp` | La clase `Application` | "Aquí empieza todo: crea el contenedor principal de la app" |
| `@AndroidEntryPoint` | La `Activity` (o Fragment) | "Esta clase puede recibir dependencias" |
| `@Inject constructor(...)` | El constructor de una clase tuya | "Sabes crear esta clase: usa este constructor y busca lo que pide" |
| `@Singleton` | Una clase o un `@Provides` | "Crea **una sola** instancia para toda la app" |
| `@Module` + `@InstallIn(...)` | Una clase u `object` | "Aquí hay recetas para lo que no puedes deducir solo" |
| `@Binds` | Función abstracta en un módulo | "Cuando pidan esta **interfaz**, entrega esta **implementación**" |
| `@Provides` | Función en un módulo | "Para crear esto, ejecuta este código" (librerías externas, configuración) |
| `@HiltViewModel` | Un `ViewModel` | "Crea este ViewModel con sus dependencias" (ya no hace falta `Factory`) |
| `hiltViewModel()` | En un `@Composable` | "Dame el ViewModel, creado por Hilt" |

¿Por qué hace falta `@Binds` para una interfaz? Porque una interfaz no tiene
constructor: Hilt no puede adivinar cuál de las implementaciones quieres.

¿Cuándo `@Provides` y no `@Inject`? Cuando la clase no es tuya y no puedes anotar
su constructor (Retrofit, Room, `OkHttpClient`) o cuando crearla requiere código.

## 4. Paso a paso para agregarlo a un proyecto

Todo esto ya está hecho en este proyecto. Úsalo como referencia.

**Paso 1. Plugins y dependencias** (`gradle/libs.versions.toml` y `app/build.gradle.kts`)

```kotlin
plugins {
    id("com.google.devtools.ksp")          // genera el código de Hilt al compilar
    id("com.google.dagger.hilt.android")
}

dependencies {
    implementation("com.google.dagger:hilt-android:<versión>")
    ksp("com.google.dagger:hilt-android-compiler:<versión>")
    implementation("androidx.hilt:hilt-navigation-compose:<versión>") // hiltViewModel()
}
```

**Paso 2. Application** (`NavegacionApp.kt`), registrada en el `AndroidManifest.xml` con `android:name=".NavegacionApp"`

```kotlin
@HiltAndroidApp
class NavegacionApp : Application()
```

**Paso 3. Activity** (`MainActivity.kt`)

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() { ... }
```

**Paso 4. Tus clases con `@Inject`** (`leccion11/RepositorioTareas.kt`)

```kotlin
@Singleton
class RepositorioTareasEnMemoria @Inject constructor() : RepositorioTareas { ... }
```

**Paso 5. Módulo para interfaces y cosas externas** (`leccion11/ModulosHilt.kt`)

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class ModuloRepositorios {
    @Binds @Singleton
    abstract fun vincularRepositorioTareas(impl: RepositorioTareasEnMemoria): RepositorioTareas
}

@Module
@InstallIn(SingletonComponent::class)
object ModuloConfiguracion {
    @Provides
    fun proveerConfiguracion(): ConfiguracionTareas = ConfiguracionTareas("Mis tareas Duoc")
}
```

**Paso 6. ViewModel** (`leccion11/TareasViewModels.kt`)

```kotlin
@HiltViewModel
class DetalleTareaViewModel @Inject constructor(
    private val repositorio: RepositorioTareas,   // Hilt lo saca del @Binds
    savedStateHandle: SavedStateHandle            // Hilt lo entrega con los argumentos de la ruta
) : ViewModel() {
    val tareaId = savedStateHandle.toRoute<DetalleTarea>().tareaId
}
```

**Paso 7. Pantalla** (`leccion11/Leccion11Hilt.kt`)

```kotlin
@Composable
fun PantallaDetalleTarea(viewModel: DetalleTareaViewModel = hiltViewModel()) { ... }
```

## 5. ¿Qué pasa cuando abres la pantalla?

```
hiltViewModel()
   └─ Hilt necesita DetalleTareaViewModel
        ├─ pide RepositorioTareas  → @Binds dice: usa RepositorioTareasEnMemoria
        │     └─ ¿ya existe? (@Singleton) → sí: entrega la MISMA instancia
        └─ pide SavedStateHandle   → lo trae la entrada de navegación (con tareaId)
```

Dentro de un `NavHost`, `hiltViewModel()` asocia el ViewModel a esa pantalla de la
pila: cuando haces `popBackStack()` el ViewModel se destruye. El repositorio
`@Singleton`, en cambio, sigue vivo; por eso el detalle y la lista ven los mismos datos.

## 6. Cómo se prueba algo que usa Hilt

Un `@HiltViewModel` sigue siendo una clase normal: en un test se construye **a mano**
pasando lo que quieras. No hace falta Hilt:

```kotlin
val vm = ListaTareasViewModel(RepositorioTareasEnMemoria(), ConfiguracionTareas("Test"))
```

Ver `app/src/test/java/cl/duoc/navegacion/leccion11/TareasViewModelTest.kt`.

## 7. Errores comunes

| Error | Causa | Solución |
| --- | --- | --- |
| `Hilt Activity must be attached to an @HiltAndroidApp Application` | Falta `@HiltAndroidApp` o `android:name` en el manifest | Revisa los pasos 2 y el `AndroidManifest.xml` |
| `... cannot be provided without an @Provides-annotated method` | Pides una interfaz o clase externa sin receta | Agrega un `@Binds` o `@Provides` en un módulo |
| `Cannot create an instance of class ...ViewModel` | Usaste `viewModel()` en vez de `hiltViewModel()`, o falta `@HiltViewModel` | Usa `hiltViewModel()` y anota el ViewModel |
| La Activity no recibe dependencias | Falta `@AndroidEntryPoint` | Paso 3 |
| Cambios en una pantalla no se ven en otra | El repositorio no es `@Singleton` (cada ViewModel recibe uno nuevo) | Agrega `@Singleton` |
