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
            Text("Contador: ${'$'}count")
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
                    Text("Contador: ${'$'}count", fontWeight = FontWeight.Bold)
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
