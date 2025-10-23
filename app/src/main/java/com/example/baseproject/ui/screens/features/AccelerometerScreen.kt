package com.example.baseproject.ui.screens.features

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccelerometerScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var z by remember { mutableStateOf(0f) }
    var isListening by remember { mutableStateOf(false) }

    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    x = it.values[0]
                    y = it.values[1]
                    z = it.values[2]
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    DisposableEffect(isListening) {
        if (isListening && accelerometer != null) {
            sensorManager.registerListener(
                sensorListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accelerometer") },
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
                text = "Accelerometer Sensor",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Read motion and orientation sensor data.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            Button(
                onClick = { isListening = !isListening },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isListening) "Stop Reading" else "Start Reading")
            }

            if (isListening) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Accelerometer Values:",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("X: ${String.format("%.2f", x)} m/s²")
                        Text("Y: ${String.format("%.2f", y)} m/s²")
                        Text("Z: ${String.format("%.2f", z)} m/s²")
                    }
                }
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
// 1. Get sensor manager
val sensorManager = context.getSystemService(
    Context.SENSOR_SERVICE
) as SensorManager

// 2. Get accelerometer sensor
val accelerometer = sensorManager.getDefaultSensor(
    Sensor.TYPE_ACCELEROMETER
)

// 3. Create sensor listener
val listener = object : SensorEventListener {
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]  // X axis
            val y = it.values[1]  // Y axis
            val z = it.values[2]  // Z axis
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {}
}

// 4. Register listener
sensorManager.registerListener(
    listener,
    accelerometer,
    SensorManager.SENSOR_DELAY_NORMAL
)

// 5. Unregister when done
sensorManager.unregisterListener(listener)
                    """.trimIndent(),
                    modifier = Modifier.padding(16.dp),
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
