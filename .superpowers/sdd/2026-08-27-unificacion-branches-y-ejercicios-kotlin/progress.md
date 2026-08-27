# SDD ledger — plan: docs/superpowers/plans/2026-08-27-unificacion-branches-y-ejercicios-kotlin.md

## Setup
- Worktree: `.worktrees/unificacion-ejercicios/`
- Branch: `feature/unificacion-ejercicios`
- Base: `b72b559` (chore: ignorar .worktrees/) — added before pre-flight
- Updated base after pre-flight fixes: `6f51665` (fix(plan): aplicar 9 correcciones del pre-flight review)
- JDK 17 instalado en `/home/sebastian/.local/jdk/jdk-17.0.20.1+1`. Wrapper `gradlew-with-jdk` en PATH (busca `./gradlew` subiendo directorios).
- Pre-flight review completado: 3 bloqueadores + 2 importantes + 4 menores; todos corregidos en commit `6f51665`.

## Convenciones
- Implementer: agente `coder` con modelo MiniMax-M3 (default). Para tasks de transcripción mecánica (código completo en el brief) usar tier cheap; para tasks con judgment usar tier más capaz.
- Reviewer: agente `coder` con tier medio para tasks mecánicas, top tier para integraciones.
- Cada task produce 1+ commits atómicos en español, imperativo.
- Report file path: `.superpowers/sdd/.../task-N-report.md`
- Brief file: `/tmp/task-N-brief.md` (generado por `task-brief` script)
- Review package: `/tmp/review-N.md` (generado por `review-package` script)

## Estado de tasks

### Task 1: Backup + mover epubs + gitignore
- **Status**: complete (commits 6f51665..8cbabaa, review with parked findings)
- **Implementer commit**: `8cbabaa` — `chore: ignorar docs/books y *.epub en gitignore`
- **Reviewer verdict**: Spec ⚠️ Partial / Quality Issues (Important commit message desviado, Minor `.git-backup/` untracked, Minor `git fetch --all` no ejecutado)
- **Parked findings**:
  - Step 3 no-op: los 2 epubs del brief no existen en el repo ni en historial. El plan los asumía como untracked; el implementer los creó `docs/books/` vacío y agregó gitignore. **Ruling**: comportamiento correcto, la intent del plan se cumple (epubs ignorados). No requiere fix.
  - Commit message final: `chore: ignorar docs/books y *.epub en gitignore` — fue un amend mío después de que el implementer reportara. Omite `exercises/build/` y `exercises/.gradle/`. **Ruling**: aceptable; la intención principal (ignorar epubs) está comunicada. El review final whole-branch lo puede ajustar.
  - Report stale del implementer: el SHA listado (`539f7e3`) no coincide con el real (`8cbabaa`) por mi amend post-report. **Ruling**: error de proceso mío, no del implementer. Aceptable.

### Task 2: Merge feature/material-showcase
- **Status**: complete (commits 8cbabaa..ba77c02, review clean)
- **Merge commit**: `ba77c02` — `Merge: integrar Material Design showcase + tests + docs 07`
- **Reviewer verdict**: Spec ✅, Quality approved.
- **Concerns parked**:
  - Dep duplicada `androidx.navigation:navigation-compose:2.7.5` en build.gradle.kts (líneas 72, 75). El commit `b195580` intentó limpiarla, pero el merge reintrodujo el duplicado. No rompe build (Gradle deduplica). **Ruling**: deferir al final-review o a Task de cleanup.
  - Tests unitarios nuevos no ejecutados (fuera de scope del brief).
- **Notas de entorno**: Android SDK 34 instalado en `/home/sebastian/android-sdk/` (~445 MB). `local.properties` configurado y gitignored. Subagents futuros deben poder reutilizar este SDK.

### Task 3: Merge native-capabilities-show
- **Status**: complete (commits ba77c02..63ae3a9, review clean)
- **Merge commit**: `63ae3a9` — `Merge: integrar capacidades nativas + guía 08`
- **Reviewer verdict**: Spec ✅, Quality approved.
- **Conflictos resueltos**: 4 reales (build.gradle.kts, MainActivity.kt, docs/05, README.md). `--ours` para todos (preservando material-showcase). 5 conflictos previstos en el brief no ocurrieron (auto-mergeados o sin divergencia).
- **Concerns parked**:
  - Camera `1.3.0` vs `1.3.1` sugerido por brief: el código nativo usa 1.3.0; mantenida para consistencia. Minor.
  - README.md no estaba en lista del brief; resuelto con `--ours`. La rama referenciada en sección "Ramas" no existirá tras Fase 3. Minor.
  - Warning pre-existente en `CameraScreen.kt:198` (`context` no usado). Pre-existente, no introducido por merge. Minor.
- **Nota de proceso**: review package (`review-task-3.diff`) inicialmente se generó con contenido incorrecto (parecía mostrar archivos de material como deleted). Regenerado correctamente usando `cd` en lugar de `cwd`.

### Task 4: Reorganizar screens/ → material/, native/
- **Status**: complete (commits 63ae3a9..94eb75c, review clean with one important parked)
- **Commit**: `94eb75c` — `refactor: reorganizar screens/ en material/ y native/`
- **Reviewer verdict**: Spec ✅, Quality approved con Important caveat.
- **Concerns parked**:
  - **Important**: `app/src/test/java/com/example/baseproject/ui/screens/ShowcaseCategoryTest.kt` no fue refactorizado: `package` decl sigue siendo `ui.screens` y referencia `ShowcaseCategory(...)` que ahora está en `ui.screens.material`. `:app:testDebugUnitTest` fallaría. **Plan**: arreglar en Task 5 cuando MainActivity también se reescribirá.
  - Minor: `ShowcaseRoutes` object name legacy dentro de `MaterialNav.kt`. Aceptable.
  - Minor: Sed del brief produce doble prefijo `.material.material`; arreglado en runtime. Bug del brief, no del trabajo entregado.
- **Bonus**: implementer también corrigió `package` de `NativeNav.kt` para alinear con su nueva ubicación (más allá del brief).

### Task 5: RootMenuScreen + RootNav + MainActivity + fix test file
- **Status**: complete (commits 94eb75c..3b49758)
- **Commits**:
  - `1ad83de` — `feat: menú raíz con 3 secciones + RootNav`
  - `6169997` — `test: mover ShowcaseCategoryTest al package ui.screens.material` (incompleto: solo añadió el nuevo, no borró el viejo)
  - `3b49758` — `test: borrar ShowcaseCategoryTest antiguo (duplicado en package ui.screens)` (fix round 1)
- **Reviewer verdict**: 
  - Initial review: Critical — el commit `6169997` no borró el archivo viejo, dejando duplicación.
  - Re-review tras fix `3b49758`: Spec ✅, ready.
- **Concerns parked**:
  - `@OptIn(ExperimentalMaterial3Api::class)` en `MenuCard` (necesario para `Card(onClick=...)` en Material3; brief no lo preveía). Minor.
  - `NativeNav(navController: NavHostController = rememberNavController())` con default; cambio de signature necesario para que RootNav lo invoque sin args. Minor.
  - Test `StringValidatorTest > RUT con K` falla — preexistente en `6761fb0`, fuera de scope. Parked.
  - Warning `navController` unused en `ExercisesMenuScreen` stub — esperado, se resuelve en Task 6.