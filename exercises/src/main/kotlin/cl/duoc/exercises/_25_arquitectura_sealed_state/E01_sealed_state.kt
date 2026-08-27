package cl.duoc.exercises._25_arquitectura_sealed_state

// ============================================================
// POR QUÉ sealed class para UI state
// ============================================================
// Una pantalla tiene estados discretos: Loading, Success(data), Error(msg).
// Modelarlos como String ("loading"/"ok"/"error") es propenso a typos.
// Modelarlos como sealed class obliga al compilador a chequear
// exhaustividad en when.
//
// DEMO: sealed class con tres estados (Loading/Success/Error) + función
// `render` con `when` exhaustivo. Ejecuta `main()`.

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<String>) : UiState()
    data class Error(val message: String) : UiState()
}

fun render(state: UiState): String = when (state) {
    is UiState.Loading -> "Cargando…"
    is UiState.Success -> "Datos: ${state.data}"
    is UiState.Error -> "Error: ${state.message}"
}
// Si agregas una nueva subclase y olvidas el caso, el compilador falla.

fun main() {
    println(render(UiState.Loading))
    println(render(UiState.Success(listOf("a", "b"))))
    println(render(UiState.Error("timeout")))
}
