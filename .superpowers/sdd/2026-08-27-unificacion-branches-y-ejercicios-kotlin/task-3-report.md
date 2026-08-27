# Task 3 Report — Merge de native-capabilities-show con resolución de conflictos

## Status
DONE_WITH_CONCERNS

## Resumen
Merge `--no-ff` de `origin/native-capabilities-show` sobre `feature/unificacion-ejercicios`, resolución de 4 conflictos (no 5+ como estimaba el brief) y compilación verificada con `:app:assembleDebug` (`BUILD SUCCESSFUL in 16s`).

## Commit creado
- `63ae3a9` — Merge: integrar capacidades nativas + guía 08

## Pasos ejecutados

### Step 1 — Inicio del merge
```bash
git merge --no-ff origin/native-capabilities-show -m "Merge: integrar capacidades nativas + guía 08"
```

### Step 2 — Conflictos identificados (4 reales vs 5+ estimados)
Conflictos materializados (distintos a los del brief):
- `app/build.gradle.kts`
- `app/src/main/java/com/example/baseproject/MainActivity.kt`
- `docs/05-Empaquetado-y-Distribucion.md` (add/add)
- `README.md`

Conflictos que el brief preveía pero se autocombinaron (no requirieron intervención):
- `app/src/main/AndroidManifest.xml` — auto-merge exitoso; resultado contiene `VIBRATE`, `CAMERA`, `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `POST_NOTIFICATIONS`. Sin `USE_BIOMETRIC`, `FLASHLIGHT`, `RECEIVE_BOOT_COMPLETED` ✓
- `app/src/main/res/values/strings.xml` — auto-merge exitoso.
- `app/src/main/java/com/example/baseproject/ui/theme/Theme.kt` — sin cambios divergentes.
- `app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt` — sin cambios divergentes (sin renombre de archivo).
- `docs/06-Personalizacion-de-la-App.md` — existe en ambas ramas; auto-merge OK.

### Step 3 — Resolución de `app/build.gradle.kts`
**Estrategia**: combinar deps de ambas ramas (manual merge en el bloque de conflicto).
- Material-showcase (HEAD) aportaba: `navigation-compose` (duplicado), `kotlin-test`, `mockk`.
- Native aportaba: `navigation-compose` (una línea), `biometric`, `camera-camera2/lifecycle/view`, `datastore-preferences`, `play-services-location`.
- Resultado: una sola entrada `navigation-compose:2.7.5` + bloque "Capacidades nativas" con todas las deps de native + bloque testing que mantiene `kotlin-test:1.9.20` y `mockk:1.13.8` de material-showcase.
- Versiones mantenidas desde native (`camera 1.3.0`, `biometric 1.2.0-alpha05`, `datastore 1.0.0`, `play-services-location 21.0.1`) para alinear con el código de los screens nativos ya escritos.

### Step 4 — `MainActivity.kt`
`git checkout --ours app/src/main/java/com/example/baseproject/MainActivity.kt`
Conservada la versión de material-showcase que usa `ShowcaseNavigation`. Task 5 Step 4 la reescribirá por completo.

### Step 5 — `docs/05-Empaquetado-y-Distribucion.md`
`git checkout --ours docs/05-Empaquetado-y-Distribucion.md`
Conservada la versión de material-showcase (más completa: incluye secciones PowerShell y Linux/Mac).

### Step 6 — `docs/06-Personalizacion-de-la-App.md`
No hubo conflicto (el archivo existe en ambas ramas y no fue tocado divergentemente). El brief mencionaba que native lo borró; en este caso el archivo persistió con auto-merge limpio. Verificado: `docs/06-Personalizacion-de-la-App.md` existe.

### Step 7 — `README.md` (conflicto no previsto en el brief)
Conflicto estilístico entre ambas versiones:
- HEAD (material-showcase): texto más extenso, terminología "Lanzador", "Lanzamiento".
- Native: añade sección "Ramas" referenciando la rama `native-features` y lista completa de features nativas.

**Resolución tomada**: `git checkout --ours README.md`.
**Justificación**: la sección "Ramas" de native apunta a una rama (`native-features`) que no existirá tras la Fase 3. Mantener el texto de material-showcase evita referenciar estructura que será reorganizada. Las features nativas siguen documentadas en `GUIA_CARACTERISTICAS_NATIVAS.md` (nuevo archivo del merge). Fase 3 puede reescribir el README.

### Step 8 — `Theme.kt`, `strings.xml`, `AndroidManifest.xml`
Auto-mergeados sin intervención. `AndroidManifest.xml` ya contiene los 5 permisos correctos (`VIBRATE`, `CAMERA`, `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`, `POST_NOTIFICATIONS`) sin los 3 prohibidos.

### Step 9 — Commit del merge
```bash
git add -A  # implícito vía los adds individuales
git commit --no-edit
# → [feature/unificacion-ejercicios 63ae3a9] Merge: integrar capacidades nativas + guía 08
```

### Step 10 — Verificación de compilación
```bash
gradlew :app:assembleDebug
# BUILD SUCCESSFUL in 16s
# 34 actionable tasks: 20 executed, 14 up-to-date
```

**Advertencias no bloqueantes**:
- `app/src/main/java/com/example/baseproject/ui/screens/features/CameraScreen.kt:198` — `Variable 'context' is never used`. Es código heredado de native, no introducido por el merge.

### Step 11 — Verificación de archivos nativos
```bash
ls app/src/main/java/com/example/baseproject/ui/screens/features/
```
Presentes los 9 screens esperados:
- AccelerometerScreen.kt
- BatteryScreen.kt
- BiometricScreen.kt
- CameraScreen.kt
- FlashlightScreen.kt
- LocalStorageScreen.kt
- LocationScreen.kt
- NotificationsScreen.kt
- VibrationScreen.kt

Además:
- `app/src/main/java/com/example/baseproject/navigation/NavGraph.kt` ✓
- `app/src/main/java/com/example/baseproject/navigation/Screen.kt` ✓
- `app/src/main/java/com/example/baseproject/ui/screens/FeaturesMenuScreen.kt` ✓
- `GUIA_CARACTERISTICAS_NATIVAS.md` (raíz) ✓

## Archivos modificados por el merge (resumen)
- Modificados: `README.md`, `app/build.gradle.kts`, `app/src/main/AndroidManifest.xml`, `app/src/main/java/com/example/baseproject/MainActivity.kt`, `app/src/main/res/values/strings.xml`, `docs/05-Empaquetado-y-Distribucion.md`
- Nuevos: `GUIA_CARACTERISTICAS_NATIVAS.md`, `app/src/main/java/com/example/baseproject/navigation/{NavGraph,Screen}.kt`, `app/src/main/java/com/example/baseproject/ui/screens/FeaturesMenuScreen.kt`, 9 archivos en `ui/screens/features/`

## Concerns (a verificar por reviewer)
1. **Conflicto README no previsto en el brief**: el brief lista 8 archivos a modificar pero omite `README.md`. Se resolvió con `--ours` por la obsolescencia de la sección "Ramas" de native. Si Fase 3 quiere contenido de la versión native (lista de features nativas en el README principal), será necesario un re-merge manual.
2. **`Theme.kt` no conflictuó**: el brief asumía conflicto. Material-showcase y native probablemente usan el mismo theme (`Theme.BaseAndroidProject`) o el mismo package `com.example.baseproject.ui.theme.BaseAndroidProjectTheme`. Verificar manualmente que los screens nativos compilen contra el theme correcto.
3. **Versiones de camera en `1.3.0` (no `1.3.1`)**: el brief sugería `1.3.1` pero la rama native usa `1.3.0`. Se mantuvo `1.3.0` para alinear con el código. Si Task 5/6 quiere actualizar, debe verificar breaking changes.
4. **`CameraScreen.kt:198` warning**: variable `context` no usada en el código heredado. Cosmético, no bloquea build.
5. **Material-showcase navigation duplicada**: el merge eliminó la línea duplicada `implementation("navigation-compose:2.7.5")` que HEAD tenía. Confirmar que no había intención de tenerla dos veces (era un bug previo introducido en `b195580` "Eliminar duplicado de Navigation Compose en build.gradle.kts" — pero el fix no llegó hasta HEAD).

## TEST_SUMMARY
`gradlew :app:assembleDebug: BUILD SUCCESSFUL in 16s`
