# Task 5 Report: RootMenuScreen, RootNav, MainActivity rewrite + parked test fix

## STATUS: DONE_WITH_CONCERNS

## COMMITS
- `1ad83de` feat: menú raíz con 3 secciones + RootNav
- `6169997` test: mover ShowcaseCategoryTest al package ui.screens.material

## TEST_SUMMARY
`gradlew-with-jdk :app:assembleDebug: BUILD SUCCESSFUL in 2s` ·
`gradlew-with-jdk :app:testDebugUnitTest: BUILD SUCCESSFUL in 7s (ShowcaseCategoryTest 6/6 PASS, 1 pre-existing failure en StringValidatorTest "RUT con K como verificador funciona" — no relacionada con esta task)`

## Files created
- `app/src/main/java/com/example/baseproject/ui/screens/RootMenuScreen.kt` — 3 MenuCards (Material / Nativas / Ejercicios) + título
- `app/src/main/java/com/example/baseproject/ui/navigation/RootNav.kt` — NavHost con 4 destinos (`root`, `material`, `native`, `exercises`)
- `app/src/main/java/com/example/baseproject/ui/navigation/ExercisesNav.kt` — stub con `exercises/menu`
- `app/src/main/java/com/example/baseproject/ui/screens/exercises/ExercisesMenuScreen.kt` — stub (warning de `navController` unused es esperado; se rellena en Task 6)

## Files modified
- `app/src/main/java/com/example/baseproject/MainActivity.kt` — ahora aloja `RootNav()` dentro de `BaseAndroidProjectTheme` (el alias `BaseProjectTheme` se mantiene para compatibilidad en `Theme.kt`)
- `app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt` — añadido default `navController = rememberNavController()` + import correspondiente
- `app/src/test/java/com/example/baseproject/ui/screens/material/ShowcaseCategoryTest.kt` — movido desde `ui/screens/` y package actualizado a `com.example.baseproject.ui.screens.material`

## Verificación
- `:app:assembleDebug` → BUILD SUCCESSFUL (1 warning esperado en stub)
- `:app:testDebugUnitTest` → `ShowcaseCategoryTest` 6/6 PASS en el nuevo package `com.example.baseproject.ui.screens.material`
- `StringValidatorTest`: 1 test falla (`RUT con K como verificador funciona`) — preexistente, lógica de RUT chileno en `utils/StringValidator.kt`, no tocado en esta task (último commit que lo modifica: `6761fb0`).

## CONCERNS
1. **`Card(onClick = ...)` requiere OptIn**: el código del brief en `RootMenuScreen.kt` usa la sobrecarga de `Card` con `onClick` (Material3 la marca `@ExperimentalMaterial3Api`). Añadí `@OptIn(ExperimentalMaterial3Api::class)` a `MenuCard`. Sin esto el build falla con `This material API is experimental and is likely to change or to be removed in the future`.
2. **`NativeNav` requería `navController` obligatorio**: el brief llama `NativeNav()` desde `RootNav` sin argumentos, pero `NativeNav` (escrito en Task 4) declaraba `navController: NavHostController` sin default. Añadí `= rememberNavController()` para que sea consistente con `MaterialNav` y permita el wrapping como sub-NavHost. Es la solución mínima — ahora cada sección top-level maneja su propio back stack internamente.
3. **`rmdir app/src/test/java/com/example/baseproject/ui/screens/` falló**: la instrucción del brief asumía que `screens/` quedaría vacío tras mover el test, pero el archivo ahora vive en `screens/material/` así que `screens/` contiene el subdirectorio `material/`. El `rmdir` se omitió — el archivo está en el package correcto, que es lo único que importa para que los tests compilen.
4. **Warning de `navController` unused en `ExercisesMenuScreen`**: esperado porque es stub. Se limpia en Task 6 cuando se añadan las rutas reales.
5. **Test preexistente fallando en `StringValidatorTest`**: 1 de 47 tests falla con `AssertionError at StringValidatorTest.kt:116`. No introducido por esta task; el archivo y su test fueron añadidos en el commit `6761fb0`. Fuera del scope de Task 5 — sugerencia: revisar lógica RUT/K en `utils/StringValidator.kt` en una task de bugfix separada.
