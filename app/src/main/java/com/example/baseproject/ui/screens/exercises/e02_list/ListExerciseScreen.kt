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
