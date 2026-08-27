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

