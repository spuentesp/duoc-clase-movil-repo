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
                        items(s.items) { Text("#${it.id} ${it.nombre}") }
                    }
                    is NetworkUiState.Error -> Text("Error: ${s.message}", color = MaterialTheme.colorScheme.error)
                }
                Text("Código:", fontWeight = FontWeight.SemiBold)
                CodeBlock(codigo = SOLUCION)
            }
        },
    )
}
