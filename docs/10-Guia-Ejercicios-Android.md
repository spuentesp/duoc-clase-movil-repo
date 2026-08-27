# 10 — Guía de Ejercicios Android

8 mini-apps dentro de la app (`app/`), accesibles desde el menú raíz →
"Ejercicios Android". Cada mini-app tiene un enunciado y una solución
mostrada en un `CodeBlock` con scroll.

## Mini-apps disponibles

| # | Mini-app | Concepto Compose/VM | Ruta |
|---|----------|---------------------|------|
| 1 | Contador | `remember`, `mutableStateOf`, `Button`, `Text` | `exercises/counter` |
| 2 | Lista de tareas | `LazyColumn`, `mutableStateListOf` | `exercises/list` |
| 3 | Formulario | `TextField`, validación | `exercises/form` |
| 4 | Navegación | `NavHost`, argumentos | `exercises/navigation` |
| 5 | ViewModel | `viewModel()`, sobrevive a rotación | `exercises/viewmodel` |
| 6 | Repository | Interfaz + fake | `exercises/repository` |
| 7 | Networking | `suspend` + sealed state | `exercises/network` |
| 8 | Room (preview) | Solo enunciado + código | `exercises/room` |

## Cómo usar

1. Abre la app en el emulador/dispositivo.
2. Toca la tarjeta "🧪 Ejercicios Android".
3. Selecciona una mini-app.
4. Lee el enunciado. Toca el icono de código (esquina superior derecha) para ver la solución.
5. Toca "Restablecer" para volver al enunciado.

## Ruta sugerida

Sigue el orden 1 → 8. Cada mini-app asume que conoces las anteriores.

## Modificar las mini-apps

Los archivos están en:

```
app/src/main/java/com/example/baseproject/ui/screens/exercises/
├── e01_counter/CounterExerciseScreen.kt
├── e02_list/ListExerciseScreen.kt
├── e03_form/FormExerciseScreen.kt
├── e04_navigation/NavigationExerciseScreen.kt
├── e05_viewmodel/{ViewModelExerciseScreen.kt, CounterViewModel.kt}
├── e06_repository/{RepositoryExerciseScreen.kt, CounterRepository.kt, RepositoryViewModel.kt}
├── e07_network/{NetworkExerciseScreen.kt, NetworkViewModel.kt, PokeApiFake.kt}
└── e08_room/RoomExerciseScreen.kt
```

Para agregar tu propia mini-app:

1. Crea un nuevo paquete `e09_mi_app/`.
2. Copia la estructura de `e01_counter/`.
3. Implementa tu `MiAppExerciseScreen()` usando `MiniAppScaffold`.
4. Registra la ruta en `ui/navigation/ExercisesNav.kt`.
5. Agrega una entrada a la lista en `ui/screens/exercises/ExercisesMenuScreen.kt`.

## Scaffold reutilizable

El `MiniAppScaffold` ya tiene:

- TopAppBar con título.
- Botones para alternar entre enunciado y solución.
- Botón de restablecer.

Úsalo así:

```kotlin
@Composable
fun MiAppExerciseScreen() {
    MiniAppScaffold(
        titulo = "Mi mini-app",
        enunciado = "Descripción del problema…",
        contenidoSolucion = {
            // UI de la solución + CodeBlock con el código
        },
    )
}
```