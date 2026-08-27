package com.example.baseproject.ui.screens.buttons

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
import com.example.baseproject.ui.theme.BaseProjectTheme
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.VisibilityOff

/**
 * Pantalla que muestra todos los tipos de botones disponibles en Material Design 3.
 * Incluye: Button, ElevatedButton, FilledTonalButton, OutlinedButton, TextButton,
 * FloatingActionButton (y variantes), e IconButton (y variantes).
 *
 * @param onBackClick Callback para volver a la pantalla anterior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(
    onBackClick: () -> Unit
) {
    var clickCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buttons") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        // FAB flotante - ejemplo de uso común
        floatingActionButton = {
            FloatingActionButton(
                onClick = { clickCount++ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Incrementar")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Contador de clics (para demostrar interactividad)
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Contador de clics: $clickCount",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Sección: Botones estándar
            item {
                ComponentSection(title = "Botones Estándar") {
                    // Button - Botón principal con mayor énfasis
                    Button(
                        onClick = { clickCount++ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Button (Filled)")
                    }

                    // ElevatedButton - Botón con elevación
                    ElevatedButton(
                        onClick = { clickCount++ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Elevated Button")
                    }

                    // FilledTonalButton - Botón con color tonal
                    FilledTonalButton(
                        onClick = { clickCount++ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Filled Tonal Button")
                    }

                    // OutlinedButton - Botón con borde
                    OutlinedButton(
                        onClick = { clickCount++ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Outlined Button")
                    }

                    // TextButton - Botón de texto sin fondo
                    TextButton(
                        onClick = { clickCount++ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            Icons.Default.Help,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Text Button")
                    }
                }
            }

            // Sección: Estados de botones
            item {
                ComponentSection(title = "Estados") {
                    // Botón deshabilitado
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Button Deshabilitado")
                    }

                    OutlinedButton(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Outlined Deshabilitado")
                    }
                }
            }

            // Sección: Floating Action Buttons (FABs)
            item {
                ComponentSection(title = "Floating Action Buttons") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // FAB pequeño
                        SmallFloatingActionButton(
                            onClick = { clickCount++ }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Small FAB")
                        }

                        // FAB normal
                        FloatingActionButton(
                            onClick = { clickCount++ }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "FAB")
                        }

                        // FAB grande
                        LargeFloatingActionButton(
                            onClick = { clickCount++ }
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Large FAB",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Extended FAB - FAB con texto
                    ExtendedFloatingActionButton(
                        onClick = { clickCount++ },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = null
                            )
                        },
                        text = { Text("Extended FAB") }
                    )
                }
            }

            // Sección: Icon Buttons
            item {
                ComponentSection(title = "Icon Buttons") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // IconButton estándar
                            IconButton(onClick = { clickCount++ }) {
                                Icon(Icons.Default.Home, contentDescription = "Home")
                            }
                            Text("IconButton", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // FilledIconButton
                            FilledIconButton(onClick = { clickCount++ }) {
                                Icon(Icons.Default.Star, contentDescription = "Favorite")
                            }
                            Text("Filled", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // FilledTonalIconButton
                            FilledTonalIconButton(onClick = { clickCount++ }) {
                                Icon(Icons.Default.Settings, contentDescription = "Settings")
                            }
                            Text("Tonal", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // OutlinedIconButton
                            OutlinedIconButton(onClick = { clickCount++ }) {
                                Icon(Icons.Default.Share, contentDescription = "Share")
                            }
                            Text("Outlined", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Sección: Icon Toggle Button
            item {
                ComponentSection(title = "Icon Toggle Buttons") {
                    var checked by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconToggleButton(
                                checked = checked,
                                onCheckedChange = { checked = it }
                            ) {
                                Icon(
                                    if (checked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Toggle"
                                )
                            }
                            Text("Standard", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            FilledIconToggleButton(
                                checked = checked,
                                onCheckedChange = { checked = it }
                            ) {
                                Icon(
                                    if (checked) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Toggle"
                                )
                            }
                            Text("Filled", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            FilledTonalIconToggleButton(
                                checked = checked,
                                onCheckedChange = { checked = it }
                            ) {
                                Icon(
                                    if (checked) Icons.Default.Star else Icons.Outlined.StarBorder,
                                    contentDescription = "Toggle"
                                )
                            }
                            Text("Tonal", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            OutlinedIconToggleButton(
                                checked = checked,
                                onCheckedChange = { checked = it }
                            ) {
                                Icon(
                                    if (checked) Icons.Default.Visibility else Icons.Outlined.VisibilityOff,
                                    contentDescription = "Toggle"
                                )
                            }
                            Text("Outlined", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Componente reutilizable para agrupar secciones de componentes.
 * Proporciona un título y un contenedor para los elementos.
 */
@Composable
fun ComponentSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Divider()
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonsScreenPreview() {
    BaseProjectTheme {
        ButtonsScreen(onBackClick = {})
    }
}
