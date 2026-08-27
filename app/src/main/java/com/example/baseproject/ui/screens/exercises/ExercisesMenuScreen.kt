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
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
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
