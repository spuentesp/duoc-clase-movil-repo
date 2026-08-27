# Task 4 Report: Renombrar paquetes de screens/ → material/, native/

## Status

**DONE_WITH_CONCERNS** — refactor ejecutado y build verde, pero el `startDestination`
de NativeNav quedó cambiado a `Screen.FeaturesMenu.route` (Task 5 debe restaurar
WelcomeScreen + el start destination original).

## Steps executed

1. **Step 1**: `mkdir -p material native` dentro de `ui/screens/`, luego `mv` de los 11 sub-paquetes
   (`buttons cards dialogs lists icons appbars bottomsheets navigation textfields theming
   selectioncontrols`) → `material/`, y `features/` → `native/`.
2. **Step 2**: `git mv MainScreen.kt ShowcaseCategory.kt material/`.
3. **Step 3**: `git mv FeaturesMenuScreen.kt native/`.
4. **Step 4**: tres `git mv` de archivos de navegación + tres `sed` (renombrar funciones
   `ShowcaseNavigation`/`NavGraph` → `MaterialNav`/`NativeNav`, corregir `package` en
   `Screen.kt`). Adicionalmente corregí el `package` de `NativeNav.kt` (de
   `com.example.baseproject.navigation` → `com.example.baseproject.ui.navigation`)
   — el brief solo mencionaba Screen.kt, pero sin esto el archivo queda en un paquete
   distinto de su ubicación.
5. **Step 5**: `rmdir` del antiguo `com.example.baseproject/navigation/` (quedó vacío
   tras mover `NavGraph.kt` y `Screen.kt`).
6. **Step 6**: corrección de `package` decls en archivos movidos + rewrite de imports.
   Tuve un tropiezo: el sed del brief (`s|^package com.example.baseproject.ui.screens\.|…|`)
   también matchea el sufijo `.material` ya agregado por la primera expresión, así que
   `MainScreen.kt`, `ShowcaseCategory.kt` y `FeaturesMenuScreen.kt` quedaron con
   `…screens.material.material` / `…screens.native.native`. Lo corregí con un sed
   dirigido (`s|material\.material|material|`, `s|native\.native|native|`). Después
   barrí todo el árbol por refs colgadas a `com.example.baseproject.navigation.*` y a
   `com.example.baseproject.ui.screens.<old>` y encontré un lote extra de imports
   `screens.buttons.ComponentSection` en 10 archivos de `material/*` — los reescribí a
   `screens.material.buttons.ComponentSection`.
7. **Step 7**: `git rm WelcomeScreen.kt`. Esto dejó `NativeNav.kt` con dos referencias
   huérfanas (`startDestination = Screen.Welcome.route` y el bloque
   `composable(Screen.Welcome.route) { WelcomeScreen(...) }`).
8. **Step 8**: build con `gradlew-with-jdk :app:assembleDebug` → primera corrida tuvo 3
   errores, los corregí:
   - `NativeNav.kt:17 Unresolved reference: WelcomeScreen` — quité el bloque del composable
     Welcome y cambié `startDestination` a `Screen.FeaturesMenu.route`, dejando un
     `// TODO(Task 5)` en su lugar.
   - `FeaturesMenuScreen.kt:17 Unresolved reference: Screen` — su import apuntaba a
     `com.example.baseproject.navigation.Screen`; lo reescribí a
     `com.example.baseproject.ui.navigation.Screen`.
   - `MainActivity.kt:10/22 Unresolved reference: ShowcaseNavigation` — al renombrar la
     función, MainActivity (que la llamaba directamente, sin pasar por RootNav) quedó
     con refs colgantes. Cambié import y call site a `MaterialNav()`. Task 5 debería
     mover MainActivity a llamar `RootNav()`.
   Build final: **BUILD SUCCESSFUL in 11s**. Único warning: `CameraScreen.kt:198
   Variable 'context' is never used` (pre-existente, no introducido por esta task).
9. **Step 9**: `git commit -m "refactor: reorganizar screens/ en material/ y native/"`.

## Concerns / hand-off to Task 5

- **`startDestination` temporal**: `NativeNav.kt` ahora arranca en
  `Screen.FeaturesMenu.route`. El comentario `// TODO(Task 5): reintroducir WelcomeScreen
  y restaurar Screen.Welcome como startDestination` marca dónde restaurar.
- **`MainActivity` llama `MaterialNav()` directamente** (en vez de un `RootNav()`). Task 5
  debería reemplazarlo por `RootNav()`.
- **`Screen.Welcome` quedó declarado** en `Screen.kt` pero sin destino en NativeNav. Task 5
  lo reutilizará.
- **Sed del brief tiene un bug**: la doble sustitución
  (`s|^package com.example.baseproject.ui.screens$|…material|; s|^package com.example.baseproject.ui.screens\.|…material.|`)
  aplica ambas expresiones a la línea ya transformada y produce `.material.material` en
  los archivos sueltos. Hay que invertir el orden o agregar `[^.]$` al ancla de la
  primera expresión para que sean mutuamente excluyentes en la entrada.
- **Pre-existing warning**: `CameraScreen.kt:198` tiene una variable `context` no usada.
  No la toqué (fuera de scope).
- **Tooling artifacts no commiteados**: `.git-backup/` y `.superpowers/` aparecen
  como untracked. No forman parte de esta task; los dejé fuera del commit (usé
  `git add app/` en lugar de `git add -A`).

## Files touched

### Renames (git-mv, tracked as renames in commit)
- `screens/buttons/ButtonsScreen.kt` → `screens/material/buttons/ButtonsScreen.kt`
- `screens/cards/CardsScreen.kt` → `screens/material/cards/CardsScreen.kt`
- `screens/dialogs/DialogsScreen.kt` → `screens/material/dialogs/DialogsScreen.kt`
- `screens/lists/ListsScreen.kt` → `screens/material/lists/ListsScreen.kt`
- `screens/icons/IconsScreen.kt` → `screens/material/icons/IconsScreen.kt`
- `screens/appbars/AppBarsScreen.kt` → `screens/material/appbars/AppBarsScreen.kt`
- `screens/bottomsheets/BottomSheetsScreen.kt` → `screens/material/bottomsheets/BottomSheetsScreen.kt`
- `screens/navigation/NavigationScreen.kt` → `screens/material/navigation/NavigationScreen.kt`
- `screens/selectioncontrols/SelectionControlsScreen.kt` → `screens/material/selectioncontrols/SelectionControlsScreen.kt`
- `screens/textfields/TextFieldsScreen.kt` → `screens/material/textfields/TextFieldsScreen.kt`
- `screens/theming/ThemingScreen.kt` → `screens/material/theming/ThemingScreen.kt`
- `screens/MainScreen.kt` → `screens/material/MainScreen.kt`
- `screens/ShowcaseCategory.kt` → `screens/material/ShowcaseCategory.kt`
- `screens/features/AccelerometerScreen.kt` → `screens/native/features/AccelerometerScreen.kt`
- `screens/features/BatteryScreen.kt` → `screens/native/features/BatteryScreen.kt`
- `screens/features/BiometricScreen.kt` → `screens/native/features/BiometricScreen.kt`
- `screens/features/CameraScreen.kt` → `screens/native/features/CameraScreen.kt`
- `screens/features/FlashlightScreen.kt` → `screens/native/features/FlashlightScreen.kt`
- `screens/features/LocalStorageScreen.kt` → `screens/native/features/LocalStorageScreen.kt`
- `screens/features/LocationScreen.kt` → `screens/native/features/LocationScreen.kt`
- `screens/features/NotificationsScreen.kt` → `screens/native/features/NotificationsScreen.kt`
- `screens/features/VibrationScreen.kt` → `screens/native/features/VibrationScreen.kt`
- `screens/FeaturesMenuScreen.kt` → `screens/native/FeaturesMenuScreen.kt`
- `ui/navigation/ShowcaseNavigation.kt` → `ui/navigation/MaterialNav.kt` (fun rename `ShowcaseNavigation` → `MaterialNav`)
- `navigation/NavGraph.kt` → `ui/navigation/NativeNav.kt` (fun rename `NavGraph` → `NativeNav`)
- `navigation/Screen.kt` → `ui/navigation/Screen.kt`

### Modifications (in-place, not renames)
- `MainActivity.kt` — import + call site `ShowcaseNavigation()` → `MaterialNav()`.
- `ui/navigation/NativeNav.kt` — `startDestination` cambia a `Screen.FeaturesMenu.route`
  (con TODO para Task 5); bloque `composable(Screen.Welcome.route)` eliminado.
- `screens/native/FeaturesMenuScreen.kt` — import `com.example.baseproject.navigation.Screen`
  → `com.example.baseproject.ui.navigation.Screen`.
- 10 archivos en `screens/material/*/XScreen.kt` — import `screens.buttons.ComponentSection`
  → `screens.material.buttons.ComponentSection`.
- 3 archivos sueltos (`screens/material/MainScreen.kt`, `screens/material/ShowcaseCategory.kt`,
  `screens/native/FeaturesMenuScreen.kt`) — `package` decl fixed (ver "tropiezo" arriba).

### Deletions
- `screens/WelcomeScreen.kt` (git rm).
- (rmdir) `com.example.baseproject/navigation/` (quedó vacío).

## Verification

```
$ gradlew-with-jdk :app:assembleDebug
…
> Task :app:compileDebugKotlin
w: CameraScreen.kt:198 Variable 'context' is never used   (pre-existing, no introducido)
> Task :app:assembleDebug
BUILD SUCCESSFUL in 11s
34 actionable tasks: 7 executed, 27 up-to-date
```

28 files changed, 56 insertions(+), 147 deletions(-).

## Contrato

- **STATUS**: DONE_WITH_CONCERNS
- **COMMITS**: 94eb75c
- **TEST_SUMMARY**: `gradlew :app:assembleDebug: BUILD SUCCESSFUL in 11s`
- **CONCERNS**:
  - `NativeNav.startDestination` temporalmente apunta a `Screen.FeaturesMenu`; Task 5
    debe restaurar WelcomeScreen + el start destination original (marcado con `// TODO(Task 5)`).
  - `MainActivity` ahora llama `MaterialNav()` directamente; Task 5 debería moverla a
    `RootNav()`.
  - El sed del brief produce doble prefijo en archivos sueltos (`material.material`) —
    el sed quedó corregido a mano en este run, pero hay que corregir el brief para
    futuros re-ejecuciones.
  - Pre-existing warning `CameraScreen.kt:198 Variable 'context' is never used` no se
    tocó (fuera de scope).
