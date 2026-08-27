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
        Button(onClick = vm::increment) { Text("${'$'}count") }
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
                Text("Contador: $count", fontWeight = FontWeight.Bold)
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
