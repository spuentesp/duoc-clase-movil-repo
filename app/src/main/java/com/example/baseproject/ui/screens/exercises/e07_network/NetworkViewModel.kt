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
