package com.example.baseproject.ui.screens.features

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
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
fun BatteryScreen(onBackClick: () -> Unit = {}) {
    val context = LocalContext.current
    var batteryInfo by remember { mutableStateOf(getBatteryInfo(context)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estado de Batería") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
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
                text = "Información de Batería",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Monitorea el nivel de batería y estado de carga.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            Button(
                onClick = { batteryInfo = getBatteryInfo(context) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar Info de Batería")
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Estado de Batería",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Nivel: ${batteryInfo.level}%")
                    Text("Estado: ${batteryInfo.status}")
                    Text("Cargando: ${if (batteryInfo.isCharging) "Sí" else "No"}")
                    Text("Salud: ${batteryInfo.health}")
                    Text("Temperatura: ${batteryInfo.temperature}°C")
                    Text("Voltaje: ${batteryInfo.voltage}mV")
                }
            }

            Divider()

            Text(
                text = "Ejemplo de Código:",
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
// Obtener el gestor de batería
val batteryManager = context.getSystemService(
    Context.BATTERY_SERVICE
) as BatteryManager

// Obtener nivel de batería
val level = batteryManager.getIntProperty(
    BatteryManager.BATTERY_PROPERTY_CAPACITY
)

// Alternativa: Usar BroadcastReceiver
val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
val batteryStatus = context.registerReceiver(null, filter)

batteryStatus?.let { intent ->
    val level = intent.getIntExtra(
        BatteryManager.EXTRA_LEVEL, -1
    )
    val scale = intent.getIntExtra(
        BatteryManager.EXTRA_SCALE, -1
    )
    val batteryPct = level * 100 / scale.toFloat()

    val status = intent.getIntExtra(
        BatteryManager.EXTRA_STATUS, -1
    )
    val isCharging = status ==
        BatteryManager.BATTERY_STATUS_CHARGING ||
        status == BatteryManager.BATTERY_STATUS_FULL
}
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
                        text = "Nota:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "¡No se requieren permisos especiales para info de batería!",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

data class BatteryInfo(
    val level: Int,
    val status: String,
    val isCharging: Boolean,
    val health: String,
    val temperature: Float,
    val voltage: Int
)

private fun getBatteryInfo(context: Context): BatteryInfo {
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val batteryStatus = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    val statusText = when (status) {
        BatteryManager.BATTERY_STATUS_CHARGING -> "Cargando"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> "Descargando"
        BatteryManager.BATTERY_STATUS_FULL -> "Completa"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "No Cargando"
        else -> "Desconocido"
    }

    val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL

    val health = batteryStatus?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1) ?: -1
    val healthText = when (health) {
        BatteryManager.BATTERY_HEALTH_GOOD -> "Buena"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Sobrecalentando"
        BatteryManager.BATTERY_HEALTH_DEAD -> "Muerta"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Sobrevoltaje"
        BatteryManager.BATTERY_HEALTH_COLD -> "Fría"
        else -> "Desconocido"
    }

    val temperature = (batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: 0) / 10f
    val voltage = batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: 0

    return BatteryInfo(level, statusText, isCharging, healthText, temperature, voltage)
}
