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
