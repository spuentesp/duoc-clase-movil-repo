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
