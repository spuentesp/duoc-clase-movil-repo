package com.example.baseproject.ui.screens.features

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashlightScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var isFlashlightOn by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flashlight") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Flashlight Control",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Control the device's flashlight/torch.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            Button(
                onClick = {
                    toggleFlashlight(context, !isFlashlightOn)
                    isFlashlightOn = !isFlashlightOn
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (isFlashlightOn) Icons.Default.FlashlightOff else Icons.Default.FlashlightOn,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isFlashlightOn) "Turn OFF Flashlight" else "Turn ON Flashlight")
            }

            Divider()

            Text(
                text = "Example Code:",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = """
// Get camera manager
val cameraManager = context.getSystemService(
    Context.CAMERA_SERVICE
) as CameraManager

// Get camera ID (usually "0" is back camera)
val cameraId = cameraManager.cameraIdList[0]

// Turn ON flashlight
cameraManager.setTorchMode(cameraId, true)

// Turn OFF flashlight
cameraManager.setTorchMode(cameraId, false)

// Check if flashlight is available
val hasFlash = context.packageManager
    .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
                    """.trimIndent(),
                    modifier = Modifier.padding(16.dp),
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Note:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "No special permissions required for flashlight control!",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

private fun toggleFlashlight(context: Context, turnOn: Boolean) {
    try {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]
        cameraManager.setTorchMode(cameraId, turnOn)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
