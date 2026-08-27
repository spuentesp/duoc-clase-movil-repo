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

