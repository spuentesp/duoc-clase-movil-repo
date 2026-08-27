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
                Text("Contador: $count", fontWeight = FontWeight.Bold)
                Button(onClick = vm::increment) { Text("+") }
                Text("El valor se persiste en el InMemoryRepository.",
                    style = MaterialTheme.typography.bodySmall)
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
