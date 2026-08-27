# Design — Unificación de branches + sección de ejercicios Kotlin

**Fecha**: 2026-08-27
**Autor**: brainstorming con el usuario
**Estado**: aprobado por el usuario en todas sus secciones

## Objetivo

Convertir `duoc-clase-movil-repo` en un único recurso de estudio de desarrollo Android con Kotlin. El repo queda con un solo branch (`main`) que contiene:

1. La base Android original (consolidada).
2. La sección "Componentes Material Design" (de `feature/material-showcase`).
3. La sección "Capacidades Nativas" (de `native-capabilities-show`).
4. Una sección nueva "Ejercicios Android" con mini-apps dentro de la app.
5. Un módulo hermano `exercises/` con ejercicios Kotlin JVM progresivos.
6. Documentación nueva que cubre capacidades nativas, ejercicios Kotlin, ejercicios Android y principios de arquitectura.

## Topología resultante

Un único branch `main`. Las branches `feature/material-showcase` y `native-capabilities-show` se borran del remoto después del merge.

```
duoc-clase-movil-repo/
├── app/                           # Android (Material + Nativo + Ejercicios)
│   └── src/main/java/com/example/baseproject/
│       ├── MainActivity.kt        # aloja RootNav
│       └── ui/
│           ├── screens/
│           │   ├── RootMenuScreen.kt        # NUEVO
│           │   ├── material/                # (de feature/material-showcase)
│           │   ├── native/                  # (de native-capabilities-show)
│           │   └── exercises/               # NUEVO (8 mini-apps)
│           ├── navigation/
│           │   ├── RootNav.kt               # NUEVO
│           │   ├── MaterialNav.kt           # renombrado de ShowcaseNavigation.kt
│           │   ├── NativeNav.kt             # renombrado de NavGraph.kt
│           │   └── ExercisesNav.kt          # NUEVO
│           └── theme/...
├── exercises/                     # NUEVO proyecto JVM hermano
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   ├── gradle/wrapper/...
│   ├── README.md
│   └── src/
│       ├── main/kotlin/cl/duoc/exercises/
│       │   ├── _00_variables/ ... _13_exception_handling/
│       │   ├── _14_coroutines_basics/        # 6 ejercicios de coroutines
│       │   ├── _15_coroutines_structured/
│       │   ├── _16_coroutines_dispatchers/
│       │   ├── _17_coroutines_flow/
│       │   ├── _18_coroutines_state_flow/
│       │   ├── _19_coroutines_error_handling/
│       │   ├── _20_arquitectura_separacion/  # 6 cápsulas de Arquitectura
│       │   ├── _21_arquitectura_mvvm_vs_mvc/
│       │   ├── _22_arquitectura_repository/
│       │   ├── _23_arquitectura_udf/
│       │   ├── _24_arquitectura_di_manual/
│       │   ├── _25_arquitectura_sealed_state/
│       │   ├── _26_android_compose_preview/  # 4 bridge a Android
│       │   ├── _27_android_viewmodel/
│       │   ├── _28_android_networking/
│       │   ├── _29_android_persistence/
│       │   └── _30_kata_integrador/
│       └── test/kotlin/cl/duoc/exercises/    # JUnit 5 por ejercicio
├── docs/
│   ├── 00-Fundamentos-Kotlin.md (existente)
│   ├── 01-Composables.md (existente)
│   ├── 02-Navigation.md (existente)
│   ├── 03-MVVM-y-Repository.md (existente)
│   ├── 04-conectando-pokeapi-a-datos.md (existente)
│   ├── 05-Empaquetado-y-Distribucion.md (conservar versión más completa)
│   ├── 06-Personalizacion-de-la-App.md (restaurar; native lo borró)
│   ├── 07-Componentes-UI-Material-Design.md (de feature)
│   ├── 08-Guia-Capacidades-Nativas.md        # NUEVO
│   ├── 09-Guia-Ejercicios-Kotlin.md          # NUEVO
│   ├── 10-Guia-Ejercicios-Android.md         # NUEVO
│   ├── 11-Principios-de-Arquitectura.md      # NUEVO
│   ├── books/                                # gitignored (epubs)
│   └── README.md (actualizado)
├── .gitignore (actualizado)
├── build.gradle.kts
├── settings.gradle.kts (incluye :app y :exercises)
├── gradle/wrapper/...
└── README.md (actualizado)
```

## Componentes

### 1. Menú raíz (`:app`)

`RootMenuScreen` muestra 3 tarjetas:
- 🎨 Componentes Material → `MaterialNav()`
- 📱 Capacidades Nativas → `NativeNav()`
- 🧪 Ejercicios Android → `ExercisesNav()`

`RootNav` es el `NavHost` raíz; cada NavGraph hijo conserva su propia navegación interna sin cambios estructurales.

### 2. Sección Material

Viene tal cual de `feature/material-showcase`. Se renombran rutas y archivos pero el contenido (botones, cards, dialogs, listas, appbars, textfields, theming, navigation, icons, selectioncontrols, bottomsheets) se conserva.

### 3. Sección Nativas

Viene de `native-capabilities-show`. Pantallas: Biometric, Camera, Flashlight, Local Storage, Location, Notifications, Vibration, Accelerometer, Battery.

### 4. Sección Ejercicios Android (nueva dentro de `:app`)

8 mini-apps. Cada una es una pantalla `@Composable` con un `MiniAppScaffold` reutilizable que alterna entre **enunciado** y **solución** (vista como `CodeBlock` con scroll).

| # | Mini-app | Concepto Compose/VM principal | Notas |
|---|----------|-------------------------------|-------|
| 1 | Counter | `remember`, `mutableStateOf`, `Button`, `Text` | Runnable |
| 2 | List | `LazyColumn`, `mutableStateListOf`, agregar/eliminar | Runnable |
| 3 | Form | `TextField`, validación, mostrar errores | Runnable |
| 4 | Navigation | Multi-pantalla, argumentos en NavController | Runnable |
| 5 | ViewModel | `viewModel()`, `viewModelScope`, sobrevive a rotación | Runnable |
| 6 | Repository | Repositorio fake con interface | Runnable |
| 7 | Network | Retrofit + suspend + sealed `Result` | Usa pokeapi fake (delay) |
| 8 | Room preview | Room + Flow + Repository | Solo enunciado + solución como `CodeBlock` |

Room no se agrega como dependencia real (mantiene simple la app). El ejercicio 8 es conceptual.

### 5. Módulo `exercises/` (JVM hermano)

Proyecto Kotlin JVM independiente con Gradle Kotlin DSL, Kotlin 1.9.20, JUnit 5. Está **dentro** del repo como subcarpeta `exercises/`, pero **no** es un módulo del proyecto Android raíz (settings.gradle.kts del proyecto exercises/ es independiente). Esto le da aislamiento de dependencias (sin androidx, sin Compose) y velocidad de iteración.

**Catálogo** (33 ejercicios: 32 temáticos + 1 kata integrador, en 6 bloques):

**Bloque 1 — Kotlin básico (7)**
1. var/val + tipos primitivos
2. Strings: templates, multilínea, raw strings
3. Control de flujo: `if` expr, `when` (con arg, sin arg, rangos, tipos)
4. Funciones: default args, named args, single-expression, `vararg`, infix
5. Lambdas y funciones de orden superior
6. Null safety: `?`, `?:`, `!!`, `let`, safe cast
7. Colecciones: List/Set/Map, inmutables vs mutables, operaciones funcionales

**Bloque 2 — Kotlin intermedio (5)**
8. Clases: constructores primarios/secundarios, init blocks
9. Data classes: `copy()`, destructuring, `componentN()`
10. Sealed classes: modelar jerarquías cerradas
11. Interfaces y herencia
12. Generics: funciones/clases genéricas, variance

**Bloque 3 — Kotlin avanzado — características (4)**
13. Scope functions: `let`, `apply`, `also`, `run`, `with`
14. Extension functions y properties
15. Property delegates: `lazy`, `observable`, `vetoable`, custom
16. Exception handling: `try`/`catch`/`finally`, `runCatching`, excepciones custom

**Bloque 4 — Coroutines en profundidad (6)**
17. Basics: `launch`, `async`/`await`, `runBlocking`, `Job`, `CoroutineScope`
18. Structured concurrency: `coroutineScope`, `supervisorScope`, cancelación
19. Dispatchers & context: `Dispatchers.Main/IO/Default`, `withContext`
20. Flow fundamentals: `flow {}`, `emit`, `collect`, operadores
21. StateFlow y SharedFlow: `MutableStateFlow`, `MutableSharedFlow`, conflation
22. Errores en coroutines: `CoroutineExceptionHandler`, `try/catch`, `Flow.catch`

**Bloque 5 — Arquitectura de Software (6 cápsulas "por qué")**
23. ¿Por qué separar UI de lógica?
24. ¿Por qué MVVM y no MVC?
25. ¿Por qué Repository Pattern?
26. ¿Por qué Unidirectional Data Flow?
27. ¿Por qué Inyección de dependencias (manual)?
28. ¿Por qué sealed classes para UI state?

Cada cápsula combina:
- Comentario `// POR QUÉ:` con la explicación razonada.
- Código `// DEMO:` ejecutable que ilustra el principio.
- Comparación lado a lado "mal vs bien" cuando aplica.

**Bloque 6 — Bridge a Android (4)**
29. Compose preview conceptual (`@Composable`, `remember`, `LaunchedEffect`)
30. ViewModel conceptual (`viewModelScope`, `SavedStateHandle`)
31. Networking conceptual (suspend + Retrofit + sealed Result)
32. Persistencia conceptual (Room + Flow + Repository)

Más un **kata integrador** (`_30_kata_integrador/`) que combina varios temas. Total: 33 archivos en main + 33 tests.

**Convención por ejercicio**:

```kotlin
package cl.duoc.exercises._XX_tema

/**
 * EJERCICIO: <título corto>
 *
 * OBJETIVO: <qué aprende>
 *
 * POR QUÉ IMPORTA: <motivación>
 *
 * INSTRUCCIONES:
 * 1. <paso>
 * 2. <paso>
 *
 * DIFICULTAD: 🟢 / 🟡 / 🔴
 *
 * TEST: ./gradlew :exercises:test --tests "*<id>*"
 */
fun <nombre>(<args>): <ReturnType> = TODO()
```

Cada ejercicio lleva un `*Test.kt` hermano en `src/test/kotlin/...` con JUnit 5. Los tests fallan hasta que el alumno implementa la función. Esto es **esperado** y se documenta en el README.

### 6. Documentación nueva

| Archivo | Propósito |
|---------|-----------|
| `docs/08-Guia-Capacidades-Nativas.md` | Índice de las 8 pantallas nativas + permisos + consideraciones |
| `docs/09-Guia-Ejercicios-Kotlin.md` | Índice del módulo `exercises/`, cómo correr, glosario, ruta |
| `docs/10-Guia-Ejercicios-Android.md` | Índice de las 8 mini-apps dentro de `app/` |
| `docs/11-Principios-de-Arquitectura.md` | Compendio escrito de las cápsulas "por qué" |

### 7. `.gitignore`

Se agrega:
```
# Libros de referencia (no se versionan; cada alumno los trae localmente)
docs/books/
*.epub

# Builds del módulo exercises
exercises/build/
exercises/.gradle/
```

Los 2 archivos `.epub` actuales se mueven a `docs/books/` antes del commit de reorganización.

## Plan de ejecución

### Fase 0 — Preparación
- Backup del repo: `git bundle create .git-backup/backup-pre-merge.bundle --all`.
- Estado limpio en `main` (`git pull --ff-only`).
- Mover `docs/*.epub` a `docs/books/`.

### Fase 1 — Merge `feature/material-showcase`
- `git merge --no-ff origin/feature/material-showcase`.
- Verificar `./gradlew :app:assembleDebug`.

### Fase 2 — Merge `native-capabilities-show`
- `git merge --no-ff origin/native-capabilities-show`.
- Resolver conflictos:
  - `app/build.gradle.kts`: combinar deps.
  - `MainActivity.kt`: descartar ambos lados (se reescribe en fase 3).
  - `docs/05-Empaquetado-y-Distribucion.md`: conservar versión más completa.
  - `docs/06-Personalizacion-de-la-App.md`: descartar el borrado (mantener versión de main).
  - `WelcomeScreen.kt`: descartar cambios (se elimina en fase 3).
  - `Theme.kt`, `strings.xml`: conservar la versión más reciente.
- Verificar `./gradlew :app:assembleDebug`.

### Fase 3 — Reorganización post-merge
- Renombrar `screens/` reorganizando en `material/`, `native/`.
- Eliminar `WelcomeScreen.kt`.
- Crear `RootMenuScreen.kt`, `RootNav.kt`, `ExercisesNav.kt`.
- Crear `screens/exercises/` con `MiniAppScaffold`, `CodeBlock`, `ExercisesMenuScreen`, y las 8 mini-apps.
- Reescribir `MainActivity.kt` para alojar `RootNav()`.
- Verificar `./gradlew :app:assembleDebug` y emulador muestra las 3 cards.

### Fase 4 — Crear módulo `exercises/`
- Crear estructura Gradle independiente (no incluido en `settings.gradle.kts` raíz).
- Crear los 33 archivos `.kt` con `TODO()` y comentarios de cabecera.
- Crear los 33 `*Test.kt` con JUnit 5.
- Verificar `./gradlew :exercises:build` y `./gradlew :exercises:test`.

### Fase 5 — Documentación
- Crear `docs/08-Guia-Capacidades-Nativas.md` (basado en `GUIA_CARACTERISTICAS_NATIVAS.md`).
- Crear `docs/09-Guia-Ejercicios-Kotlin.md`.
- Crear `docs/10-Guia-Ejercicios-Android.md`.
- Crear `docs/11-Principios-de-Arquitectura.md`.
- Actualizar `docs/README.md` con nueva ruta.
- Actualizar `README.md` raíz.

### Fase 6 — Push y limpieza
- `git push origin main`.
- `git push origin :feature/material-showcase`.
- `git push origin :native-capabilities-show`.

## Trade-offs y decisiones tomadas

| Decisión | Alternativa descartada | Razón |
|----------|----------------------|-------|
| Un solo `main` | Mantener 3 branches | Más simple para alumnos clonar |
| `exercises/` JVM dentro del repo | Módulo Gradle Android del repo principal | Aísla el ciclo de Kotlin de Android; iteración rápida sin emulador |
| 33 ejercicios (32 + kata) | Set más pequeño (~15) | El usuario pidió curso progresivo de Kotlin básico a Android |
| Coroutines expandido (6) vs básico (2) | Mínimo | El usuario pidió más detalle teórico + práctico |
| Cápsulas de Arquitectura como `.kt` ejecutable | Solo docs `.md` | El usuario pidió "no solo qué, sino por qué"; el código que demuestra el principio es más memorable que prosa |
| Mini-apps con `MiniAppScaffold` reutilizable | 8 scaffolds duplicados | Una mini-app debe ser trivial de añadir/modificar |
| No agregar Room como dep real | Sí agregar | Mantiene simple el árbol de deps; el ejercicio Room es conceptual |
| Conservar versión de main de `docs/06` | Versión de native (que la borró) | La rama native perdió contenido valioso |

## Riesgos

- **Conflictos de merge**: Fase 2 tiene 5 archivos en conflicto esperado. Mitigación: backup en Fase 0 + resolver uno a uno con verificación de compilación por archivo.
- **Tamaño del módulo `exercises/`**: 33 ejercicios × 2 archivos (main + test) = 66 archivos nuevos. Es trabajoso pero cabe en una sola sesión de implementación. Se puede paralelizar con sub-agentes.
- **Cobertura de coroutines**: las pruebas de `Flow` necesitan `kotlinx-coroutines-test`. Se incluye esa dep en `exercises/build.gradle.kts`.
- **Branch protection**: si `origin/main` tiene protección, no se puede forzar push. Plan asume que no.

## Fuera de alcance

- Hilt/Koin: no se introduce DI framework. La cápsula 24 muestra DI manual.
- Migración a Compose BOM más reciente: queda con la versión actual.
- Tests de instrumentación Android para las mini-apps: fuera de alcance (las mini-apps tienen solución visible, no necesitan test).
- Publicar el repo: no se hace.