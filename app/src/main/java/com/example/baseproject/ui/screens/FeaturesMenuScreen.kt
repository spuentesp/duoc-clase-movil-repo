package com.example.baseproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.navigation.Screen
import com.example.baseproject.ui.theme.BaseAndroidProjectTheme

data class NativeFeature(
    val name: String,
    val icon: ImageVector,
    val route: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturesMenuScreen(
    onFeatureClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val features = listOf(
        NativeFeature(
            name = "Vibración",
            icon = Icons.Default.PhoneAndroid,
            route = Screen.Vibration.route,
            description = "Probar patrones de vibración"
        ),
        NativeFeature(
            name = "Almacenamiento",
            icon = Icons.Default.Storage,
            route = Screen.LocalStorage.route,
            description = "Guardar y recuperar datos localmente"
        ),
        NativeFeature(
            name = "Biométrica",
            icon = Icons.Default.Fingerprint,
            route = Screen.Biometric.route,
            description = "Autenticación por huella y rostro"
        ),
        NativeFeature(
            name = "Cámara",
            icon = Icons.Default.CameraAlt,
            route = Screen.Camera.route,
            description = "Acceder a la cámara del dispositivo"
        ),
        NativeFeature(
            name = "Linterna",
            icon = Icons.Default.FlashlightOn,
            route = Screen.Flashlight.route,
            description = "Controlar la linterna del dispositivo"
        ),
        NativeFeature(
            name = "Acelerómetro",
            icon = Icons.Default.ScreenRotation,
            route = Screen.Accelerometer.route,
            description = "Leer datos del sensor de movimiento"
        ),
        NativeFeature(
            name = "Batería",
            icon = Icons.Default.BatteryFull,
            route = Screen.Battery.route,
            description = "Ver estado y nivel de batería"
        ),
        NativeFeature(
            name = "Ubicación",
            icon = Icons.Default.LocationOn,
            route = Screen.Location.route,
            description = "Obtener coordenadas GPS"
        ),
        NativeFeature(
            name = "Notificaciones",
            icon = Icons.Default.Notifications,
            route = Screen.Notifications.route,
            description = "Enviar notificaciones locales"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Características Nativas") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(features) { feature ->
                FeatureCard(
                    feature = feature,
                    onClick = { onFeatureClick(feature.route) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureCard(
    feature: NativeFeature,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = feature.name,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = feature.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feature.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeaturesMenuScreenPreview() {
    BaseAndroidProjectTheme {
        FeaturesMenuScreen()
    }
}
