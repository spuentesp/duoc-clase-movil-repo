package com.example.baseproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

/**
 * Pantalla raíz con tres tarjetas que dan acceso a las secciones de la app:
 *  - Componentes Material Design (showcase)
 *  - Capacidades Nativas (biometría, cámara, etc.)
 *  - Ejercicios Android (mini-apps para practicar)
 */
@Composable
fun RootMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Duoc Clase Móvil",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Recursos de estudio para desarrollo Android con Kotlin",
            style = MaterialTheme.typography.bodyMedium,
        )

        MenuCard(
            emoji = "🎨",
            titulo = "Componentes Material",
            descripcion = "Botones, cards, listas, dialogs, theming, navigation, etc. Catálogo visual de los componentes Material 3.",
            onClick = { navController.navigate("material") },
        )
        MenuCard(
            emoji = "📱",
            titulo = "Capacidades Nativas",
            descripcion = "Biometría, cámara, GPS, sensores, notificaciones, linterna, almacenamiento local, vibración.",
            onClick = { navController.navigate("native") },
        )
        MenuCard(
            emoji = "🧪",
            titulo = "Ejercicios Android",
            descripcion = "8 mini-apps para practicar: contador, listas, formularios, ViewModel, Repository, navegación, networking.",
            onClick = { navController.navigate("exercises") },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuCard(
    emoji: String,
    titulo: String,
    descripcion: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$emoji  $titulo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
