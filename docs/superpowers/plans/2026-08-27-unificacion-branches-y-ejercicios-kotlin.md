# Unificación de branches + sección de ejercicios Kotlin — Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Unificar las 3 branches (`main`, `feature/material-showcase`, `native-capabilities-show`) en un solo `main` y agregar un módulo hermano `exercises/` con 33 ejercicios Kotlin progresivos (de básico a Android), más 8 mini-apps Android dentro de la app, con menú raíz de 3 secciones.

**Architecture:** 
- Branch único `main` con tres secciones (Material, Nativas, Ejercicios Android) bajo un `RootMenuScreen`.
- Módulo hermano `exercises/` (proyecto Gradle JVM independiente, NO módulo Android) con 33 archivos `.kt` autocontenidos + sus tests JUnit 5.
- Documentación consolidada en `docs/` (00–11), libros epub movidos a `docs/books/` (gitignored).

**Tech Stack:** 
- Android: Kotlin 1.9.20, Jetpack Compose (BOM 2023.10.01), Material 3, Navigation Compose 2.7.x, ViewModel/Lifecycle 2.6.2, Activity Compose 1.8.1, AGP 8.2.0, JDK 8 target.
- Exercises: Kotlin 1.9.20 JVM, JUnit 5.10.x, kotlinx-coroutines-test 1.7.3.

## Global Constraints

- Namespace Android: `com.example.baseproject` (no cambiar).
- Package raíz ejercicios: `cl.duoc.exercises`.
- Mínimo SDK Android: 24. Target SDK: 34. Compile SDK: 34.
- Kotlin JVM target para `app/`: `1.8`.
- Todo `TODO()` en ejercicios debe quedar como `TODO()` literal (firma implementada vacía) — el alumno es quien lo completa.
- Cada cápsula de Arquitectura combina: comentario `// POR QUÉ:` + código `// DEMO:` ejecutable.
- Commits atómicos por task. Mensajes de commit en español, en imperativo.
- No se commitean los archivos `.epub` ni la carpeta `docs/books/`.

---

## Fase 0 — Preparación

### Task 1: Backup, mover epubs, actualizar `.gitignore`

**Files:**
- Modify: `.gitignore`
- Move: `docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake.epub` → `docs/books/`
- Move: `docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake (1).epub` → `docs/books/`

- [ ] **Step 1: Crear backup del repo como bundle**

```bash
mkdir -p .git-backup
git bundle create .git-backup/backup-pre-merge.bundle --all
```

Verifica que el archivo existe:
```bash
ls -la .git-backup/
```
Esperado: `backup-pre-merge.bundle` presente.

- [ ] **Step 2: Asegurar main actualizado y limpio**

```bash
git checkout main
git fetch --all
git status
```
Esperado: branch `main`, working tree limpio salvo los 2 archivos epub sin trackear.

- [ ] **Step 3: Mover epubs a docs/books/**

```bash
mkdir -p docs/books
mv 'docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake.epub' docs/books/
mv 'docs/Programming Android with Kotlin _ achieving structured -- Pierre-Olivier Laurence, Amanda Hinchman-Dominguez, G_ Blake (1).epub' docs/books/
ls docs/books/
```
Esperado: 2 archivos `.epub` listados.

- [ ] **Step 4: Actualizar `.gitignore` para ignorar epubs**

Edita `.gitignore` y agrega al final:
```
# Libros de referencia (no se versionan; cada alumno los trae localmente)
docs/books/
*.epub

# Builds del módulo exercises
exercises/build/
exercises/.gradle/
```

- [ ] **Step 5: Verificar que git ya no lista los epubs**

```bash
git status
```
Esperado: working tree limpio (epubs están ahora ignorados, no aparecen como untracked). Si aparecen listados, revisa que el patrón `docs/books/` esté al inicio de la línea sin espacios.

- [ ] **Step 6: Commit**

```bash
git add .gitignore
git commit -m "chore: mover epubs a docs/books y agregarlos a gitignore"
```

---

## Fase 1 — Merge `feature/material-showcase`

### Task 2: Merge de feature/material-showcase

**Files:**
- Modify: múltiples (resultado del merge)
- Source branch: `origin/feature/material-showcase`

- [ ] **Step 1: Iniciar merge con --no-ff**

```bash
git merge --no-ff origin/feature/material-showcase -m "Merge: integrar Material Design showcase + tests + docs 07"
```

Si hay conflictos, anota cuáles y resuélvelos uno por uno con `git status` y editando los archivos. Archivos que probablemente entren en conflicto (pero la branch no se ha tocado desde, así que probablemente no):
- `app/build.gradle.kts`: combinar deps.
- `MainActivity.kt`, `WelcomeScreen.kt`: descartar lado de material (los reescribiremos en Fase 3).

- [ ] **Step 2: Verificar compilación de la app**

```bash
./gradlew :app:assembleDebug
```
Esperado: `BUILD SUCCESSFUL`. Si falla, leer el error y corregir. Los conflictos más comunes serían duplicados de dependencias o NavHost.

- [ ] **Step 3: Verificar que el proyecto sigue funcionando**

```bash
ls app/src/main/java/com/example/baseproject/ui/screens/
```
Esperado: existen `buttons/`, `cards/`, `dialogs/`, `lists/`, `icons/`, `appbars/`, `bottomsheets/`, `navigation/`, `textfields/`, `theming/`, `selectioncontrols/`, además de `MainScreen.kt`, `ShowcaseCategory.kt`.

- [ ] **Step 4: Verificar docs 07 presente**

```bash
ls docs/07-Componentes-UI-Material-Design.md
```
Esperado: archivo existe.

---

## Fase 2 — Merge `native-capabilities-show`

### Task 3: Merge de native-capabilities-show con resolución de conflictos

**Files:**
- Modify: `app/build.gradle.kts`, `app/src/main/AndroidManifest.xml`, `MainActivity.kt`, `WelcomeScreen.kt`, `docs/05-Empaquetado-y-Distribucion.md`, `docs/06-Personalizacion-de-la-App.md`, `Theme.kt`, `strings.xml`
- Source branch: `origin/native-capabilities-show`

- [ ] **Step 1: Iniciar merge con --no-ff**

```bash
git merge --no-ff origin/native-capabilities-show -m "Merge: integrar capacidades nativas + guía 08"
```

Esperado: 5+ conflictos. Listar con `git status`.

- [ ] **Step 2: Resolver conflicto en `app/build.gradle.kts`**

Edita `app/build.gradle.kts`. La rama native agrega deps para biometría, cámara, etc. Mantén TODAS las deps de ambas branches. Ejemplo de sección `dependencies` resultante:

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // Capacidades nativas
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation("androidx.camera:camera-core:1.3.1")
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

Después:
```bash
git add app/build.gradle.kts
```

- [ ] **Step 3: Resolver conflicto en `MainActivity.kt`**

Para `MainActivity.kt`, descarta AMBOS lados. En Fase 3 lo reescribiremos. Por ahora, conserva la versión de `feature/material-showcase` (la que llama al navhost del showcase):

```bash
git checkout --theirs app/src/main/java/com/example/baseproject/MainActivity.kt
```

- [ ] **Step 4: Resolver conflicto en `WelcomeScreen.kt`**

Mantén la versión de material-showcase (se eliminará en Fase 3):

```bash
git checkout --ours app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt
```

- [ ] **Step 5: Resolver conflicto en `docs/05-Empaquetado-y-Distribucion.md`**

Compara:
```bash
git diff
```
Conserva la versión más completa (generalmente la de `main` original). Si la de native es más corta, descarta el cambio de native para este archivo:

```bash
git checkout --ours docs/05-Empaquetado-y-Distribucion.md
```

- [ ] **Step 6: Restaurar `docs/06-Personalizacion-de-la-App.md`**

La rama native borró este archivo (regresión). Conserva la versión de main:

```bash
git checkout --ours docs/06-Personalizacion-de-la-App.md
```

- [ ] **Step 7: Resolver conflictos restantes (Theme.kt, strings.xml, AndroidManifest.xml)**

- `Theme.kt`: combina cambios (probable que solo haya sido tocado por native, conservar theirs).
- `strings.xml`: combina strings nuevos (los permisos).
- `AndroidManifest.xml`: combina permisos nuevos de native + permisos INTERNET si los hubiera. Resultado esperado debe incluir: CAMERA, USE_BIOMETRIC, ACCESS_FINE_LOCATION, VIBRATE, FLASHLIGHT, RECEIVE_BOOT_COMPLETED.

```bash
# Ejemplo
git checkout --theirs app/src/main/java/com/example/baseproject/ui/theme/Theme.kt
git add app/src/main/java/com/example/baseproject/ui/theme/Theme.kt
git add app/src/main/AndroidManifest.xml
git add app/src/main/res/values/strings.xml
```

- [ ] **Step 8: Marcar merge completo**

```bash
git status  # debería decir "all conflicts fixed"
git add -A
git commit --no-edit
```

- [ ] **Step 9: Verificar compilación**

```bash
./gradlew :app:assembleDebug
```
Esperado: `BUILD SUCCESSFUL`. Si falla, revisar Manifest (faltan permisos probablemente).

- [ ] **Step 10: Verificar archivos nativos presentes**

```bash
ls app/src/main/java/com/example/baseproject/ui/screens/features/
```
Esperado: `AccelerometerScreen.kt`, `BatteryScreen.kt`, `BiometricScreen.kt`, `CameraScreen.kt`, `FlashlightScreen.kt`, `LocalStorageScreen.kt`, `LocationScreen.kt`, `NotificationsScreen.kt`, `VibrationScreen.kt`.

---

## Fase 3 — Reorganización post-merge

### Task 4: Renombrar paquetes de screens/ → material/, native/

**Files:**
- Move: `app/src/main/java/com/example/baseproject/ui/screens/buttons/` → `app/src/main/java/com/example/baseproject/ui/screens/material/buttons/`
- Move: `app/src/main/java/com/example/baseproject/ui/screens/cards/` → `.../material/cards/`
- (análogamente para los otros paquetes material)
- Move: `app/src/main/java/com/example/baseproject/ui/screens/features/` → `.../native/features/`
- Move: `app/src/main/java/com/example/baseproject/ui/navigation/ShowcaseNavigation.kt` → `app/src/main/java/com/example/baseproject/ui/navigation/MaterialNav.kt`
- Move: `app/src/main/java/com/example/baseproject/navigation/NavGraph.kt` → `app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt`
- Delete: `app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt`

- [ ] **Step 1: Crear directorios material/ y native/**

```bash
cd app/src/main/java/com/example/baseproject/ui/screens
mkdir -p material native
mv buttons cards dialogs lists icons appbars bottomsheets navigation textfields theming selectioncontrols material/
mv features native/
ls material/
ls native/features/
```

Esperado: en `material/` los 11 paquetes; en `native/features/` los 9 archivos de pantallas nativas.

- [ ] **Step 2: Mover MainScreen y ShowcaseCategory a material/**

```bash
mv MainScreen.kt ShowcaseCategory.kt material/
ls material/
```

Esperado: `MainScreen.kt`, `ShowcaseCategory.kt`, y los 11 sub-paquetes.

- [ ] **Step 3: Mover FeaturesMenuScreen a native/**

```bash
mv FeaturesMenuScreen.kt native/
```

- [ ] **Step 4: Renombrar archivos de navegación**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo
git mv app/src/main/java/com/example/baseproject/ui/navigation/ShowcaseNavigation.kt app/src/main/java/com/example/baseproject/ui/navigation/MaterialNav.kt
git mv app/src/main/java/com/example/baseproject/navigation/NavGraph.kt app/src/main/java/com/example/baseproject/ui/navigation/NativeNav.kt
git mv app/src/main/java/com/example/baseproject/navigation/Screen.kt app/src/main/java/com/example/baseproject/ui/navigation/Screen.kt
```

- [ ] **Step 5: Eliminar paquete `navigation/` huérfano si quedó**

```bash
ls app/src/main/java/com/example/baseproject/
```
Si `navigation/` quedó vacío:
```bash
rmdir app/src/main/java/com/example/baseproject/navigation/
```

- [ ] **Step 6: Actualizar imports en archivos renombrados**

Los archivos en `material/` y `native/` ahora están en paquetes distintos. Necesitamos actualizar los `package` y los imports que los referencian.

Para archivos en `material/`:
```bash
find app/src/main/java/com/example/baseproject/ui/screens/material -name '*.kt' -exec sed -i 's|^package com.example.baseproject.ui.screens$|package com.example.baseproject.ui.screens.material|; s|^package com.example.baseproject.ui.screens\.|package com.example.baseproject.ui.screens.material.|' {} +
```

Para archivos en `native/`:
```bash
find app/src/main/java/com/example/baseproject/ui/screens/native -name '*.kt' -exec sed -i 's|^package com.example.baseproject.ui.screens$|package com.example.baseproject.ui.screens.native|; s|^package com.example.baseproject.ui.screens\.|package com.example.baseproject.ui.screens.native.|' {} +
```

Actualizar `MaterialNav.kt` y `NativeNav.kt` para que apunten a los nuevos paquetes:
```bash
grep -rln 'com.example.baseproject.ui.screens.MainScreen\|com.example.baseproject.ui.screens.ShowcaseCategory\|com.example.baseproject.ui.screens.FeaturesMenuScreen' app/src/
# Editar manualmente cada match para que apunten a .material. o .native.
```

- [ ] **Step 7: Eliminar WelcomeScreen**

```bash
git rm app/src/main/java/com/example/baseproject/ui/screens/WelcomeScreen.kt
```

- [ ] **Step 8: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -30
```

Esperado: `BUILD SUCCESSFUL`. Si hay errores de imports no resueltos, corregir manualmente.

- [ ] **Step 9: Commit**

```bash
git add -A
git commit -m "refactor: reorganizar screens/ en material/ y native/"
```

---

### Task 5: Crear RootMenuScreen, RootNav, actualizar MainActivity

**Files:**
- Create: `app/src/main/java/com/example/baseproject/ui/screens/RootMenuScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/navigation/RootNav.kt`
- Modify: `app/src/main/java/com/example/baseproject/MainActivity.kt`

- [ ] **Step 1: Crear `RootMenuScreen.kt`**

Escribir `app/src/main/java/com/example/baseproject/ui/screens/RootMenuScreen.kt`:

```kotlin
package com.example.baseproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Pantalla raíz con tres tarjetas que dan acceso a las secciones de la app:
 *  - Componentes Material Design (showcase)
 *  - Capacidades Nativas (biometría, cámara, etc.)
 *  - Ejercicios Android (mini-apps para practicar)
 */
@Composable
fun RootMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Duoc Clase Móvil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Recursos de estudio para desarrollo Android con Kotlin",
            style = MaterialTheme.typography.bodyMedium,
        )

        MenuCard(
            emoji = "🎨",
            titulo = "Componentes Material",
            descripcion = "Botones, cards, listas, dialogs, theming, navigation, etc. Catálogo visual de los componentes Material 3.",
            onClick = { navController.navigate("material") },
        )
        MenuCard(
            emoji = "📱",
            titulo = "Capacidades Nativas",
            descripcion = "Biometría, cámara, GPS, sensores, notificaciones, linterna, almacenamiento local, vibración.",
            onClick = { navController.navigate("native") },
        )
        MenuCard(
            emoji = "🧪",
            titulo = "Ejercicios Android",
            descripcion = "8 mini-apps para practicar: contador, listas, formularios, ViewModel, Repository, navegación, networking.",
            onClick = { navController.navigate("exercises") },
        )
    }
}

@Composable
private fun MenuCard(
    emoji: String,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$emoji  $titulo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
```

- [ ] **Step 2: Crear `RootNav.kt`**

Escribir `app/src/main/java/com/example/baseproject/ui/navigation/RootNav.kt`:

```kotlin
package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.RootMenuScreen

/**
 * NavHost raíz. Tres destinos top-level:
 *  - "root"     → menú principal
 *  - "material" → MaterialNav (showcase de componentes)
 *  - "native"   → NativeNav (capacidades nativas)
 *  - "exercises"→ ExercisesNav (mini-apps para practicar)
 */
@Composable
fun RootNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "root") {
        composable("root") { RootMenuScreen(navController) }
        composable("material") { MaterialNav() }
        composable("native") { NativeNav() }
        composable("exercises") { ExercisesNav() }
    }
}
```

- [ ] **Step 3: Crear `ExercisesNav.kt` (stub; las rutas se completan en Task 8)**

Escribir `app/src/main/java/com/example/baseproject/ui/navigation/ExercisesNav.kt`:

```kotlin
package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.exercises.ExercisesMenuScreen

/**
 * NavGraph de la sección Ejercicios Android.
 * Las rutas concretas de cada mini-app se completan en Task 8.
 */
@Composable
fun ExercisesNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "exercises/menu") {
        composable("exercises/menu") { ExercisesMenuScreen(navController) }
    }
}
```

- [ ] **Step 4: Reescribir `MainActivity.kt`**

Reemplaza el contenido de `app/src/main/java/com/example/baseproject/MainActivity.kt`:

```kotlin
package com.example.baseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.baseproject.ui.navigation.RootNav
import com.example.baseproject.ui.theme.BaseAndroidProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseAndroidProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RootNav()
                }
            }
        }
    }
}
```

- [ ] **Step 5: Crear stub `ExercisesMenuScreen.kt` (se completa en Task 6)**

Escribir `app/src/main/java/com/example/baseproject/ui/screens/exercises/ExercisesMenuScreen.kt`:

```kotlin
package com.example.baseproject.ui.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/** Placeholder. Se reemplaza en Task 6 con la lista de mini-apps. */
@Composable
fun ExercisesMenuScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ejercicios Android", style = MaterialTheme.typography.headlineSmall)
        Text("Lista de mini-apps próximamente…", style = MaterialTheme.typography.bodyMedium)
    }
}
```

- [ ] **Step 6: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -20
```

Esperado: `BUILD SUCCESSFUL`. Si falla, probablemente hay un import faltante.

- [ ] **Step 7: Commit**

```bash
git add -A
git commit -m "feat: menú raíz con 3 secciones + RootNav"
```

---

### Task 6: Crear MiniAppScaffold, CodeBlock, ExercisesMenuScreen completo

**Files:**
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/MiniAppScaffold.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/CodeBlock.kt`
- Modify: `app/src/main/java/com/example/baseproject/ui/screens/exercises/ExercisesMenuScreen.kt`

- [ ] **Step 1: Crear `MiniAppScaffold.kt`**

Escribir `app/src/main/java/com/example/baseproject/ui/screens/exercises/MiniAppScaffold.kt`:

```kotlin
package com.example.baseproject.ui.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ShowState { ENUNCIADO, SOLUCION }

/**
 * Scaffold reutilizable para las mini-apps de Ejercicios Android.
 * Botones en la top-bar para alternar entre enunciado y solución.
 *
 * @param titulo Título de la mini-app (aparece en la top-bar)
 * @param enunciado Texto con el problema a resolver
 * @param contenidoSolucion Composable que muestra la solución (típicamente un CodeBlock)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniAppScaffold(
    titulo: String,
    enunciado: String,
    contenidoSolucion: @Composable () -> Unit,
) {
    var mostrando by remember { mutableStateOf(ShowState.ENUNCIADO) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                actions = {
                    IconButton(onClick = { mostrando = ShowState.ENUNCIADO }) {
                        Icon(Icons.Default.Description, contentDescription = "Ver enunciado")
                    }
                    IconButton(onClick = { mostrando = ShowState.SOLUCION }) {
                        Icon(Icons.Default.Code, contentDescription = "Ver solución")
                    }
                    IconButton(onClick = { mostrando = ShowState.ENUNCIADO }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Restablecer")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            when (mostrando) {
                ShowState.ENUNCIADO -> {
                    Text(
                        text = enunciado,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                ShowState.SOLUCION -> contenidoSolucion()
            }
        }
    }
}
```

- [ ] **Step 2: Crear `CodeBlock.kt`**

Escribir `app/src/main/java/com/example/baseproject/ui/screens/exercises/CodeBlock.kt`:

```kotlin
package com.example.baseproject.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

/**
 * Pinta un bloque de código con scroll vertical y fondo oscuro.
 * Usado para mostrar la solución de cada mini-app.
 */
@Composable
fun CodeBlock(codigo: String, modifier: Modifier = Modifier) {
    Text(
        text = codigo,
        style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(12.dp),
    )
}
```

- [ ] **Step 3: Reemplazar `ExercisesMenuScreen.kt` con la lista real**

Reemplaza `app/src/main/java/com/example/baseproject/ui/screens/exercises/ExercisesMenuScreen.kt`:

```kotlin
package com.example.baseproject.ui.screens.exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

private data class MiniAppItem(
    val id: String,
    val emoji: String,
    val titulo: String,
    val descripcion: String,
)

private val MINI_APPS = listOf(
    MiniAppItem("counter", "🔢", "1. Contador", "remember, mutableStateOf, Button, Text."),
    MiniAppItem("list", "📋", "2. Lista de tareas", "LazyColumn, mutableStateListOf, agregar/eliminar."),
    MiniAppItem("form", "📝", "3. Formulario", "TextField, validación, mostrar errores."),
    MiniAppItem("navigation", "🧭", "4. Navegación", "Multi-pantalla, argumentos en NavController."),
    MiniAppItem("viewmodel", "🧠", "5. ViewModel", "viewModel(), viewModelScope, sobrevive a rotación."),
    MiniAppItem("repository", "🗂️", "6. Repository", "Interfaz de fuente de datos + fake implementation."),
    MiniAppItem("network", "🌐", "7. Networking", "suspend fun + sealed Result + pokeapi fake."),
    MiniAppItem("room", "💾", "8. Room (preview)", "Solo enunciado + código: no se ejecuta."),
)

@Composable
fun ExercisesMenuScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Ejercicios Android",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Toca una mini-app para ver su enunciado y luego su solución.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(MINI_APPS, key = { it.id }) { item ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    onClick = { navController.navigate("exercises/${item.id}") },
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "${item.emoji}  ${item.titulo}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = item.descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                }
            }
        }
    }
}
```

- [ ] **Step 4: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -20
```
Esperado: `BUILD SUCCESSFUL`.

- [ ] **Step 5: Commit**

```bash
git add -A
git commit -m "feat: MiniAppScaffold + CodeBlock + lista de mini-apps"
```

---

### Task 7: Crear las 4 mini-apps básicas (counter, list, form, navigation)

**Files:**
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e01_counter/CounterExerciseScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e02_list/ListExerciseScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e03_form/FormExerciseScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e04_navigation/NavigationExerciseScreen.kt`

- [ ] **Step 1: Crear `e01_counter/CounterExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e01_counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Crea un contador con Compose que tenga dos botones: '+' incrementa
    y '-' decrementa. Muestra el valor actual en un Text.

    Conceptos: remember, mutableStateOf, Button, Text.
"""

private val SOLUCION = """
    @Composable
    fun CounterApp() {
        var count by remember { mutableStateOf(0) }
        Column {
            Text("Contador: \$count")
            Row {
                Button(onClick = { count-- }) { Text("-") }
                Button(onClick = { count++ }) { Text("+") }
            }
        }
    }
""".trimIndent()

@Composable
fun CounterExerciseScreen() {
    var count by remember { mutableStateOf(0) }
    MiniAppScaffold(
        titulo = "1. Contador",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Contador: \$count", fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { count-- }) { Text("-") }
                        Button(onClick = { count++ }) { Text("+") }
                    }
                }
                Text("Código de la solución:", fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 8.dp))
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 2: Crear `e02_list/ListExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e02_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Crea una lista de tareas con Compose. Un TextField permite agregar
    una tarea nueva con un botón; la lista se muestra en un LazyColumn;
    cada item tiene un botón para eliminarla.

    Conceptos: mutableStateListOf, LazyColumn, items(), TextField, Button.
"""

private val SOLUCION = """
    @Composable
    fun TodoList() {
        var tareas = remember { mutableStateListOf<String>() }
        var nueva by remember { mutableStateOf("") }
        Column {
            Row {
                TextField(value = nueva, onValueChange = { nueva = it })
                Button(onClick = {
                    if (nueva.isNotBlank()) { tareas.add(nueva); nueva = "" }
                }) { Text("Agregar") }
            }
            LazyColumn {
                items(tareas) { tarea ->
                    Row {
                        Text(tarea, Modifier.weight(1f))
                        Button(onClick = { tareas.remove(tarea) }) { Text("X") }
                    }
                }
            }
        }
    }
""".trimIndent()

@Composable
fun ListExerciseScreen() {
    var tareas = remember { mutableStateListOf<String>() }
    var nueva by remember { mutableStateOf("") }
    MiniAppScaffold(
        titulo = "2. Lista de tareas",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = nueva,
                        onValueChange = { nueva = it },
                        label = { Text("Nueva tarea") },
                        modifier = Modifier.weight(1f),
                    )
                    Button(
                        onClick = {
                            if (nueva.isNotBlank()) { tareas.add(nueva); nueva = "" }
                        },
                        modifier = Modifier.padding(start = 8.dp),
                    ) { Text("+") }
                }
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(tareas) { tarea ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(tarea, Modifier.weight(1f))
                            TextButton(onClick = { tareas.remove(tarea) }) { Text("Eliminar") }
                        }
                    }
                }
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 3: Crear `e03_form/FormExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e03_form

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Crea un formulario con dos campos: email y password. Muestra un
    mensaje de error bajo cada campo si:
      - email no contiene '@'
      - password tiene menos de 6 caracteres
    Habilita un botón 'Enviar' solo cuando ambos campos son válidos.

    Conceptos: TextField, validación, isError, enabled.
"""

private val SOLUCION = """
    @Composable
    fun LoginForm() {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        val emailError = email.isNotEmpty() && !email.contains('@')
        val passError = pass.isNotEmpty() && pass.length < 6
        val puedeEnviar = email.contains('@') && pass.length >= 6
        Column {
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") }, isError = emailError,
                supportingText = { if (emailError) Text("Email inválido") },
            )
            OutlinedTextField(
                value = pass, onValueChange = { pass = it },
                label = { Text("Password") }, isError = passError,
                supportingText = { if (passError) Text("Mínimo 6 caracteres") },
            )
            Button(onClick = { /* enviar */ }, enabled = puedeEnviar) {
                Text("Enviar")
            }
        }
    }
""".trimIndent()

@Composable
fun FormExerciseScreen() {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val emailError = email.isNotEmpty() && !email.contains('@')
    val passError = pass.isNotEmpty() && pass.length < 6
    val puedeEnviar = email.contains('@') && pass.length >= 6

    MiniAppScaffold(
        titulo = "3. Formulario",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") }, isError = emailError,
                    supportingText = { if (emailError) Text("Email inválido") },
                )
                OutlinedTextField(
                    value = pass, onValueChange = { pass = it },
                    label = { Text("Password") }, isError = passError,
                    supportingText = { if (passError) Text("Mínimo 6 caracteres") },
                )
                Button(onClick = {}, enabled = puedeEnviar) { Text("Enviar") }
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 4: Crear `e04_navigation/NavigationExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e04_navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Crea una navegación entre dos pantallas:
      - ListaScreen: muestra una LazyColumn de 5 ítems ficticios.
      - DetalleScreen: recibe el id del ítem como argumento y lo muestra.

    Conceptos: NavHost, composable(), navArgument, NavType.IntType.
"""

private val SOLUCION = """
    @Composable
    fun EjemploNavegacion() {
        val nav = rememberNavController()
        NavHost(nav, startDestination = "lista") {
            composable("lista") {
                LazyColumn {
                    items(5) { id ->
                        Button(onClick = { nav.navigate("detalle/\$id") }) {
                            Text("Item \$id")
                        }
                    }
                }
            }
            composable(
                "detalle/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("id") ?: 0
                Text("Detalle del item \$id")
            }
        }
    }
""".trimIndent()

@Composable
fun NavigationExerciseScreen(parentNav: NavController) {
    MiniAppScaffold(
        titulo = "4. Navegación",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demostración interna:", fontWeight = FontWeight.SemiBold)
                DemoNavInterno()
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}

@Composable
private fun DemoNavInterno() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "lista") {
        composable("lista") {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(5) { id ->
                    Button(onClick = { nav.navigate("detalle/$id") }) { Text("Item $id") }
                }
            }
        }
        composable(
            "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            Column {
                Text("Detalle del item $id", fontWeight = FontWeight.Bold)
                Button(onClick = { nav.popBackStack() }) { Text("Volver") }
            }
        }
    }
}
```

- [ ] **Step 5: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -20
```
Esperado: `BUILD SUCCESSFUL`.

- [ ] **Step 6: Commit**

```bash
git add -A
git commit -m "feat: mini-apps 1-4 (counter, list, form, navigation)"
```

---

### Task 8: Crear las 4 mini-apps avanzadas (viewmodel, repository, network, room)

**Files:**
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e05_viewmodel/CounterViewModel.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e05_viewmodel/ViewModelExerciseScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e06_repository/CounterRepository.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e06_repository/RepositoryViewModel.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e06_repository/RepositoryExerciseScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e07_network/PokeApiFake.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e07_network/NetworkViewModel.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e07_network/NetworkExerciseScreen.kt`
- Create: `app/src/main/java/com/example/baseproject/ui/screens/exercises/e08_room/RoomExerciseScreen.kt`

- [ ] **Step 1: Crear `e05_viewmodel/CounterViewModel.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e05_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel de la mini-app 5. Sobrevive a cambios de configuración
    (rotación) gracias a su scope atado al ciclo de vida.
 */
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        viewModelScope.launch { _count.value += 1 }
    }

    fun decrement() {
        viewModelScope.launch { _count.value -= 1 }
    }
}
```

- [ ] **Step 2: Crear `e05_viewmodel/ViewModelExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e05_viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Migra el contador del ejercicio 1 a un ViewModel. La pantalla debe
    sobrevivir a la rotación: gira el dispositivo y el contador debe
    mantener su valor.

    Conceptos: ViewModel, viewModelScope, StateFlow, viewModel(),
    collectAsState().
"""

private val SOLUCION = """
    class CounterViewModel : ViewModel() {
        private val _count = MutableStateFlow(0)
        val count: StateFlow<Int> = _count.asStateFlow()
        fun increment() { viewModelScope.launch { _count.value++ } }
        fun decrement() { viewModelScope.launch { _count.value-- } }
    }

    @Composable
    fun CounterScreen(vm: CounterViewModel = viewModel()) {
        val count by vm.count.collectAsState()
        Button(onClick = vm::increment) { Text("\$count") }
    }
""".trimIndent()

@Composable
fun ViewModelExerciseScreen() {
    val vm: CounterViewModel = viewModel()
    val count by vm.count.collectAsState()

    MiniAppScaffold(
        titulo = "5. ViewModel",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                Text("Contador: \$count", fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = vm::decrement) { Text("-") }
                    Button(onClick = vm::increment) { Text("+") }
                }
                Text("Gira el dispositivo para verificar persistencia.",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 3: Crear `e06_repository/CounterRepository.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e06_repository

/**
 * Interfaz de fuente de datos. Permite cambiar la implementación
    (fake en memoria, real con red, test) sin tocar la UI ni el ViewModel.
 */
interface CounterRepository {
    suspend fun getValue(): Int
    suspend fun saveValue(value: Int)
}

class InMemoryCounterRepository(private val initial: Int = 0) : CounterRepository {
    private var stored: Int = initial
    override suspend fun getValue(): Int = stored
    override suspend fun saveValue(value: Int) { stored = value }
}
```

- [ ] **Step 4: Crear `e06_repository/RepositoryViewModel.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e06_repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepositoryViewModel(
    private val repository: CounterRepository = InMemoryCounterRepository(),
) : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    init {
        viewModelScope.launch { _count.value = repository.getValue() }
    }

    fun increment() {
        viewModelScope.launch {
            val next = _count.value + 1
            repository.saveValue(next)
            _count.value = next
        }
    }
}
```

- [ ] **Step 5: Crear `e06_repository/RepositoryExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e06_repository

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Separa la fuente de datos en un Repository. La pantalla no sabe si
    los datos vienen de memoria, red o un fake. Esto permite testear la
    UI con un fake repository y cambiar la fuente en producción.

    Conceptos: Repository Pattern, DI por constructor, interfaz.
"""

private val SOLUCION = """
    interface CounterRepository {
        suspend fun getValue(): Int
        suspend fun saveValue(value: Int)
    }

    class InMemoryCounterRepository : CounterRepository {
        private var stored = 0
        override suspend fun getValue() = stored
        override suspend fun saveValue(value: Int) { stored = value }
    }

    class RepositoryViewModel(private val repo: CounterRepository) : ViewModel() {
        // …
    }
""".trimIndent()

@Composable
fun RepositoryExerciseScreen() {
    val vm: RepositoryViewModel = viewModel()
    val count by vm.count.collectAsState()

    MiniAppScaffold(
        titulo = "6. Repository",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                Text("Contador: \$count", fontWeight = FontWeight.Bold)
                Button(onClick = vm::increment) { Text("+") }
                Text("El valor se persiste en el InMemoryRepository.",
                    style = MaterialTheme.typography.bodySmall)
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 6: Crear `e07_network/PokeApiFake.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e07_network

import kotlinx.coroutines.delay

/**
 * Fake que simula una llamada de red con delay.
    Reemplaza Retrofit en este ejemplo para no agregar dependencias.
 */
data class Pokemon(val id: Int, val nombre: String)

class PokeApiFake {
    suspend fun fetchFirst(count: Int = 20): List<Pokemon> {
        delay(1500) // simula latencia de red
        return (1..count).map { Pokemon(it, "pokemon-$it") }
    }
}
```

- [ ] **Step 7: Crear `e07_network/NetworkViewModel.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e07_network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class NetworkUiState {
    data object Idle : NetworkUiState()
    data object Loading : NetworkUiState()
    data class Success(val items: List<Pokemon>) : NetworkUiState()
    data class Error(val message: String) : NetworkUiState()
}

class NetworkViewModel(
    private val api: PokeApiFake = PokeApiFake(),
) : ViewModel() {
    private val _state = MutableStateFlow<NetworkUiState>(NetworkUiState.Idle)
    val state: StateFlow<NetworkUiState> = _state.asStateFlow()

    fun load() {
        _state.value = NetworkUiState.Loading
        viewModelScope.launch {
            try {
                _state.value = NetworkUiState.Success(api.fetchFirst())
            } catch (t: Throwable) {
                _state.value = NetworkUiState.Error(t.message ?: "Error desconocido")
            }
        }
    }
}
```

- [ ] **Step 8: Crear `e07_network/NetworkExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e07_network

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Modela el estado de UI como sealed class para representar las
    cuatro fases de una llamada de red: Idle, Loading, Success, Error.
    El compilador te obliga a manejar todos los casos en `when`.

    Conceptos: sealed class para UI state, Retrofit (sustituir fake),
    viewModelScope, manejo de errores.
"""

private val SOLUCION = """
    sealed class NetworkUiState {
        data object Idle : NetworkUiState()
        data object Loading : NetworkUiState()
        data class Success(val items: List<Pokemon>) : NetworkUiState()
        data class Error(val message: String) : NetworkUiState()
    }

    class NetworkViewModel(private val api: PokeApiFake) : ViewModel() {
        private val _state = MutableStateFlow<NetworkUiState>(NetworkUiState.Idle)
        val state: StateFlow<NetworkUiState> = _state.asStateFlow()
        fun load() { /* … */ }
    }
""".trimIndent()

@Composable
fun NetworkExerciseScreen() {
    val vm: NetworkViewModel = viewModel()
    val state by vm.state.collectAsState()

    MiniAppScaffold(
        titulo = "7. Networking",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Demostración:", fontWeight = FontWeight.SemiBold)
                Button(onClick = vm::load) { Text("Cargar") }
                when (val s = state) {
                    NetworkUiState.Idle -> Text("Idle")
                    NetworkUiState.Loading -> CircularProgressIndicator()
                    is NetworkUiState.Success -> LazyColumn(
                        modifier = Modifier.heightIn(max = 200.dp),
                    ) {
                        items(s.items) { Text("#\${it.id} \${it.nombre}") }
                    }
                    is NetworkUiState.Error -> Text("Error: \${s.message}", color = MaterialTheme.colorScheme.error)
                }
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 9: Crear `e08_room/RoomExerciseScreen.kt`**

```kotlin
package com.example.baseproject.ui.screens.exercises.e08_room

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.exercises.CodeBlock
import com.example.baseproject.ui.screens.exercises.MiniAppScaffold

private const val ENUNCIADO = """
    Esta mini-app es conceptual: no se ejecuta porque no agregamos Room
    como dependencia. El objetivo es mostrar cómo se vería una pantalla
    con persistencia local usando Room + Flow + Repository.

    Si quieres ejecutarla, agrega en build.gradle.kts:
      implementation(\"androidx.room:room-runtime:2.6.1\")
      implementation(\"androidx.room:room-ktx:2.6.1\")
      ksp(\"androidx.room:room-compiler:2.6.1\")
"""

private val SOLUCION = """
    @Entity data class Note(@PrimaryKey val id: Int, val texto: String)
    @Dao interface NoteDao {
        @Query(\"SELECT * FROM Note\") fun all(): Flow<List<Note>>
        @Insert suspend fun insert(note: Note)
    }
    @Database(entities = [Note::class], version = 1)
    abstract class AppDb : RoomDatabase() { abstract fun notes(): NoteDao }
    class NoteRepository(private val dao: NoteDao) {
        val notes: Flow<List<Note>> = dao.all()
        suspend fun add(texto: String) = dao.insert(Note(0, texto))
    }
    class NoteViewModel(private val repo: NoteRepository) : ViewModel() {
        val notes: StateFlow<List<Note>> = repo.notes.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
        fun add(texto: String) { viewModelScope.launch { repo.add(texto) } }
    }
""".trimIndent()

@Composable
fun RoomExerciseScreen() {
    MiniAppScaffold(
        titulo = "8. Room (preview)",
        enunciado = ENUNCIADO,
        contenidoSolucion = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Esta pantalla no se ejecuta: es solo código de referencia.",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error)
                Text("Solución:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
```

- [ ] **Step 10: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -20
```
Esperado: `BUILD SUCCESSFUL`.

- [ ] **Step 11: Commit**

```bash
git add -A
git commit -m "feat: mini-apps 5-8 (viewmodel, repository, network, room preview)"
```

---

### Task 9: Registrar las 8 mini-apps en ExercisesNav

**Files:**
- Modify: `app/src/main/java/com/example/baseproject/ui/navigation/ExercisesNav.kt`

- [ ] **Step 1: Reemplazar contenido de `ExercisesNav.kt`**

Reemplaza `app/src/main/java/com/example/baseproject/ui/navigation/ExercisesNav.kt`:

```kotlin
package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.exercises.ExercisesMenuScreen
import com.example.baseproject.ui.screens.exercises.e01_counter.CounterExerciseScreen
import com.example.baseproject.ui.screens.exercises.e02_list.ListExerciseScreen
import com.example.baseproject.ui.screens.exercises.e03_form.FormExerciseScreen
import com.example.baseproject.ui.screens.exercises.e04_navigation.NavigationExerciseScreen
import com.example.baseproject.ui.screens.exercises.e05_viewmodel.ViewModelExerciseScreen
import com.example.baseproject.ui.screens.exercises.e06_repository.RepositoryExerciseScreen
import com.example.baseproject.ui.screens.exercises.e07_network.NetworkExerciseScreen
import com.example.baseproject.ui.screens.exercises.e08_room.RoomExerciseScreen

@Composable
fun ExercisesNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "exercises/menu") {
        composable("exercises/menu") { ExercisesMenuScreen(navController) }
        composable("exercises/counter") { CounterExerciseScreen() }
        composable("exercises/list") { ListExerciseScreen() }
        composable("exercises/form") { FormExerciseScreen() }
        composable("exercises/navigation") { NavigationExerciseScreen(navController) }
        composable("exercises/viewmodel") { ViewModelExerciseScreen() }
        composable("exercises/repository") { RepositoryExerciseScreen() }
        composable("exercises/network") { NetworkExerciseScreen() }
        composable("exercises/room") { RoomExerciseScreen() }
    }
}
```

- [ ] **Step 2: Verificar compilación**

```bash
./gradlew :app:assembleDebug 2>&1 | tail -20
```
Esperado: `BUILD SUCCESSFUL`.

- [ ] **Step 3: Commit**

```bash
git add -A
git commit -m "feat: registrar las 8 mini-apps en ExercisesNav"
```

---## Fase 4 — Módulo `exercises/` (Kotlin JVM)

### Task 10: Setup Gradle del módulo exercises/

**Files:**
- Create: `exercises/settings.gradle.kts`
- Create: `exercises/build.gradle.kts`
- Create: `exercises/gradle.properties`
- Create: `exercises/gradle/wrapper/gradle-wrapper.properties`
- Create: `exercises/README.md`
- Create: `exercises/.gitignore`
- Create: `exercises/src/main/kotlin/.gitkeep`
- Create: `exercises/src/test/kotlin/.gitkeep`

- [ ] **Step 1: Crear `exercises/.gitignore`**

```
.gradle/
build/
.idea/
*.iml
local.properties
```

- [ ] **Step 2: Crear `exercises/settings.gradle.kts`**

```kotlin
rootProject.name = "kotlin-exercises"
```

- [ ] **Step 3: Crear `exercises/gradle.properties`**

```properties
org.gradle.jvmargs=-Xmx2g -Dfile.encoding=UTF-8
kotlin.code.style=official
```

- [ ] **Step 4: Crear `exercises/gradle/wrapper/gradle-wrapper.properties`**

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.4-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
```

- [ ] **Step 5: Crear `exercises/build.gradle.kts`**

```kotlin
plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
    }
}
```

- [ ] **Step 6: Crear `exercises/README.md`**

```markdown
# Ejercicios Kotlin — Recursos de Estudio

Módulo hermano con 33 ejercicios Kotlin autocontenidos, diseñados para
progresar de Kotlin básico a Android. No requiere Android Studio ni
emulador: corre con Gradle en JVM.

## Requisitos

- JDK 17+
- Gradle 8.4 (provisto por el wrapper)

## Cómo correr

Desde esta carpeta (`exercises/`):

```bash
./gradlew test                          # todos los tests
./gradlew test --tests "*E01*"          # un ejercicio específico
./gradlew test --tests "*_14_*"          # un bloque completo (coroutines basic)
./gradlew test --tests "*_20_*"          # bloque de Arquitectura
```

## Convención

- Un paquete por tema: `_NN_tema/`.
- Cada ejercicio tiene dos archivos:
  - `E##_nombre.kt`: contiene el `TODO()` a implementar.
  - `E##_nombreTest.kt`: test JUnit 5 que valida el comportamiento.
- Los tests fallan hasta que el alumno implementa la función. Esto es
  **esperado** y parte del aprendizaje.

## Ruta de aprendizaje

| Bloque | Carpeta | Ejercicios |
|--------|---------|-----------|
| Kotlin básico | `_00_` a `_05_` | 7 |
| Kotlin intermedio | `_06_` a `_09_` | 5 |
| Kotlin avanzado | `_10_` a `_13_` | 4 |
| Coroutines en profundidad | `_14_` a `_19_` | 6 |
| Arquitectura de Software | `_20_` a `_25_` | 6 |
| Bridge a Android | `_26_` a `_29_` | 4 |
| Kata integrador | `_30_` | 1 |

Total: 33 ejercicios.
```

- [ ] **Step 7: Crear directorios base**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo
mkdir -p exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p exercises/src/test/kotlin/cl/duoc/exercises
touch exercises/src/main/kotlin/.gitkeep
touch exercises/src/test/kotlin/.gitkeep
```

- [ ] **Step 8: Verificar build del módulo**

```bash
cd exercises && ./gradlew build 2>&1 | tail -10
```
Esperado: `BUILD SUCCESSFUL`. Si falla por JDK, instalar JDK 17 o ajustar `jvmToolchain(11)` y reintentar.

- [ ] **Step 9: Commit**

```bash
git add exercises/
git commit -m "feat: setup módulo exercises/ (Gradle JVM, JUnit 5, coroutines-test)"
```

---

### Task 11: Bloque 1 — Kotlin básico (7 ejercicios)

**Files:**
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_00_variables/E01_var_val.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_00_variables/E02_tipos_primitivos.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_01_tipos_y_strings/E01_string_templates.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_01_tipos_y_strings/E02_raw_strings.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_02_control_de_flujo/E01_when_expresiones.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_02_control_de_flujo/E02_when_sin_argumento.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_02_control_de_flujo/E03_loops.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_03_funciones_y_lambdas/E01_funciones_basicas.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_03_funciones_y_lambdas/E02_lambdas.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_04_null_safety/E01_safe_calls.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_04_null_safety/E02_elvis_let.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_05_colecciones/E01_list_set_map.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_05_colecciones/E02_operaciones_funcionales.kt`
- Create: 14 `*Test.kt` (uno por ejercicio arriba)

- [ ] **Step 1: Crear directorios de los bloques 1**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo/exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p _00_variables _01_tipos_y_strings _02_control_de_flujo _03_funciones_y_lambdas _04_null_safety _05_colecciones
cd ../../../test/kotlin/cl/duoc/exercises
mkdir -p _00_variables _01_tipos_y_strings _02_control_de_flujo _03_funciones_y_lambdas _04_null_safety _05_colecciones
```

- [ ] **Step 2: Crear `E01_var_val.kt` + test (plantilla del bloque)**

`exercises/src/main/kotlin/cl/duoc/exercises/_00_variables/E01_var_val.kt`:

```kotlin
package cl.duoc.exercises._00_variables

/**
 * EJERCICIO: var vs val
 *
 * OBJETIVO: Diferenciar variables mutables (var) de inmutables (val).
 *
 * POR QUÉ IMPORTA: en Kotlin se prefiere val por defecto. Menos
 * mutación = menos bugs. var se reserva para cuando realmente
 * necesitas reasignar.
 *
 * INSTRUCCIONES:
 * 1. Implementa `sumar` que retorne la suma de a + b. Usa val internamente.
 * 2. Implementa `contadorInmutable` que retorne un Pair(val actual: Int, val incrementado: Int)
 *    sin mutar ninguna variable.
 *
 * DIFICULTAD: 🟢
 *
 * TEST: ./gradlew :test --tests "*_00_variables*E01*"
 */
fun sumar(a: Int, b: Int): Int = TODO()

fun contadorInmutable(actual: Int): Pair<Int, Int> = TODO()
```

`exercises/src/test/kotlin/cl/duoc/exercises/_00_variables/E01_var_valTest.kt`:

```kotlin
package cl.duoc.exercises._00_variables

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class E01_var_valTest {
    @Test
    fun `sumar funciona`() {
        assertEquals(5, sumar(2, 3))
        assertEquals(0, sumar(-5, 5))
    }

    @Test
    fun `contador inmutable retorna par`() {
        assertEquals(Pair(0, 1), contadorInmutable(0))
        assertEquals(Pair(7, 8), contadorInmutable(7))
    }
}
```

- [ ] **Step 3: Crear `E02_tipos_primitivos.kt` + test**

`.../_00_variables/E02_tipos_primitivos.kt`:

```kotlin
package cl.duoc.exercises._00_variables

/**
 * EJERCICIO: Tipos primitivos
 *
 * OBJETIVO: Conocer Int, Long, Double, Float, Boolean, Char y la
 * inferencia de tipos de Kotlin.
 *
 * POR QUÉ IMPORTA: Kotlin distingue Int de Long, no como Java.
 * La inferencia (`val x = 5` infiere Int) te ahorra declaraciones
 * explícitas en el 95% de los casos.
 *
 * INSTRUCCIONES:
 * 1. Implementa `describirTipo` que retorne "Int"|"Long"|"Double"|"Boolean"
 *    según el tipo del argumento recibido.
 *
 * DIFICULTAD: 🟢
 */
fun describirTipo(x: Any): String = TODO()
```

`.../_00_variables/E02_tipos_primitivosTest.kt`:

```kotlin
package cl.duoc.exercises._00_variables

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class E02_tipos_primitivosTest {
    @Test
    fun `detecta Int`() = assertEquals("Int", describirTipo(5))
    @Test
    fun `detecta Long`() = assertEquals("Long", describirTipo(5L))
    @Test
    fun `detecta Double`() = assertEquals("Double", describirTipo(3.14))
    @Test
    fun `detecta Boolean`() = assertEquals("Boolean", describirTipo(true))
}
```

- [ ] **Step 4: Crear E01 y E02 de `_01_tipos_y_strings`**

`.../_01_tipos_y_strings/E01_string_templates.kt`:

```kotlin
package cl.duoc.exercises._01_tipos_y_strings

/**
 * EJERCICIO: String templates
 *
 * OBJETIVO: Usar $ y ${} para interpolar variables y expresiones.
 *
 * INSTRUCCIONES:
 * 1. Implementa `saludar` que retorne "Hola, <nombre>!".
 * 2. Implementa `presentar` que retorne "Soy <nombre> y tengo <edad> años".
 */
fun saludar(nombre: String): String = TODO()
fun presentar(nombre: String, edad: Int): String = TODO()
```

`.../_01_tipos_y_strings/E01_string_templatesTest.kt`:

```kotlin
package cl.duoc.exercises._01_tipos_y_strings
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_string_templatesTest {
    @Test fun `saludar`() = assertEquals("Hola, Ana!", saludar("Ana"))
    @Test fun `presentar`() = assertEquals("Soy Pedro y tengo 30 años", presentar("Pedro", 30))
}
```

`.../_01_tipos_y_strings/E02_raw_strings.kt`:

```kotlin
package cl.duoc.exercises._01_tipos_y_strings

/**
 * EJERCICIO: Raw strings (triple comilla)
 *
 * OBJETIVO: Crear strings multilínea sin escapar caracteres. Útil para
 * SQL, JSON, HTML embebido.
 *
 * INSTRUCCIONES:
 * 1. Implementa `crearSqlInsert` que reciba tabla y dos columnas
 *    y retorne un INSERT formateado.
 */
fun crearSqlInsert(tabla: String, col1: String, val1: String, col2: String, val2: String): String = TODO()
```

`.../_01_tipos_y_strings/E02_raw_stringsTest.kt`:

```kotlin
package cl.duoc.exercises._01_tipos_y_strings
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
class E02_raw_stringsTest {
    @Test fun `sql insert`() {
        val sql = crearSqlInsert("usuarios", "nombre", "Ana", "edad", "30")
        assertTrue(sql.contains("INSERT INTO usuarios"))
        assertTrue(sql.contains("nombre = 'Ana'"))
        assertTrue(sql.contains("edad = '30'"))
    }
}
```

- [ ] **Step 5: Crear E01, E02, E03 de `_02_control_de_flujo`**

`.../_02_control_de_flujo/E01_when_expresiones.kt`:

```kotlin
package cl.duoc.exercises._02_control_de_flujo

/**
 * EJERCICIO: when con argumento (expresión)
 *
 * OBJETIVO: Practicar when como expression (retorna valor) — más potente
 * que switch de Java porque permite rangos, tipos y comparación arbitraria.
 *
 * INSTRUCCIONES:
 * 1. `clasificarNota(nota: Int): String` → "Suspenso" (<5), "Aprobado"
 *    (5-6), "Notable" (7-8), "Sobresaliente" (9-10), "Inválida" (otro).
 */
fun clasificarNota(nota: Int): String = TODO()
```

`.../_02_control_de_flujo/E01_when_expresionesTest.kt`:

```kotlin
package cl.duoc.exercises._02_control_de_flujo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_when_expresionesTest {
    @Test fun `suspenso`() = assertEquals("Suspenso", clasificarNota(3))
    @Test fun `aprobado`() = assertEquals("Aprobado", clasificarNota(5))
    @Test fun `notable`() = assertEquals("Notable", clasificarNota(7))
    @Test fun `sobresaliente`() = assertEquals("Sobresaliente", clasificarNota(10))
    @Test fun `invalida`() = assertEquals("Inválida", clasificarNota(11))
}
```

`.../_02_control_de_flujo/E02_when_sin_argumento.kt`:

```kotlin
package cl.duoc.exercises._02_control_de_flujo

/**
 * EJERCICIO: when sin argumento
 *
 * OBJETIVO: when sin sujeto es como if/elseif/else pero más legible
 * cuando las condiciones son múltiples.
 *
 * INSTRUCCIONES:
 * 1. `describir(x: Any)` retorna:
 *    - "Número positivo" si x es Int o Double y > 0.
 *    - "Número negativo" si es Int o Double y < 0.
 *    - "Cero" si es 0.
 *    - "Texto vacío" si es String y está vacío.
 *    - "Texto" si es String no vacío.
 *    - "Boolean" si es Boolean.
 *    - "Otro" en cualquier otro caso.
 */
fun describir(x: Any): String = TODO()
```

`.../_02_control_de_flujo/E02_when_sin_argumentoTest.kt`:

```kotlin
package cl.duoc.exercises._02_control_de_flujo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_when_sin_argumentoTest {
    @Test fun `positivo`() = assertEquals("Número positivo", describir(5))
    @Test fun `negativo`() = assertEquals("Número negativo", describir(-3.14))
    @Test fun `cero`() = assertEquals("Cero", describir(0))
    @Test fun `string vacio`() = assertEquals("Texto vacío", describir(""))
    @Test fun `string`() = assertEquals("Texto", describir("hola"))
    @Test fun `bool`() = assertEquals("Boolean", describir(true))
    @Test fun `otro`() = assertEquals("Otro", describir(listOf(1, 2)))
}
```

`.../_02_control_de_flujo/E03_loops.kt`:

```kotlin
package cl.duoc.exercises._02_control_de_flujo

/**
 * EJERCICIO: Loops en Kotlin
 *
 * OBJETIVO: for, while, do-while, repeat, rangos.
 *
 * INSTRUCCIONES:
 * 1. `sumarPares(hasta: Int)` suma los números pares entre 1 y hasta (inclusive).
 * 2. `primerImparEnPosicion(lista: List<Int>, posicion: Int)` retorna el
 *    elemento en la posición o -1 si está fuera de rango.
 */
fun sumarPares(hasta: Int): Int = TODO()
fun primerImparEnPosicion(lista: List<Int>, posicion: Int): Int = TODO()
```

`.../_02_control_de_flujo/E03_loopsTest.kt`:

```kotlin
package cl.duoc.exercises._02_control_de_flujo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E03_loopsTest {
    @Test fun `sumar pares hasta 10`() = assertEquals(30, sumarPares(10))
    @Test fun `sumar pares hasta 1`() = assertEquals(0, sumarPares(1))
    @Test fun `elemento en posicion`() = assertEquals(3, primerImparEnPosicion(listOf(1,2,3,4,5), 2))
    @Test fun `fuera de rango`() = assertEquals(-1, primerImparEnPosicion(listOf(1,2,3), 10))
}
```

- [ ] **Step 6: Crear E01 y E02 de `_03_funciones_y_lambdas`**

`.../_03_funciones_y_lambdas/E01_funciones_basicas.kt`:

```kotlin
package cl.duoc.exercises._03_funciones_y_lambdas

/**
 * EJERCICIO: Funciones en Kotlin
 *
 * OBJETIVO: default args, named args, single-expression, vararg, infix.
 *
 * INSTRUCCIONES:
 * 1. `saludo` retorna "Hola, <nombre>" con argumento por defecto "mundo".
 * 2. `concatenar` recibe vararg de strings y los une con separador por defecto ",".
 * 3. `infix fun Int.multiplicadoPor(n: Int)` retorna this * n.
 */
fun saludo(nombre: String = "mundo"): String = TODO()
fun concatenar(separador: String = ",", vararg partes: String): String = TODO()
infix fun Int.multiplicadoPor(n: Int): Int = TODO()
```

`.../_03_funciones_y_lambdas/E01_funciones_basicasTest.kt`:

```kotlin
package cl.duoc.exercises._03_funciones_y_lambdas
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_funciones_basicasTest {
    @Test fun `default`() = assertEquals("Hola, mundo", saludo())
    @Test fun `con nombre`() = assertEquals("Hola, Ana", saludo("Ana"))
    @Test fun `concatenar`() = assertEquals("a-b-c", concatenar("-", "a", "b", "c"))
    @Test fun `infix`() = assertEquals(15, 3 multiplicadoPor 5)
}
```

`.../_03_funciones_y_lambdas/E02_lambdas.kt`:

```kotlin
package cl.duoc.exercises._03_funciones_y_lambdas

/**
 * EJERCICIO: Lambdas y funciones de orden superior
 *
 * OBJETIVO: Pasar comportamiento como parámetro. La base de map/filter/etc.
 *
 * INSTRUCCIONES:
 * 1. `aplicarDoble(lista, transform)` aplica transform a cada elemento y retorna el doble.
 * 2. `filtrar` retorna sublista de elementos que cumplen `predicado`.
 * 3. `reducirA(lista, inicial, op)` reduce la lista con op partiendo de inicial.
 */
fun aplicarDoble(lista: List<Int>, transform: (Int) -> Int): List<Int> = TODO()
fun filtrar(lista: List<Int>, predicado: (Int) -> Boolean): List<Int> = TODO()
fun reducirA(lista: List<Int>, inicial: Int, op: (Int, Int) -> Int): Int = TODO()
```

`.../_03_funciones_y_lambdas/E02_lambdasTest.kt`:

```kotlin
package cl.duoc.exercises._03_funciones_y_lambdas
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_lambdasTest {
    @Test fun `aplicar doble`() = assertEquals(listOf(2,4,6), aplicarDoble(listOf(1,2,3)) { it * 2 })
    @Test fun `filtrar`() = assertEquals(listOf(2,4), filtrar(listOf(1,2,3,4)) { it % 2 == 0 })
    @Test fun `reducir`() = assertEquals(15, reducirA(listOf(1,2,3,4,5), 0) { acc, x -> acc + x })
}
```

- [ ] **Step 7: Crear E01 y E02 de `_04_null_safety`**

`.../_04_null_safety/E01_safe_calls.kt`:

```kotlin
package cl.duoc.exercises._04_null_safety

/**
 * EJERCICIO: Safe calls (?.) y Elvis (?:)
 *
 * OBJETIVO: Manejar nullables sin NullPointerException.
 *
 * INSTRUCCIONES:
 * 1. `longitudSegura(s: String?): Int` retorna s?.length ?: 0.
 * 2. `primerCaracter(s: String?): Char?` retorna s?.firstOrNull().
 */
fun longitudSegura(s: String?): Int = TODO()
fun primerCaracter(s: String?): Char? = TODO()
```

`.../_04_null_safety/E01_safe_callsTest.kt`:

```kotlin
package cl.duoc.exercises._04_null_safety
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_safe_callsTest {
    @Test fun `null es 0`() = assertEquals(0, longitudSegura(null))
    @Test fun `hola es 4`() = assertEquals(4, longitudSegura("hola"))
    @Test fun `vacio es 0`() = assertEquals(0, longitudSegura(""))
    @Test fun `primer char`() = assertEquals('A', primerCaracter("Ana"))
    @Test fun `null primer`() = assertEquals(null, primerCaracter(null))
    @Test fun `vacio primer`() = assertEquals(null, primerCaracter(""))
}
```

`.../_04_null_safety/E02_elvis_let.kt`:

```kotlin
package cl.duoc.exercises._04_null_safety

/**
 * EJERCICIO: Elvis + let + safe cast
 *
 * INSTRUCCIONES:
 * 1. `comoString(x: Any?): String` retorna x como String o "" si no lo es o es null.
 * 2. `safeCastLista(x: Any): Int` retorna el size de la lista si x es List<*>, sino -1.
 */
fun comoString(x: Any?): String = TODO()
fun safeCastLista(x: Any): Int = TODO()
```

`.../_04_null_safety/E02_elvis_letTest.kt`:

```kotlin
package cl.duoc.exercises._04_null_safety
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_elvis_letTest {
    @Test fun `string`() = assertEquals("hola", comoString("hola"))
    @Test fun `int como string`() = assertEquals("", comoString(5))
    @Test fun `null como string`() = assertEquals("", comoString(null))
    @Test fun `lista`() = assertEquals(3, safeCastLista(listOf(1,2,3)))
    @Test fun `no lista`() = assertEquals(-1, safeCastLista("hola"))
}
```

- [ ] **Step 8: Crear E01 y E02 de `_05_colecciones`**

`.../_05_colecciones/E01_list_set_map.kt`:

```kotlin
package cl.duoc.exercises._05_colecciones

/**
 * EJERCICIO: List, Set, Map
 *
 * OBJETIVO: Diferenciar listOf / mutableListOf / setOf / mutableSetOf / mapOf.
 *
 * INSTRUCCIONES:
 * 1. `sinDuplicados(lista: List<Int>): List<Int>` retorna la lista sin duplicados,
 *    preservando el orden de la primera aparición.
 * 2. `invertirMap(mapa: Map<String, Int>): Map<Int, String>` invierte claves/valores.
 */
fun sinDuplicados(lista: List<Int>): List<Int> = TODO()
fun invertirMap(mapa: Map<String, Int>): Map<Int, String> = TODO()
```

`.../_05_colecciones/E01_list_set_mapTest.kt`:

```kotlin
package cl.duoc.exercises._05_colecciones
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_list_set_mapTest {
    @Test fun `sin duplicados`() = assertEquals(listOf(1,2,3), sinDuplicados(listOf(1,2,2,3,1)))
    @Test fun `vacio`() = assertEquals(emptyList<Int>(), sinDuplicados(emptyList()))
    @Test fun `invertir`() {
        assertEquals(mapOf(1 to "a", 2 to "b"), invertirMap(mapOf("a" to 1, "b" to 2)))
    }
}
```

`.../_05_colecciones/E02_operaciones_funcionales.kt`:

```kotlin
package cl.duoc.exercises._05_colecciones

/**
 * EJERCICIO: Operaciones funcionales sobre colecciones
 *
 * OBJETIVO: map, filter, reduce, groupBy, partition, count, any, all, none, sum.
 *
 * INSTRUCCIONES:
 * 1. `top3MasGrandes(lista: List<Int>): List<Int>` retorna los 3 mayores, ordenados desc.
 * 2. `agruparPorParidad(lista: List<Int>): Map<String, List<Int>` agrupa en
 *    "pares" e "impares".
 * 3. `sonTodosPositivos(lista: List<Int>): Boolean`.
 */
fun top3MasGrandes(lista: List<Int>): List<Int> = TODO()
fun agruparPorParidad(lista: List<Int>): Map<String, List<Int>> = TODO()
fun sonTodosPositivos(lista: List<Int>): Boolean = TODO()
```

`.../_05_colecciones/E02_operaciones_funcionalesTest.kt`:

```kotlin
package cl.duoc.exercises._05_colecciones
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_operaciones_funcionalesTest {
    @Test fun `top3`() = assertEquals(listOf(9, 7, 5), top3MasGrandes(listOf(1,5,3,9,7,2)))
    @Test fun `agrupar`() = assertEquals(
        mapOf("pares" to listOf(2,4), "impares" to listOf(1,3)),
        agruparPorParidad(listOf(1,2,3,4))
    )
    @Test fun `positivos`() = assertEquals(true, sonTodosPositivos(listOf(1,2,3)))
    @Test fun `no positivos`() = assertEquals(false, sonTodosPositivos(listOf(1,-2,3)))
}
```

- [ ] **Step 9: Verificar que el módulo sigue compilando**

```bash
cd exercises && ./gradlew compileKotlin compileTestKotlin 2>&1 | tail -10
```
Esperado: `BUILD SUCCESSFUL`. Los tests fallarán al ejecutarse (`TODO()` no compila al invocarse), pero la compilación debe pasar.

- [ ] **Step 10: Commit**

```bash
git add exercises/
git commit -m "feat: bloque 1 - Kotlin básico (7 ejercicios)"
```

---### Task 12: Bloque 2 — Kotlin intermedio (5 ejercicios)

**Files:**
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_06_clases_y_objetos/E01_clase_basica.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_06_clases_y_objetos/E01_clase_basicaTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_06_clases_y_objetos/E02_init_blocks.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_06_clases_y_objetos/E02_init_blocksTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_07_data_classes_sealed/E01_data_class.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_07_data_classes_sealed/E01_data_classTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_07_data_classes_sealed/E02_sealed_class.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_07_data_classes_sealed/E02_sealed_classTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_08_interfaces_y_herencia/E01_interfaces.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_08_interfaces_y_herencia/E01_interfacesTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_09_generics/E01_generics.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_09_generics/E01_genericsTest.kt`

- [ ] **Step 1: Crear directorios**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo/exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p _06_clases_y_objetos _07_data_classes_sealed _08_interfaces_y_herencia _09_generics
cd ../../../test/kotlin/cl/duoc/exercises
mkdir -p _06_clases_y_objetos _07_data_classes_sealed _08_interfaces_y_herencia _09_generics
```

- [ ] **Step 2: Crear `E01_clase_basica.kt` + test**

`.../_06_clases_y_objetos/E01_clase_basica.kt`:

```kotlin
package cl.duoc.exercises._06_clases_y_objetos

/**
 * EJERCICIO: Clase básica en Kotlin
 *
 * OBJETIVO: Constructores primarios, propiedades en el constructor.
 *
 * INSTRUCCIONES:
 * 1. Implementa la clase `Persona` con propiedades inmutables `nombre`
 *    (String) y `edad` (Int).
 * 2. Implementa `presentarse()` que retorne "Soy <nombre> y tengo <edad> años".
 */
class Persona(val nombre: String, val edad: Int) {
    fun presentarse(): String = TODO()
}
```

`.../_06_clases_y_objetos/E01_clase_basicaTest.kt`:

```kotlin
package cl.duoc.exercises._06_clases_y_objetos
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_clase_basicaTest {
    @Test fun `presentar`() = assertEquals("Soy Ana y tengo 30 años", Persona("Ana", 30).presentarse())
    @Test fun `propiedades`() {
        val p = Persona("Beto", 25)
        assertEquals("Beto", p.nombre)
        assertEquals(25, p.edad)
    }
}
```

- [ ] **Step 3: Crear `E02_init_blocks.kt` + test**

`.../_06_clases_y_objetos/E02_init_blocks.kt`:

```kotlin
package cl.duoc.exercises._06_clases_y_objetos

/**
 * EJERCICIO: Init blocks y constructores secundarios
 *
 * OBJETIVO: Bloques init se ejecutan en orden; constructor secundario
 * delega al primario con `: this(...)`.
 *
 * INSTRUCCIONES:
 * 1. Implementa `CuentaBancaria` con `saldo: Double` (no puede ser negativo).
 *    Init block debe validar y lanzar IllegalArgumentException si saldo < 0.
 *    Constructor secundario sin argumentos que inicializa saldo = 0.
 */
class CuentaBancaria(saldoInicial: Double) {
    val saldo: Double = saldoInicial.also {
        require(it >= 0) { "El saldo inicial no puede ser negativo" }
    }

    constructor() : this(0.0)
}
```

`.../_06_clases_y_objetos/E02_init_blocksTest.kt`:

```kotlin
package cl.duoc.exercises._06_clases_y_objetos
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
class E02_init_blocksTest {
    @Test fun `saldo positivo`() = assertEquals(100.0, CuentaBancaria(100.0).saldo)
    @Test fun `sin argumentos`() = assertEquals(0.0, CuentaBancaria().saldo)
    @Test fun `saldo negativo lanza`() = assertThrows(IllegalArgumentException::class.java) { CuentaBancaria(-10.0) }
}
```

- [ ] **Step 4: Crear `E01_data_class.kt` + test**

`.../_07_data_classes_sealed/E01_data_class.kt`:

```kotlin
package cl.duoc.exercises._07_data_classes_sealed

/**
 * EJERCICIO: Data class
 *
 * OBJETIVO: equals/hashCode/copy/toString/componentN automáticos.
 *
 * INSTRUCCIONES:
 * 1. Implementa la data class `Punto(x: Int, y: Int)`.
 * 2. Implementa `swap()` que retorna un Punto con x e y intercambiados.
 *    Usa `copy()`.
 */
data class Punto(val x: Int, val y: Int) {
    fun swap(): Punto = TODO()
}
```

`.../_07_data_classes_sealed/E01_data_classTest.kt`:

```kotlin
package cl.duoc.exercises._07_data_classes_sealed
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
class E01_data_classTest {
    @Test fun `swap`() = assertEquals(Punto(2, 1), Punto(1, 2).swap())
    @Test fun `equals`() = assertEquals(Punto(1, 2), Punto(1, 2))
    @Test fun `no equals`() = assertNotEquals(Punto(1, 2), Punto(2, 1))
    @Test fun `componentN`() {
        val (x, y) = Punto(7, 9)
        assertEquals(7, x)
        assertEquals(9, y)
    }
}
```

- [ ] **Step 5: Crear `E02_sealed_class.kt` + test**

`.../_07_data_classes_sealed/E02_sealed_class.kt`:

```kotlin
package cl.duoc.exercises._07_data_classes_sealed

/**
 * EJERCICIO: Sealed class
 *
 * OBJETIVO: Modelar jerarquías cerradas. Útil para UI state y resultados.
 *
 * INSTRUCCIONES:
 * 1. Define sealed class `Resultado` con tres subclases: Exito(val valor: Int),
 *    Error(val mensaje: String), Pendiente.
 * 2. Implementa `describir(r: Resultado): String` que use when exhaustivo
 *    y retorne "Exito(<valor>)", "Error: <mensaje>", "Pendiente".
 */
sealed class Resultado {
    data class Exito(val valor: Int) : Resultado()
    data class Error(val mensaje: String) : Resultado()
    data object Pendiente : Resultado()
}

fun describir(r: Resultado): String = TODO()
```

`.../_07_data_classes_sealed/E02_sealed_classTest.kt`:

```kotlin
package cl.duoc.exercises._07_data_classes_sealed
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_sealed_classTest {
    @Test fun `exito`() = assertEquals("Exito(42)", describir(Resultado.Exito(42)))
    @Test fun `error`() = assertEquals("Error: oops", describir(Resultado.Error("oops")))
    @Test fun `pendiente`() = assertEquals("Pendiente", describir(Resultado.Pendiente))
}
```

- [ ] **Step 6: Crear `E01_interfaces.kt` + test**

`.../_08_interfaces_y_herencia/E01_interfaces.kt`:

```kotlin
package cl.duoc.exercises._08_interfaces_y_herencia

/**
 * EJERCICIO: Interfaces y polimorfismo
 *
 * OBJETIVO: Definir contratos con default methods. Las clases que la
 * implementan pueden override.
 *
 * INSTRUCCIONES:
 * 1. Define interface `Forma` con propiedad abstracta `area: Double` y
 *    método `describir(): String` que retorna "Forma con área <area>".
 * 2. Implementa `Circulo(radio: Double)` y `Cuadrado(lado: Double)`.
 */
interface Forma {
    val area: Double
    fun describir(): String = "Forma con área \${area}"
}

class Circulo(val radio: Double) : Forma {
    override val area: Double = TODO()
}

class Cuadrado(val lado: Double) : Forma {
    override val area: Double = TODO()
}
```

`.../_08_interfaces_y_herencia/E01_interfacesTest.kt`:

```kotlin
package cl.duoc.exercises._08_interfaces_y_herencia
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_interfacesTest {
    @Test fun `circulo`() {
        val c = Circulo(2.0)
        assertEquals(Math.PI * 4, c.area, 0.0001)
        assertEquals("Forma con área \${c.area}", c.describir())
    }
    @Test fun `cuadrado`() {
        val q = Cuadrado(3.0)
        assertEquals(9.0, q.area, 0.0001)
    }
}
```

- [ ] **Step 7: Crear `E01_generics.kt` + test**

`.../_09_generics/E01_generics.kt`:

```kotlin
package cl.duoc.exercises._09_generics

/**
 * EJERCICIO: Generics con variance
 *
 * OBJETIVO: `out T` (covariance) permite Producer; `in T` (contravariance)
 * permite Consumer.
 *
 * INSTRUCCIONES:
 * 1. Implementa `primero(lista: List<T>): T?` que retorna el primer
 *    elemento o null si está vacía.
 * 2. Implementa `convertirAString(lista: List<out Any>): String` que
 *    une los elementos (covariance out Any permite leer).
 */
fun <T> primero(lista: List<T>): T? = TODO()
fun convertirAString(lista: List<out Any>): String = TODO()
```

`.../_09_generics/E01_genericsTest.kt`:

```kotlin
package cl.duoc.exercises._09_generics
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
class E01_genericsTest {
    @Test fun `primero`() = assertEquals(1, primero(listOf(1,2,3)))
    @Test fun `vacio`() = assertNull(primero(emptyList<Int>()))
    @Test fun `convertir`() = assertEquals("a-b-c", convertirAString(listOf("a","b","c")))
}
```

- [ ] **Step 8: Verificar compilación y commit**

```bash
cd exercises && ./gradlew compileKotlin compileTestKotlin 2>&1 | tail -10
```
Esperado: `BUILD SUCCESSFUL`.

```bash
git add exercises/
git commit -m "feat: bloque 2 - Kotlin intermedio (5 ejercicios)"
```

---

### Task 13: Bloque 3 — Kotlin avanzado: características del lenguaje (4 ejercicios)

**Files:**
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_10_scope_functions/E01_scope_functions.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_10_scope_functions/E01_scope_functionsTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_11_extension_functions/E01_extension.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_11_extension_functions/E01_extensionTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_12_properties_delegates/E01_delegates.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_12_properties_delegates/E01_delegatesTest.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_13_exception_handling/E01_excepciones.kt`
- Create: `exercises/src/main/kotlin/cl/duoc/exercises/_13_exception_handling/E01_excepcionesTest.kt`

- [ ] **Step 1: Crear directorios**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo/exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p _10_scope_functions _11_extension_functions _12_properties_delegates _13_exception_handling
cd ../../../test/kotlin/cl/duoc/exercises
mkdir -p _10_scope_functions _11_extension_functions _12_properties_delegates _13_exception_handling
```

- [ ] **Step 2: Crear `E01_scope_functions.kt` + test**

`.../_10_scope_functions/E01_scope_functions.kt`:

```kotlin
package cl.duoc.exercises._10_scope_functions

/**
 * EJERCICIO: Scope functions
 *
 * OBJETIVO: `let`, `apply`, `also`, `run`, `with`.
 *   - let:   it como argumento, retorna lambda. Para null-safe.
 *   - apply: this como receptor, retorna this. Para configurar.
 *   - also:  it como argumento, retorna this. Para side-effects.
 *   - run:   this como receptor, retorna lambda. Para computar.
 *   - with: igual que run pero no extension.
 *
 * INSTRUCCIONES:
 * 1. `configurar(): StringBuilder` retorna un StringBuilder con "Hola", " ", "Mundo" usando apply.
 * 2. `safeLength(s: String?): Int` retorna s?.let { it.length } ?: 0.
 */
fun configurar(): StringBuilder = TODO()
fun safeLength(s: String?): Int = TODO()
```

`.../_10_scope_functions/E01_scope_functionsTest.kt`:

```kotlin
package cl.duoc.exercises._10_scope_functions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_scope_functionsTest {
    @Test fun `configurar`() = assertEquals("Hola Mundo", configurar().toString())
    @Test fun `safe length`() {
        assertEquals(4, safeLength("hola"))
        assertEquals(0, safeLength(null))
    }
}
```

- [ ] **Step 3: Crear `E01_extension.kt` + test**

`.../_11_extension_functions/E01_extension.kt`:

```kotlin
package cl.duoc.exercises._11_extension_functions

/**
 * EJERCICIO: Extension functions y properties
 *
 * OBJETIVO: Agregar funciones/properties a clases sin modificarlas.
 * Útiles para hacer legible código (e.g., `list.second()`).
 *
 * INSTRUCCIONES:
 * 1. Implementa extension property `List<Int>.segundo` que retorne el
 *    segundo elemento o -1 si no existe.
 * 2. Implementa extension function `String.esPalindromo(): Boolean`
 *    que ignore mayúsculas y espacios.
 */
val List<Int>.segundo: Int
    get() = TODO()

fun String.esPalindromo(): Boolean = TODO()
```

`.../_11_extension_functions/E01_extensionTest.kt`:

```kotlin
package cl.duoc.exercises._11_extension_functions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
class E01_extensionTest {
    @Test fun `segundo ok`() = assertEquals(2, listOf(1,2,3).segundo)
    @Test fun `segundo vacio`() = assertEquals(-1, emptyList<Int>().segundo)
    @Test fun `segundo uno`() = assertEquals(-1, listOf(1).segundo)
    @Test fun `palindromo`() = assertTrue("Anita lava la tina".esPalindromo())
    @Test fun `no palindromo`() = assertFalse("hola".esPalindromo())
}
```

- [ ] **Step 4: Crear `E01_delegates.kt` + test**

`.../_12_properties_delegates/E01_delegates.kt`:

```kotlin
package cl.duoc.exercises._12_properties_delegates

import kotlin.properties.Delegates

/**
 * EJERCICIO: Property delegates
 *
 * OBJETIVO: `lazy`, `observable`, `vetoable`, `Delegates.notNull()`.
 *
 * INSTRUCCIONES:
 * 1. `valorGrande: String` lazy que retorna "computado" solo en el primer acceso.
 * 2. `contador: Int` con vetoable que solo acepta valores positivos.
 * 3. `nombre: String` con observable que registra cada cambio en `log`.
 */
val valorGrande: String by lazy { TODO() }

class Config {
    var contador: Int by Delegates.vetoable(0) { _, _, nuevo -> TODO() }
    var nombre: String by Delegates.observable("<sin nombre>") { _, _, nuevo -> log.add(nuevo) }
    val log: MutableList<String> = mutableListOf()
}
```

`.../_12_properties_delegates/E01_delegatesTest.kt`:

```kotlin
package cl.duoc.exercises._12_properties_delegates
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_delegatesTest {
    @Test fun `lazy`() = assertEquals("computado", valorGrande)
    @Test fun `vetoable`() {
        val c = Config()
        c.contador = 5
        assertEquals(5, c.contador)
        c.contador = -1
        assertEquals(5, c.contador) // veto
    }
    @Test fun `observable`() {
        val c = Config()
        c.nombre = "Ana"
        c.nombre = "Beto"
        assertEquals(listOf("Ana", "Beto"), c.log)
    }
}
```

- [ ] **Step 5: Crear `E01_excepciones.kt` + test**

`.../_13_exception_handling/E01_excepciones.kt`:

```kotlin
package cl.duoc.exercises._13_exception_handling

/**
 * EJERCICIO: Manejo de excepciones
 *
 * OBJETIVO: try/catch/finally, excepciones custom, runCatching.
 *
 * INSTRUCCIONES:
 * 1. Implementa `parsearEntero(s: String): Int` que retorne el entero
 *    parseado o 0 si la cadena no es válida. Usa `runCatching` y `.getOrDefault(0)`.
 * 2. Define `class SaldoInsuficienteException(val deficit: Double) : Exception(...)`.
 * 3. Implementa `retirar(saldo: Double, monto: Double): Double` que reste
 *    monto a saldo o lance SaldoInsuficienteException con el déficit.
 */
class SaldoInsuficienteException(val deficit: Double) :
    Exception("Saldo insuficiente: faltan \${deficit}")

fun parsearEntero(s: String): Int = TODO()

fun retirar(saldo: Double, monto: Double): Double = TODO()
```

`.../_13_exception_handling/E01_excepcionesTest.kt`:

```kotlin
package cl.duoc.exercises._13_exception_handling
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
class E01_excepcionesTest {
    @Test fun `parsear ok`() = assertEquals(42, parsearEntero("42"))
    @Test fun `parsear invalido`() = assertEquals(0, parsearEntero("abc"))
    @Test fun `retirar ok`() = assertEquals(50.0, retirar(100.0, 50.0))
    @Test fun `retirar insuficiente`() {
        val ex = assertThrows(SaldoInsuficienteException::class.java) { retirar(30.0, 50.0) }
        assertEquals(20.0, ex.deficit, 0.0001)
    }
}
```

- [ ] **Step 6: Verificar compilación y commit**

```bash
cd exercises && ./gradlew compileKotlin compileTestKotlin 2>&1 | tail -10
git add exercises/
git commit -m "feat: bloque 3 - Kotlin avanzado: características (4 ejercicios)"
```

---

### Task 14: Bloque 4 — Coroutines en profundidad (6 ejercicios)

**Files:** (12 archivos .kt + 12 tests)
- `_14_coroutines_basics/E01_launch.kt` + Test
- `_14_coroutines_basics/E02_async_await.kt` + Test
- `_15_coroutines_structured/E01_cancellation.kt` + Test
- `_15_coroutines_structured/E02_supervisorScope.kt` + Test
- `_16_coroutines_dispatchers/E01_withContext.kt` + Test
- `_17_coroutines_flow/E01_flow_basics.kt` + Test
- `_18_coroutines_state_flow/E01_state_flow.kt` + Test
- `_19_coroutines_error_handling/E01_catch.kt` + Test

- [ ] **Step 1: Crear directorios**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo/exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p _14_coroutines_basics _15_coroutines_structured _16_coroutines_dispatchers _17_coroutines_flow _18_coroutines_state_flow _19_coroutines_error_handling
cd ../../../test/kotlin/cl/duoc/exercises
mkdir -p _14_coroutines_basics _15_coroutines_structured _16_coroutines_dispatchers _17_coroutines_flow _18_coroutines_state_flow _19_coroutines_error_handling
```

- [ ] **Step 2: Crear `_14_coroutines_basics/E01_launch.kt` + test**

`.../_14_coroutines_basics/E01_launch.kt`:

```kotlin
package cl.duoc.exercises._14_coroutines_basics

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * EJERCICIO: Coroutines básicas — launch
 *
 * OBJETIVO: Una coroutine no es un thread: es una unidad de suspensión
 * cooperativa. `launch` arranca coroutine que devuelve Job.
 *
 * INSTRUCCIONES:
 * 1. `lanzarYSaludar()` arranca una coroutine que duerme 100ms y
 *    luego imprime "Hola desde coroutine". Retorna Unit. Usa
 *    `runBlocking` para esperar.
 */
fun lanzarYSaludar() = TODO()
```

`.../_14_coroutines_basics/E01_launchTest.kt`:

```kotlin
package cl.duoc.exercises._14_coroutines_basics
import org.junit.jupiter.api.Test
class E01_launchTest {
    @Test fun `lanza y espera`() {
        // No hay assert: si no se lanza excepción, runBlocking ejecutó la coroutine.
        lanzarYSaludar()
    }
}
```

(Nota: el alumno debe usar `runBlocking { launch { delay(100); println(...) } }` para implementar.)

- [ ] **Step 3: Crear `_14_coroutines_basics/E02_async_await.kt` + test**

`.../_14_coroutines_basics/E02_async_await.kt`:

```kotlin
package cl.duoc.exercises._14_coroutines_basics

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * EJERCICIO: async / await
 *
 * OBJETIVO: async retorna Deferred<T>; await() obtiene el valor.
 * Útil para paralelizar tareas independientes.
 *
 * INSTRUCCIONES:
 * 1. `sumarParalelo(a: Int, b: Int): Int` calcula a*2 y b*3 en paralelo
 *    usando `coroutineScope { async { ... }; async { ... } }` y retorna
 *    la suma de los dos resultados.
 */
suspend fun sumarParalelo(a: Int, b: Int): Int = TODO()
```

`.../_14_coroutines_basics/E02_async_awaitTest.kt`:

```kotlin
package cl.duoc.exercises._14_coroutines_basics
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_async_awaitTest {
    @Test fun `suma en paralelo`() = runTest {
        assertEquals(13, sumarParalelo(2, 3))  // 2*2 + 3*3 = 4 + 9
    }
}
```

- [ ] **Step 4: Crear `_15_coroutines_structured/E01_cancellation.kt` + test**

`.../_15_coroutines_structured/E01_cancellation.kt`:

```kotlin
package cl.duoc.exercises._15_coroutines_structured

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * EJERCICIO: Cancelación cooperativa
 *
 * OBJETIVO: Las coroutines no se cancelan a la fuerza; chequean
 * `isActive` o llaman a funciones suspendibles que la cancelan
 * (delay, yield). Si tu coroutine hace CPU-bound sin chequear, no se cancela.
 *
 * INSTRUCCIONES:
 * 1. `cancelarAntesDe(): Job` lanza una coroutine que duerme 1000ms
 *    y luego imprime "terminó". Tras 100ms, cancela el Job retornado.
 *    Usa `runBlocking` para esperar.
 */
fun cancelarAntesDe(): Job = TODO()
```

`.../_15_coroutines_structured/E01_cancellationTest.kt`:

```kotlin
package cl.duoc.exercises._15_coroutines_structured
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
class E01_cancellationTest {
    @Test fun `job se cancela`() = runBlocking {
        val job = cancelarAntesDe()
        job.join()
        assertTrue(job.isCancelled)
    }
}
```

- [ ] **Step 5: Crear `_15_coroutines_structured/E02_supervisorScope.kt` + test**

`.../_15_coroutines_structured/E02_supervisorScope.kt`:

```kotlin
package cl.duoc.exercises._15_coroutines_structured

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.runBlocking

/**
 * EJERCICIO: supervisorScope vs coroutineScope
 *
 * OBJETIVO: En `coroutineScope`, una excepción en un hijo cancela a todos.
 * En `supervisorScope`, los hermanos sobreviven.
 *
 * INSTRUCCIONES:
 * 1. `supervisarHermanos()` lanza dos coroutines dentro de
 *    `supervisorScope`. La primera falla con RuntimeException("A").
 *    La segunda termina OK y agrega "B" a una MutableList.
 *    Retorna la lista.
 */
fun supervisarHermanos(): List<String> = TODO()
```

`.../_15_coroutines_structured/E02_supervisorScopeTest.kt`:

```kotlin
package cl.duoc.exercises._15_coroutines_structured
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E02_supervisorScopeTest {
    @Test fun `hermano sobrevive`() = runBlocking {
        val result = supervisarHermanos()
        assertEquals(listOf("B"), result)
    }
}
```

- [ ] **Step 6: Crear `_16_coroutines_dispatchers/E01_withContext.kt` + test**

`.../_16_coroutines_dispatchers/E01_withContext.kt`:

```kotlin
package cl.duoc.exercises._16_coroutines_dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * EJERCICIO: withContext y Dispatchers
 *
 * OBJETIVO: Dispatchers.Main/IO/Default. withContext cambia de thread
 * sin necesidad de callback hell.
 *
 * INSTRUCCIONES:
 * 1. `threadDeIO(): String` retorna el nombre del thread desde
 *    `Dispatchers.IO`. Usa `withContext(Dispatchers.IO) { Thread.currentThread().name }`.
 */
suspend fun threadDeIO(): String = TODO()
```

`.../_16_coroutines_dispatchers/E01_withContextTest.kt`:

```kotlin
package cl.duoc.exercises._16_coroutines_dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull
class E01_withContextTest {
    @Test fun `thread de IO`() = runTest {
        val name = threadDeIO()
        assertNotNull(name)
        // No podemos asertar nombre exacto porque los threads cambian;
        // solo verificamos que retorna algo.
    }
}
```

- [ ] **Step 7: Crear `_17_coroutines_flow/E01_flow_basics.kt` + test**

`.../_17_coroutines_flow/E01_flow_basics.kt`:

```kotlin
package cl.duoc.exercises._17_coroutines_flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * EJERCICIO: Flow fundamentals
 *
 * OBJETIVO: Flow es cold stream: cada collect() dispara el producer.
 * `emit(value)` produce un valor.
 *
 * INSTRUCCIONES:
 * 1. `numerosFlow(n: Int): Flow<Int>` retorna un flow que emite
 *    los números de 1 a n con `delay(50)` entre cada uno.
 * 2. `sumarFlow(n: Int): Int` consume el flow y retorna la suma.
 */
fun numerosFlow(n: Int): Flow<Int> = TODO()
suspend fun sumarFlow(n: Int): Int = TODO()
```

`.../_17_coroutines_flow/E01_flow_basicsTest.kt`:

```kotlin
package cl.duoc.exercises._17_coroutines_flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_flow_basicsTest {
    @Test fun `suma flow`() = runTest {
        assertEquals(15, sumarFlow(5)) // 1+2+3+4+5
    }
}
```

- [ ] **Step 8: Crear `_18_coroutines_state_flow/E01_state_flow.kt` + test**

`.../_18_coroutines_state_flow/E01_state_flow.kt`:

```kotlin
package cl.duoc.exercises._18_coroutines_state_flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * EJERCICIO: StateFlow y SharedFlow
 *
 * OBJETIVO: StateFlow es hot stream con un valor inicial; los nuevos
 * collectors reciben el último valor (conflated). Perfecto para UI state.
 *
 * INSTRUCCIONES:
 * 1. Implementa `Contador` con un `MutableStateFlow<Int>` interno y
 *    métodos `incrementar()`, `valor: StateFlow<Int>`.
 */
class Contador {
    private val _valor = MutableStateFlow(0)
    val valor: StateFlow<Int> = _valor.asStateFlow()
    fun incrementar() = TODO()
}
```

`.../_18_coroutines_state_flow/E01_state_flowTest.kt`:

```kotlin
package cl.duoc.exercises._18_coroutines_state_flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_state_flowTest {
    @Test fun `incrementar`() = runTest {
        val c = Contador()
        assertEquals(0, c.valor.value)
        c.incrementar()
        c.incrementar()
        c.incrementar()
        assertEquals(3, c.valor.value)
    }
}
```

- [ ] **Step 9: Crear `_19_coroutines_error_handling/E01_catch.kt` + test**

`.../_19_coroutines_error_handling/E01_catch.kt`:

```kotlin
package cl.duoc.exercises._19_coroutines_error_handling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * EJERCICIO: Manejo de errores en coroutines y Flow
 *
 * OBJETIVO: En launch, try/catch dentro de la coroutine.
 * En async, await() lanza. En Flow, usar `catch` operator.
 *
 * INSTRUCCIONES:
 * 1. `flowConError(): Flow<Int>` emite 1, 2, y luego lanza RuntimeException("boom").
 * 2. `flowSeguro(): Flow<String>` consume flowConError y usa `.catch`
 *    para emitir "error" en lugar de propagar la excepción.
 */
fun flowConError(): Flow<Int> = TODO()
fun flowSeguro(): Flow<String> = TODO()
```

`.../_19_coroutines_error_handling/E01_catchTest.kt`:

```kotlin
package cl.duoc.exercises._19_coroutines_error_handling
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
class E01_catchTest {
    @Test fun `catch recupera`() = runTest {
        val result = flowSeguro().toList()
        assertEquals(listOf("1", "2", "error"), result)
    }
}
```

- [ ] **Step 10: Verificar y commit**

```bash
cd exercises && ./gradlew compileKotlin compileTestKotlin 2>&1 | tail -10
git add exercises/
git commit -m "feat: bloque 4 - Coroutines en profundidad (6 ejercicios)"
```

---

### Task 15: Bloque 5 — Arquitectura de Software (6 cápsulas "por qué")

**Files:** (6 archivos .kt en main, sin tests propios — la verificación es que el alumno lea la cápsula y ejecute el main() de cada archivo)

- `_20_arquitectura_separacion/E01_separar_ui_logica.kt`
- `_21_arquitectura_mvvm_vs_mvc/E01_mvvm_vs_mvc.kt`
- `_22_arquitectura_repository/E01_repository.kt`
- `_23_arquitectura_udf/E01_udf.kt`
- `_24_arquitectura_di_manual/E01_di_manual.kt`
- `_25_arquitectura_sealed_state/E01_sealed_state.kt`

- [ ] **Step 1: Crear directorios**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo/exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p _20_arquitectura_separacion _21_arquitectura_mvvm_vs_mvc _22_arquitectura_repository _23_arquitectura_udf _24_arquitectura_di_manual _25_arquitectura_sealed_state
```

(No creamos directorios en test/ porque estos archivos no llevan test JUnit — son cápsulas que se ejecutan vía `main()` y se leen.)

- [ ] **Step 2: Crear `_20_arquitectura_separacion/E01_separar_ui_logica.kt`**

```kotlin
package cl.duoc.exercises._20_arquitectura_separacion

// ============================================================
// POR QUÉ separar UI de lógica
// ============================================================
// Una pantalla que mezcla UI con lógica de negocio es:
//   - Difícil de testear (necesitas emulador o Robolectric).
//   - Imposible de reusar (la lógica vive atada a la pantalla).
//   - Frágil: refactors de UI rompen reglas de negocio.
// La solución: la pantalla emite eventos y observa estado desde un
// ViewModel. La pantalla no sabe cómo se computa el estado.
//
// DEMO: contador mal hecho vs bien hecho. Ejecuta `main()`.

// --- ❌ MAL: lógica en el composable (esquemático) ---
// @Composable
// fun ContadorMalHecho() {
//     var count by remember { mutableStateOf(0) }
//     Button(onClick = {
//         count++
//         if (count == 10) println("🎉")  // regla de negocio en UI
//     }) { Text("\$count") }
// }

// --- ✅ BIEN: composable pasivo + ViewModel ---
class CounterViewModel {
    var count = 0; private set
    fun increment() {
        count++
        if (count == 10) println("🎉")  // regla testeable
    }
}

// @Composable
// fun ContadorBienHecho(vm: CounterViewModel) {
//     Button(onClick = vm::increment) { Text("\${vm.count}") }
// }

fun main() {
    val vm = CounterViewModel()
    repeat(11) { vm.increment() }
    println("count final: \${vm.count}")
}
```

- [ ] **Step 3: Crear `_21_arquitectura_mvvm_vs_mvc/E01_mvvm_vs_mvc.kt`**

```kotlin
package cl.duoc.exercises._21_arquitectura_mvvm_vs_mvc

// ============================================================
// POR QUÉ MVVM y no MVC
// ============================================================
// MVC clásico (web): el Controller recibe requests, modifica el Model,
// elige la View. La View puede leer el Model directamente.
// MVVM: la View solo observa el ViewModel. La View es pasiva.
// Diferencia clave: en MVVM, el sentido de dependencias va
// View -> ViewModel -> Model. La View NUNCA toca el Model.
//
// DEMO: TaskList con MVC vs MVVM.

data class Task(val id: Int, val titulo: String)

// --- MVC: View lee Model directamente ---
class TaskListModelMVC {
    private val tasks = mutableListOf<Task>()
    fun add(task: Task) { tasks.add(task) }
    fun getAll(): List<Task> = tasks
}

// --- MVVM: ViewModel expone StateFlow; View solo observa ---
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskListViewModel {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    fun add(task: Task) { _tasks.value = _tasks.value + task }
}

fun main() {
    println("=== MVC ===")
    val mvc = TaskListModelMVC()
    mvc.add(Task(1, "Aprender MVC"))
    println(mvc.getAll())

    println("=== MVVM ===")
    val mvvm = TaskListViewModel()
    mvvm.add(Task(1, "Aprender MVVM"))
    println(mvvm.tasks.value)
}
```

- [ ] **Step 4: Crear `_22_arquitectura_repository/E01_repository.kt`**

```kotlin
package cl.duoc.exercises._22_arquitectura_repository

// ============================================================
// POR QUÉ Repository Pattern
// ============================================================
// Una pantalla que carga datos no debería saber si vienen de:
//   - una API REST
//   - una base de datos local
//   - un fake en memoria para tests
// Repository encapsula la fuente. La UI pide "dame los items";
// el Repository decide de dónde sacarlos.
//
// Sin Repository: ViewModel -> API (acoplado).
// Con Repository: ViewModel -> Repository -> API/DB/Fake.

interface ItemRepository {
    suspend fun getItems(): List<String>
}

class FakeItemRepository : ItemRepository {
    override suspend fun getItems(): List<String> = listOf("fake-1", "fake-2")
}

class ApiItemRepository : ItemRepository {
    override suspend fun getItems(): List<String> {
        // llamada HTTP real
        return listOf("api-1", "api-2")
    }
}

class ItemViewModel(private val repo: ItemRepository) {
    suspend fun load(): List<String> = repo.getItems()
}

fun main() {
    // En tests: pasamos Fake. En prod: pasamos Api.
    val vm = ItemViewModel(FakeItemRepository())
    println(vm.load())  // [fake-1, fake-2]
}
```

- [ ] **Step 5: Crear `_23_arquitectura_udf/E01_udf.kt`**

```kotlin
package cl.duoc.exercises._23_arquitectura_udf

// ============================================================
// POR QUÉ Unidirectional Data Flow (UDF)
// ============================================================
// Flujo de datos en una sola dirección: View -> ViewModel (eventos),
// ViewModel -> View (estado). La View no modifica el estado directamente.
// Por qué:
//   - Testeable: dado un evento, espero un estado.
//   - Predecible: el estado solo cambia cuando el ViewModel lo emite.
//   - Sin "fuentes de verdad" múltiples.
//
// DEMO: contador bidireccional vs UDF.

class ContadorBidireccional {
    var count = 0  // mutable, accesible desde la View
    fun increment() { count++ }
}

// En UDF el estado está en el ViewModel y la View solo lo lee.

fun main() {
    val c = ContadorBidireccional()
    c.count = 100  // la View puede meter la mano: mal
    c.increment()
    println(c.count)  // 101
}
```

- [ ] **Step 6: Crear `_24_arquitectura_di_manual/E01_di_manual.kt`**

```kotlin
package cl.duoc.exercises._24_arquitectura_di_manual

// ============================================================
// POR QUÉ Inyección de Dependencias (manual)
// ============================================================
// DI manual = pasar las dependencias por constructor, no crearlas dentro.
// Por qué:
//   - Testeable: en tests pasas un fake; en prod, el real.
//   - Transparente: miras la firma del constructor y sabes qué necesita.
//   - Sin frameworks mágicos: Koin/Hilt son opcionales; empezar manual.
//
// DEMO: ViewModel que crea sus deps vs ViewModel que las recibe.

class ApiClient { fun ping(): Boolean = true }

class BadViewModel {
    private val api = ApiClient()  // acoplado
    fun load() = api.ping()
}

class GoodViewModel(private val api: ApiClient) {
    fun load() = api.ping()
}

fun main() {
    val api = ApiClient()
    val vm = GoodViewModel(api)
    println(vm.load())
}
```

- [ ] **Step 7: Crear `_25_arquitectura_sealed_state/E01_sealed_state.kt`**

```kotlin
package cl.duoc.exercises._25_arquitectura_sealed_state

// ============================================================
// POR QUÉ sealed class para UI state
// ============================================================
// Una pantalla tiene estados discretos: Loading, Success(data), Error(msg).
// Modelarlos como String ("loading"/"ok"/"error") es propenso a typos.
// Modelarlos como sealed class obliga al compilador a chequear
// exhaustividad en when.

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<String>) : UiState()
    data class Error(val message: String) : UiState()
}

fun render(state: UiState): String = when (state) {
    isUiState.Loading -> "Cargando…"
    isUiState.Success -> "Datos: ${state.data}"
    isUiState.Error -> "Error: ${state.message}"
}
// Si agregas una nueva subclase y olvidas el caso, el compilador falla.

fun main() {
    println(render(UiState.Loading))
    println(render(UiState.Success(listOf("a", "b"))))
    println(render(UiState.Error("timeout")))
}
```

- [ ] **Step 8: Verificar compilación y commit**

```bash
cd exercises && ./gradlew compileKotlin 2>&1 | tail -10
git add exercises/
git commit -m "feat: bloque 5 - Arquitectura de Software (6 cápsulas)"
```

---### Task 16: Bloque 6 — Bridge a Android (4 ejercicios) + kata integrador

**Files:**
- `_26_android_compose_preview/E01_compose_basics.kt`
- `_27_android_viewmodel/E01_viewmodel.kt`
- `_28_android_networking/E01_retrofit.kt`
- `_29_android_persistence/E01_room.kt`
- `_30_kata_integrador/E01_csv_parser.kt`

- [ ] **Step 1: Crear directorios**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo/exercises/src/main/kotlin/cl/duoc/exercises
mkdir -p _26_android_compose_preview _27_android_viewmodel _28_android_networking _29_android_persistence _30_kata_integrador
```

(Estos 5 archivos son solo lectura/esquemáticos: no se ejecutan como main() porque requieren Android. Se documenta con comentarios al inicio.)

- [ ] **Step 2: Crear `_26_android_compose_preview/E01_compose_basics.kt`**

```kotlin
package cl.duoc.exercises._26_android_compose_preview

// ============================================================
// BRIDGE: Conceptos Compose (solo lectura)
// ============================================================
// Este archivo NO se ejecuta porque requiere AndroidX.
// Su propósito es mostrar cómo los conceptos de Kotlin que ya
// practicaste se traducen a Compose.

/*
@Composable
fun SaludoComposable() {
    // remember + mutableStateOf = estado local que sobrevive recomposiciones
    var nombre by remember { mutableStateOf("Mundo") }

    // LaunchedEffect = coroutine que arranca al primer compose y se cancela al salir
    LaunchedEffect(Unit) {
        delay(1000)
        nombre = "Compose"
    }

    Column {
        Text("Hola, \$nombre!")
        Button(onClick = { nombre = "click!" }) { Text("Cambiar") }
    }
}

// Equivalencias:
//   val nombre = "Ana"           -> remember { mutableStateOf("Ana") }
//   Thread.sleep(1000)            -> delay(1000) en LaunchedEffect
//   .setText() / .setOnClickListener -> recomposición declarativa
*/
```

- [ ] **Step 3: Crear `_27_android_viewmodel/E01_viewmodel.kt`**

```kotlin
package cl.duoc.exercises._27_android_viewmodel

// ============================================================
// BRIDGE: ViewModel en Android (solo lectura)
// ============================================================

/*
// ViewModel sobrevive a rotación. Se atacha a un ViewModelStoreOwner.
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    fun increment() { _count.value++ }
}

// En el composable, obtienes la instancia con viewModel():
@Composable
fun CounterScreen(vm: CounterViewModel = viewModel()) {
    val count by vm.count.collectAsState()
    Button(onClick = vm::increment) { Text("\$count") }
}

// viewModelScope se cancela automáticamente cuando el VM se destruye.
class MyViewModel : ViewModel() {
    fun load() {
        viewModelScope.launch {
            val data = api.fetch()
            _state.value = data
        }
    }
}
*/
```

- [ ] **Step 4: Crear `_28_android_networking/E01_retrofit.kt`**

```kotlin
package cl.duoc.exercises._28_android_networking

// ============================================================
// BRIDGE: Networking con Retrofit (solo lectura)
// ============================================================

/*
// Definir interfaz
interface PokeApi {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonDto
}

// Configurar Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(PokeApi::class.java)

// Consumir desde ViewModel
class PokeViewModel(private val api: PokeApi) : ViewModel() {
    fun load(id: Int) {
        viewModelScope.launch {
            try {
                val poke = api.getPokemon(id)
                _state.value = NetworkUiState.Success(poke)
            } catch (t: Throwable) {
                _state.value = NetworkUiState.Error(t.message ?: "Error")
            }
        }
    }
}
*/
```

- [ ] **Step 5: Crear `_29_android_persistence/E01_room.kt`**

```kotlin
package cl.duoc.exercises._29_android_persistence

// ============================================================
// BRIDGE: Room + Flow + Repository (solo lectura)
// ============================================================

/*
@Entity
data class Note(@PrimaryKey(autoGenerate = true) val id: Int = 0, val texto: String)

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note") fun all(): Flow<List<Note>>
    @Insert suspend fun insert(note: Note)
}

@Database(entities = [Note::class], version = 1)
abstract class AppDb : RoomDatabase() { abstract fun notes(): NoteDao }

class NoteRepository(private val dao: NoteDao) {
    val notes: Flow<List<Note>> = dao.all()
    suspend fun add(texto: String) = dao.insert(Note(texto = texto))
}
*/
```

- [ ] **Step 6: Crear `_30_kata_integrador/E01_csv_parser.kt` + test**

`.../_30_kata_integrador/E01_csv_parser.kt`:

```kotlin
package cl.duoc.exercises._30_kata_integrador

/**
 * EJERCICIO KATA: Parser de CSV inmutable con sealed Result
 *
 * OBJETIVO: Integrar varios temas en un solo ejercicio:
 *   - data class para modelar filas (con destructuring)
 *   - sealed class para resultados (éxito/header inválido/celda vacía)
 *   - extension functions (String.splitCsvLine)
 *   - lambdas/operaciones funcionales
 *   - null safety con Elvis
 *
 * INSTRUCCIONES:
 * 1. Implementa la data class `CsvRow(val cells: List<String>)`.
 * 2. Implementa extension `String.splitCsvLine(): List<String>` que
 *    separa por comas y descarta celdas vacías.
 * 3. Implementa `parsearCsv(texto: String): ResultadoCsv` que retorna:
 *    - Exito(rows: List<CsvRow>) si todo OK.
 *    - Error(mensaje: String) si la primera línea (header) no tiene
 *      al menos 2 columnas.
 *    Usa sealed class ResultadoCsv.
 */
data class CsvRow(val cells: List<String>)

sealed class ResultadoCsv {
    data class Exito(val rows: List<CsvRow>) : ResultadoCsv()
    data class Error(val mensaje: String) : ResultadoCsv()
}

fun String.splitCsvLine(): List<String> = TODO()

fun parsearCsv(texto: String): ResultadoCsv = TODO()
```

`.../_30_kata_integrador/E01_csv_parserTest.kt`:

```kotlin
package cl.duoc.exercises._30_kata_integrador
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
class E01_csv_parserTest {
    @Test fun `split basico`() = assertEquals(listOf("a","b","c"), "a,b,c".splitCsvLine())
    @Test fun `split con vacias`() = assertEquals(listOf("a","b"), "a,,b,".splitCsvLine())
    @Test fun `parsear ok`() {
        val csv = "nombre,edad\nAna,30\nBeto,25"
        val r = parsearCsv(csv)
        assertTrue(r is ResultadoCsv.Exito)
        r as ResultadoCsv.Exito
        assertEquals(2, r.rows.size)
        assertEquals(listOf("Ana", "30"), r.rows[0].cells)
    }
    @Test fun `header invalido`() {
        val r = parsearCsv("solo_una_columna\nAna")
        assertTrue(r is ResultadoCsv.Error)
        r as ResultadoCsv.Error
        assertEquals("Header inválido", r.mensaje)
    }
}
```

- [ ] **Step 7: Verificar compilación**

```bash
cd exercises && ./gradlew compileKotlin compileTestKotlin 2>&1 | tail -10
```
Esperado: `BUILD SUCCESSFUL`.

- [ ] **Step 8: Commit**

```bash
git add exercises/
git commit -m "feat: bloque 6 - bridge a Android (4) + kata integrador"
```

---

## Fase 5 — Documentación

### Task 17: docs/08-Guia-Capacidades-Nativas.md

**Files:**
- Create: `docs/08-Guia-Capacidades-Nativas.md`

- [ ] **Step 1: Crear el archivo**

Escribir `docs/08-Guia-Capacidades-Nativas.md`:

```markdown
# 08 — Capacidades Nativas de Android

Guía de las pantallas nativas incluidas en este proyecto y los permisos
requeridos para cada una.

## Pantallas disponibles

| Pantalla | Concepto | Permisos requeridos |
|----------|----------|---------------------|
| `AccelerometerScreen` | Sensores de movimiento | Ninguno |
| `BatteryScreen` | Estado de la batería | Ninguno |
| `BiometricScreen` | Autenticación biométrica | `USE_BIOMETRIC` |
| `CameraScreen` | Captura de fotos | `CAMERA` |
| `FlashlightScreen` | Linterna | `FLASHLIGHT` |
| `LocalStorageScreen` | Archivos locales | `READ/WRITE_EXTERNAL_STORAGE` (legacy) |
| `LocationScreen` | GPS / ubicación | `ACCESS_FINE_LOCATION` |
| `NotificationsScreen` | Notificaciones | `POST_NOTIFICATIONS` (Android 13+) |
| `VibrationScreen` | Vibración | `VIBRATE` |

## Cómo probar cada pantalla

Las pantallas están en el menú raíz → "Capacidades Nativas". Toca cada
una para ver su demo. Algunos sensores solo están disponibles en
dispositivos físicos (acelerómetro, GPS); en el emulador algunos
devuelven valores simulados.

## Notas por pantalla

### Biometría
- Requiere dispositivo con huella o reconocimiento facial configurado.
- En el emulador: Settings → Security → Fingerprint → Enroll.

### Cámara
- Usa CameraView de Jetpack (sin permisos en runtime adicionales a CAMERA).
- En emulador: la cámara virtual está disponible.

### GPS
- En emulador: Extended Controls → Location → set lat/lon.
- En dispositivo: habilitar ubicación.

### Notificaciones
- Android 13+ requiere pedir permiso POST_NOTIFICATIONS en runtime.
- La app ya lo solicita la primera vez que entras a la pantalla.

## Permisos del Manifest

`AndroidManifest.xml` ya incluye:

```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.USE_BIOMETRIC" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.FLASHLIGHT" />
```

## Recursos adicionales

- [Documentación de permisos Android](https://developer.android.com/guide/topics/permissions/overview)
- [Jetpack CameraX](https://developer.android.com/training/camerax)
- [Android Biometric](https://developer.android.com/training/sign-in/biometric)
```

- [ ] **Step 2: Commit**

```bash
git add docs/08-Guia-Capacidades-Nativas.md
git commit -m "docs: añadir guía 08 - Capacidades Nativas"
```

---

### Task 18: docs/09-Guia-Ejercicios-Kotlin.md

**Files:**
- Create: `docs/09-Guia-Ejercicios-Kotlin.md`

- [ ] **Step 1: Crear el archivo**

Escribir `docs/09-Guia-Ejercicios-Kotlin.md`:

```markdown
# 09 — Guía de Ejercicios Kotlin

Los 33 ejercicios del módulo `exercises/` están diseñados para progresar
de Kotlin básico a Android. Esta guía explica cómo correrlos, cómo están
organizados y cómo aprovechar al máximo el aprendizaje.

## Cómo correr

Desde la carpeta `exercises/`:

```bash
cd exercises
./gradlew test                            # todos los tests (muchos fallarán — esperado)
./gradlew test --tests "*E01*"            # un ejercicio específico
./gradlew test --tests "*_14_*"           # todos los de un bloque (coroutines basic)
./gradlew test --tests "*_20_*"           # todos los de un bloque (arquitectura)
```

Los tests fallan hasta que implementas la función. **Esto es esperado**:
el ciclo es: leer el enunciado → implementar → correr el test → iterar.

No necesitas Android Studio, emulador ni un dispositivo. Es un proyecto
JVM puro.

## Mapa de bloques

| # | Bloque | Carpetas | Ejercicios | Dificultad |
|---|--------|----------|-----------:|-----------:|
| 1 | Kotlin básico | `_00_` a `_05_` | 7 | 🟢 |
| 2 | Kotlin intermedio | `_06_` a `_09_` | 5 | 🟡 |
| 3 | Kotlin avanzado | `_10_` a `_13_` | 4 | 🔴 |
| 4 | Coroutines | `_14_` a `_19_` | 6 | 🔴 |
| 5 | Arquitectura | `_20_` a `_25_` | 6 | 🟡 |
| 6 | Bridge Android | `_26_` a `_29_` | 4 | 🟢 (lectura) |
| 7 | Kata integrador | `_30_` | 1 | 🔴 |

Total: 33 ejercicios.

## Ruta sugerida

1. **Bloque 1 → 3** (semanas 1-3): dominar el lenguaje.
2. **Bloque 4 — Coroutines** (semanas 4-5): corazón de Android moderno.
3. **Bloque 5 — Arquitectura** (semana 6): leer cada cápsula y ejecutar su `main()`.
4. **Bloque 6** (lectura, semana 7): conectar lo anterior con Android.
5. **Kata integrador** (semana 8): integrar todo.

## Convención por ejercicio

Cada `.kt` de ejercicio sigue este formato:

```kotlin
package cl.duoc.exercises._XX_tema

/**
 * EJERCICIO: <título>
 *
 * OBJETIVO: <qué>
 * POR QUÉ IMPORTA: <motivación>
 * INSTRUCCIONES: <pasos>
 * DIFICULTAD: 🟢 / 🟡 / 🔴
 * TEST: ./gradlew :test --tests "*<id>*"
 */
fun <nombre>(<args>): <ReturnType> = TODO()
```

El `TODO()` es un placeholder que lanza `NotImplementedError` al invocarse.
Reemplázalo por tu implementación. El test te dice cuándo vas bien.

## Glosario

- **`TODO()`**: marca de "implementar aquí". Lanza si lo ejecutas.
- **`suspend fun`**: función que puede suspenderse sin bloquear el thread.
- **`Flow`**: stream de valores cold (cada collect() arranca el producer).
- **`StateFlow`**: stream hot con un valor actual; perfecto para UI state.
- **Sealed class**: jerarquía cerrada; el compilador chequea exhaustividad.
- **Repository**: encapsula la fuente de datos para no acoplar UI a API/DB.

## Recursos adicionales

- [Kotlin Docs](https://kotlinlang.org/docs/home.html)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
- [Android Architecture Guide](https://developer.android.com/topic/architecture)
```

- [ ] **Step 2: Commit**

```bash
git add docs/09-Guia-Ejercicios-Kotlin.md
git commit -m "docs: añadir guía 09 - Ejercicios Kotlin"
```

---

### Task 19: docs/10-Guia-Ejercicios-Android.md

**Files:**
- Create: `docs/10-Guia-Ejercicios-Android.md`

- [ ] **Step 1: Crear el archivo**

Escribir `docs/10-Guia-Ejercicios-Android.md`:

```markdown
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
```

- [ ] **Step 2: Commit**

```bash
git add docs/10-Guia-Ejercicios-Android.md
git commit -m "docs: añadir guía 10 - Ejercicios Android"
```

---

### Task 20: docs/11-Principios-de-Arquitectura.md

**Files:**
- Create: `docs/11-Principios-de-Arquitectura.md`

- [ ] **Step 1: Crear el archivo**

Escribir `docs/11-Principios-de-Arquitectura.md`:

```markdown
# 11 — Principios de Arquitectura

Compendio escrito de los principios detrás de las 6 cápsulas de
Arquitectura en `exercises/_20_` a `_25_`. Cada cápsula tiene su `.kt`
ejecutable; este documento los explica con más profundidad.

## 1. Separar UI de lógica

**Principio**: el composable no debe contener reglas de negocio. Debe
emitir eventos y observar estado.

**Por qué**:
- Testeable sin emulador.
- Lógica reutilizable en otras pantallas.
- Refactors de UI no rompen reglas.

**Cómo se ve en código**: ViewModel con `MutableStateFlow` +
`collectAsState()` en el composable.

## 2. MVVM vs MVC

**Diferencia clave**: en MVVM, la View es pasiva y solo observa el
ViewModel. En MVC clásico, la View puede leer el Model directamente.

**Por qué importa**: en MVVM el sentido de dependencias es siempre
View → ViewModel → Model. Es más fácil de razonar.

## 3. Repository Pattern

**Principio**: la UI pide "dame los items" sin saber de dónde vienen.

**Por qué**:
- Cambiar la fuente (API ↔ DB ↔ fake) sin tocar UI ni ViewModel.
- Testear con un fake.
- Punto único para caché, retry, logging.

**Cuándo NO**: si solo hay una fuente y no planeas testear, agregar
Repository es sobreingeniería.

## 4. Unidirectional Data Flow (UDF)

**Principio**: View → ViewModel (eventos), ViewModel → View (estado).
La View no modifica el estado directamente.

**Por qué**:
- Estado siempre en un solo lugar.
- Testeable: dado un evento, espero un estado.
- Sin "fuentes de verdad" múltiples.

**Antipatrón**: variables `var count` en el composable que la View y
la lógica modifican a la vez. Eso es bidireccional y propenso a bugs.

## 5. Inyección de dependencias (manual)

**Principio**: las dependencias se pasan por constructor, no se crean
dentro.

**Por qué**:
- Testeable: en tests pasas un fake.
- Transparente: la firma del constructor documenta las necesidades.
- Sin magia: no necesitas Koin/Hilt para empezar.

**Cuándo SÍ frameworks**: cuando el grafo crece (10+ clases), la
inyección manual se vuelve boilerplate. Ahí entra Hilt.

## 6. Sealed class para UI state

**Principio**: los estados discretos de una pantalla se modelan con
sealed class.

**Por qué**:
- El compilador obliga a manejar todos los casos en `when`.
- Refactors seguros: agregar un estado nuevo rompe el código que no
  lo maneje, y eso es bueno.

**Antipatrón**: modelar estado como String ("loading"/"ok"/"error").
Typos silenciosos, sin chequeo de exhaustividad.

## Resumen

| Principio | Antipatrón |
|-----------|-----------|
| UI sin lógica | Lógica en el composable |
| MVVM | View lee Model directamente |
| Repository | ViewModel conoce la API |
| UDF | Estado mutable bidireccional |
| DI manual | `new ApiClient()` dentro |
| Sealed state | Strings para estados |

Estos 6 principios son la base de cualquier app Android medianamente
compleja. Si entiendes el "por qué" de cada uno, no necesitas memorizar
el "qué".
```

- [ ] **Step 2: Commit**

```bash
git add docs/11-Principios-de-Arquitectura.md
git commit -m "docs: añadir guía 11 - Principios de Arquitectura"
```

---

### Task 21: Actualizar docs/README.md y README.md raíz

**Files:**
- Modify: `docs/README.md`
- Modify: `README.md`

- [ ] **Step 1: Reemplazar `docs/README.md`**

Sobrescribe `docs/README.md` con:

```markdown
# Documentación — Desarrollo Android con Kotlin y Jetpack Compose

Esta carpeta contiene guías completas para aprender desarrollo de
aplicaciones Android modernas, desde Kotlin básico hasta publicación.

## 📚 Índice de Contenidos

### Kotlin puro (módulo `exercises/`)

- **[00 — Fundamentos de Kotlin](00-Fundamentos-Kotlin.md)** — conceptos del lenguaje
- **[09 — Guía de Ejercicios Kotlin](09-Guia-Ejercicios-Kotlin.md)** — los 33 ejercicios, cómo correrlos, ruta

### Android (app)

- **[01 — Composables](01-Composables.md)** — Jetpack Compose y componentes UI
- **[02 — Navigation](02-Navigation.md)** — sistema de navegación
- **[03 — MVVM y Repository](03-MVVM-y-Repository.md)** — arquitectura
- **[04 — Conectando PokeAPI a Datos](04-conectando-pokeapi-a-datos.md)** — networking

### Distribución y personalización

- **[05 — Empaquetado y Distribución](05-Empaquetado-y-Distribucion.md)** — APK y Google Play
- **[06 — Personalización de la App](06-Personalizacion-de-la-App.md)** — nombre, ícono, splash

### Catálogos visuales

- **[07 — Componentes UI Material Design](07-Componentes-UI-Material-Design.md)** — showcase de componentes
- **[08 — Capacidades Nativas Android](08-Guia-Capacidades-Nativas.md)** — biometría, cámara, GPS, etc.

### Ejercicios integradores

- **[10 — Guía de Ejercicios Android](10-Guia-Ejercicios-Android.md)** — 8 mini-apps dentro de la app
- **[11 — Principios de Arquitectura](11-Principios-de-Arquitectura.md)** — el "por qué" detrás de las decisiones

---

## 🎯 Ruta de Aprendizaje Recomendada

| Semana | Nivel | Recursos |
|--------|-------|----------|
| 1-2 | Kotlin básico | `exercises/_00_` a `_05_` + [00](00-Fundamentos-Kotlin.md) |
| 3 | Kotlin intermedio | `exercises/_06_` a `_09_` |
| 4 | Kotlin avanzado | `exercises/_10_` a `_13_` |
| 5-6 | Coroutines | `exercises/_14_` a `_19_` |
| 7 | Arquitectura | `exercises/_20_` a `_25_` + [11](11-Principios-de-Arquitectura.md) |
| 8-9 | Bridge Android | `exercises/_26_` a `_30_` |
| 10 | Compose | app sección Material + [01](01-Composables.md) |
| 11 | Navegación | app sección Material (NavigationScreen) + [02](02-Navigation.md) |
| 12-13 | Arquitectura Android | app sección Ejercicios (mini-apps 5-7) + [03](03-MVVM-y-Repository.md) |
| 14 | Capacidades nativas | app sección Nativas + [08](08-Guia-Capacidades-Nativas.md) |
| 15 | Distribución | [05](05-Empaquetado-y-Distribucion.md) + [06](06-Personalizacion-de-la-App.md) |

---

## 🚀 Cómo empezar

### Si nunca programaste en Kotlin

1. Lee [00 — Fundamentos de Kotlin](00-Fundamentos-Kotlin.md).
2. Abre la carpeta `exercises/` y corre `./gradlew test`.
3. Verás que muchos tests fallan — eso es porque son ejercicios por hacer.
4. Empieza por `_00_variables/E01_var_val.kt`, implementa `sumar`, corre el test.

### Si ya sabes Kotlin

1. Lee [01 — Composables](01-Composables.md).
2. Abre la app en Android Studio.
3. Toca "🧪 Ejercicios Android" y resuelve las mini-apps en orden.

### Si quieres ver componentes

1. Abre la app.
2. Toca "🎨 Componentes Material" para ver el catálogo visual.
3. Lee [07](07-Componentes-UI-Material-Design.md) para profundizar.

### Si quieres ver capacidades nativas

1. Abre la app.
2. Toca "📱 Capacidades Nativas".
3. Lee [08](08-Guia-Capacidades-Nativas.md) para los permisos y consideraciones.

---

## 📞 Soporte

- [Documentación oficial de Android](https://developer.android.com/)
- [Kotlin Docs](https://kotlinlang.org/docs/home.html)
- [Jetpack Compose Pathway](https://developer.android.com/courses/pathways/compose)

---

**Última actualización**: ver `git log` del repo.
```

- [ ] **Step 2: Reemplazar `README.md` raíz**

Sobrescribe `README.md` con:

```markdown
# Duoc Clase Móvil — Recursos de Estudio Android

Proyecto Android con Kotlin y Jetpack Compose, consolidado como recurso
de estudio progresivo (de Kotlin básico a Android intermedio).

## ✨ Contenido

- 🎨 **Componentes Material Design** — catálogo visual de los componentes
  Material 3 (botones, cards, dialogs, listas, theming, navigation, etc.).
- 📱 **Capacidades Nativas** — pantallas de demostración de biometría,
  cámara, linterna, GPS, sensores, notificaciones, almacenamiento local,
  vibración.
- 🧪 **Ejercicios Android** — 8 mini-apps para practicar Compose,
  ViewModel, Repository, networking.
- 📚 **Ejercicios Kotlin** (módulo hermano `exercises/`) — 33 ejercicios
  Kotlin JVM progresivos de básico a avanzado + coroutines + arquitectura.

## 🗺️ Estructura del repo

```
duoc-clase-movil-repo/
├── app/                              # Android (Compose + Material + Nativo + Ejercicios)
│   └── src/main/java/com/example/baseproject/
│       ├── MainActivity.kt           # aloja RootNav
│       └── ui/
│           ├── screens/{RootMenuScreen, material/, native/, exercises/}
│           ├── navigation/{RootNav, MaterialNav, NativeNav, ExercisesNav}
│           └── theme/
├── exercises/                        # proyecto JVM hermano (Gradle independiente)
│   ├── build.gradle.kts
│   └── src/{main,test}/kotlin/cl/duoc/exercises/
│       ├── _00_variables/ ... _13_exception_handling/   # Kotlin
│       ├── _14_... _19_coroutines_*/                     # Coroutines (6)
│       ├── _20_... _25_arquitectura_*/                   # Arquitectura (6)
│       └── _26_... _29_android_*/ + _30_kata_integrador  # Bridge + kata
├── docs/
│   ├── 00-Fundamentos-Kotlin.md  → 11-Principios-de-Arquitectura.md
│   ├── books/   # gitignored (epubs de referencia)
│   └── README.md   # índice actualizado
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 🚀 Cómo empezar

### Requisitos

- Android Studio Hedgehog (2023.1.1) o posterior.
- JDK 17+ (para el módulo `exercises/`).
- SDK mínimo 24, target 34.

### Clonar y abrir

```bash
git clone <url>
cd duoc-clase-movil-repo
# Abre la carpeta en Android Studio.
```

### Correr la app

Con un emulador o dispositivo conectado:

1. Sincroniza Gradle.
2. Run → Run 'app'.
3. La app abre en el menú raíz con 3 secciones.

### Correr los ejercicios Kotlin

```bash
cd exercises
./gradlew test
```

Los tests fallarán hasta que implementes las funciones — eso es parte
del aprendizaje. Ver [docs/09-Guia-Ejercicios-Kotlin.md](docs/09-Guia-Ejercicios-Kotlin.md).

## 📖 Rutas de aprendizaje

- **Si nunca programaste**: [docs/README.md](docs/README.md) tiene la
  ruta detallada semana por semana.
- **Si ya sabes Kotlin**: ve directo a [docs/01-Composables.md](docs/01-Composables.md)
  y a las mini-apps dentro de la app.

## 🤝 Contribuir

- Issues y PRs bienvenidos.
- Nuevas mini-apps: crea un paquete en `app/src/main/java/.../exercises/`
  y registra la ruta en `ExercisesNav.kt`.
- Nuevos ejercicios Kotlin: crea un paquete en `exercises/src/main/kotlin/...`
  con su test hermano.

## 📜 Licencia

Plantilla de uso libre para fines educativos.
```

- [ ] **Step 3: Commit**

```bash
git add docs/README.md README.md
git commit -m "docs: actualizar README raíz y docs/README con nueva ruta"
```

---

## Fase 6 — Push y limpieza

### Task 22: Push final y limpieza de branches remotas

- [ ] **Step 1: Verificar compilación completa**

```bash
cd /home/sebastian/orca/duoc-clase-movil-repo
./gradlew :app:assembleDebug 2>&1 | tail -5
cd exercises && ./gradlew compileKotlin compileTestKotlin 2>&1 | tail -5
cd ..
```

Esperado: ambos terminan con `BUILD SUCCESSFUL`.

- [ ] **Step 2: Verificar que no quedan epubs sin trackear**

```bash
git ls-files | grep -i epub
```
Esperado: ningún resultado (epubs están gitignored).

- [ ] **Step 3: Verificar el log de commits**

```bash
git log --oneline -25
```
Esperado: serie de commits ordenados correspondientes a las 22 tasks previas. Los mensajes de commit deben estar en español y ser atómicos.

- [ ] **Step 4: Push a origin/main**

⚠️ **Confirmar con el usuario antes de ejecutar este paso** — el push es una acción saliente y debe ser aprobada explícitamente.

(Si el usuario aprueba:)
```bash
git push origin main
```

- [ ] **Step 5: Borrar branches remotas antiguas**

⚠️ **Confirmar con el usuario antes de ejecutar este paso** — el borrado de branches remotas no es reversible desde el repo local.

(Si el usuario aprueba:)
```bash
git push origin :feature/material-showcase
git push origin :native-capabilities-show
```

- [ ] **Step 6: Verificar el estado final**

```bash
git branch -a
git remote -v
```
Esperado: solo `main` local y `origin/main` remoto. Las branches `feature/material-showcase` y `native-capabilities-show` ya no existen.

- [ ] **Step 7: Commit final de housekeeping (si es necesario)**

Si hubo cambios en `.gitignore` adicionales o ajustes menores:
```bash
git status
# Si hay algo:
git add -A
git commit -m "chore: limpieza final post-push"
git push origin main
```

- [ ] **Step 8: Tag de release opcional**

```bash
git tag -a v1.0.0-unified -m "Versión unificada: Material + Nativas + Ejercicios Kotlin"
git push origin v1.0.0-unified
```

(Solo si el usuario quiere marcar la versión. Confirmar primero.)

---

## Verificación final del plan (self-review)

| Spec requirement | Task que lo implementa |
|---|---|
| Un solo branch `main` con todo | Tasks 2-3 (merges) |
| Borrar branches remotas | Task 22 |
| Epubs a `docs/books/` + gitignored | Task 1 |
| Renombrar a `material/` y `native/` | Task 4 |
| Menú raíz + RootNav | Task 5 |
| 8 mini-apps Android | Tasks 6-9 |
| Módulo `exercises/` (JVM) | Task 10 |
| 33 ejercicios Kotlin | Tasks 11-16 |
| Coroutines expandido (6) | Task 14 |
| Cápsulas de Arquitectura (6) | Task 15 |
| Docs 08-11 | Tasks 17-20 |
| README actualizado | Task 21 |
| Push final | Task 22 |

**Total de tasks**: 22
**Total de archivos nuevos**: ~125 (8 mini-apps × 2-9 archivos + 33 ejercicios × 2 + 33 tests + 4 docs + 7 archivos Gradle de exercises/ + 1-2 archivos de navegación + `RootMenuScreen` + `MiniAppScaffold` + `CodeBlock` + `ExercisesMenuScreen`).
**Total de archivos modificados**: ~10 (build.gradle.kts, AndroidManifest.xml, MainActivity.kt, Theme.kt, strings.xml, WelcomeScreen.kt eliminado, .gitignore, README raíz, docs/README, varios `*Nav.kt`).