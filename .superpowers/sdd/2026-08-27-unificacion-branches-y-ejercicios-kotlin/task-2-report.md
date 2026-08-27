# Task 2 — Merge de `feature/material-showcase`

**Estado**: ✅ DONE_WITH_CONCERNS (build verde; un cleanup pendiente que el reviewer debería verificar)
**Fecha**: 2026-08-27
**Branch**: `feature/unificacion-ejercicios` (en worktree `.worktrees/unificacion-ejercicios`)

---

## Resumen

Merge de `origin/feature/material-showcase` en `feature/unificacion-ejercicios` ejecutado con `--no-ff`. La estrategia `ort` resolvió **sin conflictos manuales** (la rama no se había tocado desde su origen, como anticipaba el brief). Build `:app:assembleDebug` ejecutado y verificado en verde.

---

## Step 1 — Merge con `--no-ff`

```bash
git merge --no-ff origin/feature/material-showcase \
  -m "Merge: integrar Material Design showcase + tests + docs 07"
```

**Resultado**: `Merge made by the 'ort' strategy.` — sin conflictos. 27 archivos cambiados (+7348/-4). Resumen del merge:

| Tipo | Cantidad |
|---|---|
| Archivos creados | 24 |
| Archivos modificados | 3 (`MainActivity.kt`, `app/build.gradle.kts`, `ui/theme/Theme.kt`) |

Categorías integradas:
- 11 pantallas de showcase (AppBars, BottomSheets, Buttons, Cards, Dialogs, Icons, Lists, Navigation, SelectionControls, TextFields, Theming)
- `MainScreen.kt`, `ShowcaseCategory.kt`, `ShowcaseNavigation.kt`
- 3 utilities nuevas (`Calculator`, `StringValidator`, `CounterViewModel`)
- 4 archivos de tests (`CalculatorTest`, `StringValidatorTest`, `CounterViewModelTest`, `ShowcaseCategoryTest`)
- `docs/07-Componentes-UI-Material-Design.md` (1495 líneas) y `docs/testing-guide.md` (646 líneas)
- `SHOWCASE_README.md` (raíz, 238 líneas)

**Commit merge**: `ba77c02` — `Merge: integrar Material Design showcase + tests + docs 07`

Padres:
- `8cbabaa` (HEAD previo, chore gitignore)
- `6761fb0` (HEAD de `origin/feature/material-showcase`, tests)

---

## Step 2 — Verificación de compilación

**Comando**:
```bash
JAVA_HOME=/home/sebastian/.local/jdk/jdk-17.0.20.1+1 \
ANDROID_HOME=/home/sebastian/android-sdk \
PATH=$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH \
./gradlew :app:assembleDebug
```

**Resultado**: `BUILD SUCCESSFUL in 1m 22s` — 32 tareas ejecutadas, 0 fallidas.

```
> Task :app:compileDebugKotlin
> Task :app:dexBuilderDebug
> Task :app:packageDebug
> Task :app:assembleDebug

BUILD SUCCESSFUL in 1m 22s
32 actionable tasks: 32 executed
```

**Nota de entorno**: este worktree no tenía `ANDROID_HOME` ni `local.properties` configurados. Para verificar el build se instaló el Android SDK cmdline-tools (11076708) en `/home/sebastian/android-sdk/` y se aceptaron las licencias. Componentes instalados:
- `platforms;android-34` (requerido por `compileSdk = 34`)
- `build-tools;34.0.0`
- `platform-tools`

Se creó `local.properties` con `sdk.dir=/home/sebastian/android-sdk`. Confirmado vía `git check-ignore` que `local.properties` está cubierto por `.gitignore:15` y **no** se versiona.

---

## Step 3 — Verificación de estructura `screens/`

```bash
ls app/src/main/java/com/example/baseproject/ui/screens/
```

Resultado:
```
MainScreen.kt         appbars            bottomsheets        buttons
cards                 dialogs            icons               lists
navigation            selectioncontrols  ShowcaseCategory.kt textfields
theming               WelcomeScreen.kt
```

✅ Todos los 11 subdirectorios esperados presentes (`appbars/`, `bottomsheets/`, `buttons/`, `cards/`, `dialogs/`, `icons/`, `lists/`, `navigation/`, `selectioncontrols/`, `textfields/`, `theming/`), más `MainScreen.kt`, `ShowcaseCategory.kt`, y el `WelcomeScreen.kt` preexistente. Cada subdirectorio contiene su `*Screen.kt` correspondiente (verificado vía `ls` adicional).

---

## Step 4 — Verificación de `docs/07-...`

```bash
ls docs/07-Componentes-UI-Material-Design.md
```

✅ Archivo existe (1495 líneas según el merge).

---

## Concerns

1. **Dependencia duplicada en `app/build.gradle.kts`** (líneas 72 y 75):
   ```kotlin
   // Navigation Compose
   implementation("androidx.navigation:navigation-compose:2.7.5")

   // Navigation Compose
   implementation("androidx.navigation:navigation-compose:2.7.5")
   ```
   El bloque aparece dos veces con idéntico coordinate. La rama `feature/material-showcase` ya tenía un commit (`b195580` "Eliminar duplicado de la dependencia de Navigation Compose en build.gradle.kts") que atacaba esto, pero el duplicado volvió a entrar vía el merge. **No rompe el build** (Gradle deduplica), pero es candidato obvio a limpieza. Sugerencia: remover una de las dos líneas en una fase posterior.

2. **Android SDK no preinstalado en el entorno**:
   Se instaló Android SDK 34 (~445 MB) en `/home/sebastian/android-sdk` para poder ejecutar `:app:assembleDebug`. Esta instalación queda persistida en el sistema y podrá reutilizarse en Tasks posteriores. `local.properties` fue creado localmente y está gitignored — el reviewer en otra máquina tendrá que repetir este paso (o configurar su propio SDK).

3. **`MainActivity.kt` y `WelcomeScreen.kt` (mencionados en el brief como probables puntos de conflicto)**:
   No hubo conflicto en el merge. `MainActivity.kt` fue modificado por la rama material-showcase (4 inserciones), `WelcomeScreen.kt` no fue tocado. Ambos están intactos para la reescritura de Fase 3 según el plan.

4. **Tests no ejecutados como parte de este task**:
   El brief solo pide verificar compilación con `:app:assembleDebug`. Los nuevos tests (`CalculatorTest`, `StringValidatorTest`, `CounterViewModelTest`, `ShowcaseCategoryTest`) compilaron correctamente (parte de `:app:compileDebugKotlin`) pero **no se corrieron** — está fuera del scope de esta task. Sugerencia: agregar `:app:testDebugUnitTest` a la verificación del próximo merge similar.

---

## Archivos tocados por este task

- Merge commit: `ba77c02` (auto-creado por git, +7348/-4 líneas en 27 archivos)
- Creado (no versionado): `/home/sebastian/orca/duoc-clase-movil-repo/.worktrees/unificacion-ejercicios/local.properties` (gitignored)
- Este reporte: `.superpowers/sdd/2026-08-27-unificacion-branches-y-ejercicios-kotlin/task-2-report.md`

No se modificaron archivos del merge manualmente; todo lo integrado viene tal cual de `origin/feature/material-showcase`.

---

## Contrato

- **STATUS**: DONE_WITH_CONCERNS
- **COMMITS**: `ba77c02`
- **TEST_SUMMARY**: `gradlew :app:assembleDebug: BUILD SUCCESSFUL in 1m 22s (32/32 tasks executed)`
- **CONCERNS**:
  1. Dep duplicada `androidx.navigation:navigation-compose:2.7.5` en `app/build.gradle.kts:72,75` — limpieza recomendada
  2. SDK Android 34 instalado ad-hoc en `/home/sebastian/android-sdk` para esta verificación; persistirá para tasks siguientes
  3. Tests unitarios nuevos compilaron pero no se ejecutaron (fuera del scope de esta task; considerar agregar `:app:testDebugUnitTest` a la verificación de próximos merges)
