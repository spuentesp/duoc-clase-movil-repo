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
                title = { Text("Battery Status") },
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
                text = "Battery Information",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Monitor battery level and charging status.",
                style = MaterialTheme.typography.bodyMedium
            )

            Divider()

            Button(
                onClick = { batteryInfo = getBatteryInfo(context) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Refresh Battery Info")
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Battery Status",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Level: ${batteryInfo.level}%")
                    Text("Status: ${batteryInfo.status}")
                    Text("Charging: ${if (batteryInfo.isCharging) "Yes" else "No"}")
                    Text("Health: ${batteryInfo.health}")
                    Text("Temperature: ${batteryInfo.temperature}°C")
                    Text("Voltage: ${batteryInfo.voltage}mV")
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
// Get battery manager
val batteryManager = context.getSystemService(
    Context.BATTERY_SERVICE
) as BatteryManager

// Get battery level
val level = batteryManager.getIntProperty(
    BatteryManager.BATTERY_PROPERTY_CAPACITY
)

// Alternative: Using BroadcastReceiver
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
                        text = "Note:",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = "No special permissions required for battery info!",
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
        BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
        BatteryManager.BATTERY_STATUS_FULL -> "Full"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
        else -> "Unknown"
    }

    val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL

    val health = batteryStatus?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1) ?: -1
    val healthText = when (health) {
        BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheating"
        BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
        BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
        else -> "Unknown"
    }

    val temperature = (batteryStatus?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) ?: 0) / 10f
    val voltage = batteryStatus?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: 0

    return BatteryInfo(level, statusText, isCharging, healthText, temperature, voltage)
}
