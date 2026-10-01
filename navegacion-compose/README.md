# Navegación en Jetpack Compose — de cero a inyección de dependencias

App Android de estudio que enseña **solo** navegación en Compose, paso a paso.
Cada lección es un ejemplo **independiente** (su propio `NavHost` y `NavController`)
que puedes ejecutar en la app y copiar a tu proyecto.

Cada pantalla muestra en la app la explicación del concepto y el fragmento de código clave.

📘 Guías complementarias:
- [`GUIA_HILT.md`](GUIA_HILT.md): qué es la inyección de dependencias, qué es Hilt y cómo agregarlo paso a paso.
- [`GUIA_ROBOLECTRIC.md`](GUIA_ROBOLECTRIC.md): qué es Robolectric y cómo probar pantallas y navegación sin emulador.

## Cómo abrirlo

1. Android Studio → **File → Open…** → selecciona la carpeta `navegacion-compose`
   (es un proyecto Gradle independiente del resto del repo).
2. Espera la sincronización de Gradle y ejecuta la configuración **app** en un emulador o teléfono.

Desde la terminal:

```bash
cd navegacion-compose
./gradlew assembleDebug        # compila el APK
./gradlew testDebugUnitTest    # 18 tests (ViewModels + recorridos de navegación con Robolectric)
```

Requisitos: Android Studio reciente con JDK 21 (el que trae Android Studio sirve), compileSdk 36, minSdk 24.

## Conceptos base (léelos antes de la lección 1)

| Concepto | Qué es | Analogía |
| --- | --- | --- |
| **Ruta** | Identificador de una pantalla (`"perfil"` o una clase `@Serializable`) | La dirección de una casa |
| **NavController** | Objeto que cambia de pantalla (`navigate`) y retrocede (`popBackStack`) | El control remoto |
| **NavHost** | Composable que muestra la pantalla de la ruta actual | La tele |
| **Grafo de navegación** | El conjunto de rutas declaradas dentro del `NavHost` | El mapa |
| **Back stack (pila)** | Historial de pantallas abiertas; la de arriba es la visible | Una pila de platos |
| **NavBackStackEntry** | Cada elemento de la pila: tiene sus argumentos, su `savedStateHandle` y sus ViewModels | Cada plato con su etiqueta |

Reglas que se repiten en todas las lecciones:

- La pantalla **no recibe el `NavController`**: recibe lambdas (`onIrAPerfil: () -> Unit`). Es más fácil de reutilizar y de probar.
- Por la ruta se pasan **datos simples** (un id, un texto corto), **nunca objetos completos**. La pantalla de destino usa el id para buscar el resto.
- La app tiene **una sola Activity**: las pantallas son funciones `@Composable`.

## Lecciones (en orden)

| # | Lección | Aprendes | Archivo |
| --- | --- | --- | --- |
| 1 | Navegación básica | `rememberNavController`, `NavHost`, `composable("ruta")`, `navigate`, `popBackStack` | `leccion01/` |
| 2 | Argumentos obligatorios | `"detalle/{id}/{nombre}"`, `navArgument`, `NavType`, `Uri.encode` | `leccion02/` |
| 3 | Argumentos opcionales | `"busqueda?texto={texto}"`, `nullable`, `defaultValue` | `leccion03/` |
| 4 | **Navegación type-safe** (recomendada) | Rutas `@Serializable`, `composable<T>`, `toRoute()`, enums y opcionales | `leccion04/` |
| 5 | Pila de navegación | `popUpTo`, `inclusive`, `launchSingleTop`, login y cerrar sesión | `leccion05/` |
| 6 | Devolver resultados | `previousBackStackEntry.savedStateHandle` | `leccion06/` |
| 7 | ViewModel + argumentos | `SavedStateHandle.toRoute()` dentro del ViewModel; ciclo de vida por pantalla | `leccion07/` |
| 8 | Grafos anidados | `navigation<Grafo>`, ViewModel compartido por un flujo, `popUpTo<Grafo>` | `leccion08/` |
| 9 | Bottom Navigation | `NavigationBar`, `hierarchy`, `saveState`/`restoreState` | `leccion09/` |
| 10 | Inyección de dependencias **manual** | Interfaz de repositorio, contenedor, `viewModelFactory { initializer { } }` | `leccion10/` |
| 11 | Inyección de dependencias con **Hilt** | `@HiltAndroidApp`, `@AndroidEntryPoint`, `@Module`, `@Binds`, `@Provides`, `@HiltViewModel`, `hiltViewModel()` | `leccion11/` |

Todo el código está en `app/src/main/java/cl/duoc/navegacion/`.

## Resumen rápido (cheat sheet)

```kotlin
// 1. Rutas type-safe
@Serializable data object Inicio
@Serializable data class Detalle(val id: Int, val filtro: String? = null)

// 2. NavHost
val navController = rememberNavController()
NavHost(navController, startDestination = Inicio) {
    composable<Inicio> {
        PantallaInicio(onAbrir = { id -> navController.navigate(Detalle(id)) })
    }
    composable<Detalle> { entry ->
        val ruta: Detalle = entry.toRoute()
        PantallaDetalle(ruta.id, onVolver = { navController.popBackStack() })
    }
}

// 3. Controlar la pila
navController.navigate(Home) { popUpTo<Login> { inclusive = true } }   // login
navController.navigate(Home) { launchSingleTop = true }                // sin duplicados

// 4. Devolver un resultado
navController.previousBackStackEntry?.savedStateHandle?.set("clave", valor)
navController.popBackStack()

// 5. Argumentos en el ViewModel
class DetalleViewModel(handle: SavedStateHandle) : ViewModel() {
    val id = handle.toRoute<Detalle>().id
}

// 6. Con Hilt
@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val repo: Repositorio,
    handle: SavedStateHandle
) : ViewModel()

val vm: DetalleViewModel = hiltViewModel()
```

## ¿DI manual o Hilt?

| | Manual (lección 10) | Hilt (lección 11) |
| --- | --- | --- |
| Quién crea las dependencias | `ContenedorDependencias` | Hilt, a partir de `@Inject`, `@Binds` y `@Provides` |
| Factory del ViewModel | La escribes tú (`viewModelFactory`) | No hace falta (`@HiltViewModel`) |
| Configuración | Ninguna librería | Plugins `hilt` + `ksp` y anotaciones |
| Recomendado para | Entender el concepto, apps pequeñas | Apps medianas y grandes |

En ambos casos el ViewModel recibe sus dependencias **por el constructor**, y por eso
se puede probar con un repositorio falso (ver `app/src/test/`).

Explicación completa de Hilt: [`GUIA_HILT.md`](GUIA_HILT.md).

## Tests

Los tests corren en el computador, sin emulador, gracias a **Robolectric** (una librería
que simula Android dentro de la JVM). Explicación completa: [`GUIA_ROBOLECTRIC.md`](GUIA_ROBOLECTRIC.md).

| Archivo | Qué comprueba |
| --- | --- |
| `NavegacionLeccionesTest.kt` | Recorre las lecciones 1 a 10 tocando botones (Robolectric, sin emulador) |
| `AppConHiltTest.kt` | Abre la app real y verifica que Hilt inyecta y comparte el repositorio |
| `leccion10/ListaCursosViewModelTest.kt` | ViewModel probado con un repositorio falso |
| `leccion11/TareasViewModelTest.kt` | ViewModels de Hilt construidos a mano y `SavedStateHandle(route = ...)` |

## Versiones

Kotlin 2.2 · AGP 8.13 · Compose BOM 2025.09 · Navigation 2.9 · Hilt 2.57 · KSP.
Todas están centralizadas en `gradle/libs.versions.toml`.
