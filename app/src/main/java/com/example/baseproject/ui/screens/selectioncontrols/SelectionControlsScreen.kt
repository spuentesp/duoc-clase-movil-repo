package com.example.baseproject.ui.screens.selectioncontrols

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.buttons.ComponentSection
import com.example.baseproject.ui.theme.BaseProjectTheme
import androidx.compose.material.icons.outlined.NotificationsOff

/**
 * Pantalla que muestra controles de selección: Checkboxes, RadioButtons, Switches, Sliders.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionControlsScreen(
    onBackClick: () -> Unit
) {
    var checkbox1 by remember { mutableStateOf(false) }
    var checkbox2 by remember { mutableStateOf(true) }
    var checkbox3 by remember { mutableStateOf(false) }

    var radioSelection by remember { mutableStateOf(0) }
    var switch1 by remember { mutableStateOf(false) }
    var switch2 by remember { mutableStateOf(true) }

    var sliderValue by remember { mutableStateOf(50f) }
    var rangeSliderValue by remember { mutableStateOf(20f..80f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selection Controls") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Checkboxes
            item {
                ComponentSection(title = "Checkboxes") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkbox1,
                            onCheckedChange = { checkbox1 = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Opción 1")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkbox2,
                            onCheckedChange = { checkbox2 = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Opción 2 (Marcada)")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checkbox3,
                            onCheckedChange = { checkbox3 = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Opción 3")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = false,
                            onCheckedChange = {},
                            enabled = false
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Checkbox deshabilitado",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Radio Buttons
            item {
                ComponentSection(title = "Radio Buttons") {
                    val options = listOf("Opción A", "Opción B", "Opción C")

                    options.forEachIndexed { index, option ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = radioSelection == index,
                                onClick = { radioSelection = index }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = false,
                            onClick = {},
                            enabled = false
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "RadioButton deshabilitado",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Switches
            item {
                ComponentSection(title = "Switches") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Notificaciones")
                        Switch(
                            checked = switch1,
                            onCheckedChange = { switch1 = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Modo oscuro")
                            Text(
                                "Activado",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = switch2,
                            onCheckedChange = { switch2 = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Deshabilitado",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Switch(
                            checked = false,
                            onCheckedChange = {},
                            enabled = false
                        )
                    }

                    // Switch con ícono
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (switch1) Icons.Default.NotificationsActive
                                else Icons.Outlined.NotificationsOff,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Con ícono")
                        }
                        Switch(
                            checked = switch1,
                            onCheckedChange = { switch1 = it }
                        )
                    }
                }
            }

            // Sliders
            item {
                ComponentSection(title = "Sliders") {
                    Text(
                        "Slider: ${sliderValue.toInt()}%",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        valueRange = 0f..100f,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Slider con steps: ${sliderValue.toInt()}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        valueRange = 0f..100f,
                        steps = 9,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Range Slider: ${rangeSliderValue.start.toInt()} - ${rangeSliderValue.endInclusive.toInt()}",
                        style = MaterialTheme.typography.titleSmall
                    )
                    RangeSlider(
                        value = rangeSliderValue,
                        onValueChange = { rangeSliderValue = it },
                        valueRange = 0f..100f,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Slider deshabilitado",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Slider(
                        value = 50f,
                        onValueChange = {},
                        valueRange = 0f..100f,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Ejemplo integrado
            item {
                ComponentSection(title = "Ejemplo: Panel de Configuración") {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                "Ajustes de Audio",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Sonido activado")
                                Switch(
                                    checked = switch1,
                                    onCheckedChange = { switch1 = it }
                                )
                            }

                            Text("Volumen: ${sliderValue.toInt()}%")
                            Slider(
                                value = sliderValue,
                                onValueChange = { sliderValue = it },
                                valueRange = 0f..100f,
                                enabled = switch1
                            )

                            Divider()

                            Text(
                                "Notificaciones",
                                style = MaterialTheme.typography.titleSmall
                            )

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = checkbox1,
                                    onCheckedChange = { checkbox1 = it }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Alertas de mensajes")
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = checkbox2,
                                    onCheckedChange = { checkbox2 = it }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Recordatorios")
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Cuándo usar cada control",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Checkbox: Múltiples opciones independientes\n" +
                                    "RadioButton: Una opción de varias\n" +
                                    "Switch: Activar/desactivar funciones\n" +
                                    "Slider: Seleccionar valor en un rango",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionControlsScreenPreview() {
    BaseProjectTheme {
        SelectionControlsScreen(onBackClick = {})
    }
}
