package com.example.baseproject.ui.screens.theming

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.buttons.ComponentSection
import com.example.baseproject.ui.theme.BaseProjectTheme

/**
 * Pantalla que muestra el sistema de diseño: Typography, Colors, Shapes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemingScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Theming") },
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
            // Typography
            item {
                ComponentSection(title = "Typography - Tipografía") {
                    Text(
                        "Display Large",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        "Display Medium",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        "Display Small",
                        style = MaterialTheme.typography.displaySmall
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Headline Large",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        "Headline Medium",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        "Headline Small",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Title Large",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        "Title Medium",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Title Small",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Body Large - Este es el estilo para cuerpo de texto grande",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Body Medium - Este es el estilo para cuerpo de texto medio",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Body Small - Este es el estilo para cuerpo de texto pequeño",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Label Large",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        "Label Medium",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        "Label Small",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Color Scheme
            item {
                ComponentSection(title = "Color Scheme - Colores") {
                    Text(
                        "Material 3 usa un esquema de colores dinámico basado en " +
                                "un color semilla. Los colores se adaptan automáticamente al modo " +
                                "claro y oscuro.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Colores principales
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ColorSwatch(
                            "Primary",
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimary
                        )
                        ColorSwatch(
                            "Primary Container",
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        ColorSwatch(
                            "Secondary",
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.onSecondary
                        )
                        ColorSwatch(
                            "Secondary Container",
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        ColorSwatch(
                            "Tertiary",
                            MaterialTheme.colorScheme.tertiary,
                            MaterialTheme.colorScheme.onTertiary
                        )
                        ColorSwatch(
                            "Tertiary Container",
                            MaterialTheme.colorScheme.tertiaryContainer,
                            MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        ColorSwatch(
                            "Error",
                            MaterialTheme.colorScheme.error,
                            MaterialTheme.colorScheme.onError
                        )
                        ColorSwatch(
                            "Surface",
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.onSurface
                        )
                        ColorSwatch(
                            "Surface Variant",
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        ColorSwatch(
                            "Background",
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            // Shapes
            item {
                ComponentSection(title = "Shapes - Formas") {
                    Text(
                        "Material 3 define formas redondeadas que se aplican a los componentes:",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                shape = MaterialTheme.shapes.extraSmall,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {}
                            Text(
                                "Extra Small",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                shape = MaterialTheme.shapes.small,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {}
                            Text(
                                "Small",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                shape = MaterialTheme.shapes.medium,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {}
                            Text(
                                "Medium",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                shape = MaterialTheme.shapes.large,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {}
                            Text(
                                "Large",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {}
                            Text(
                                "Extra Large",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            // Elevación
            item {
                ComponentSection(title = "Elevation - Elevación") {
                    Text(
                        "La elevación crea profundidad usando sombras:",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                tonalElevation = 0.dp,
                                shadowElevation = 0.dp,
                                shape = MaterialTheme.shapes.medium
                            ) {}
                            Text("0dp", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                tonalElevation = 1.dp,
                                shadowElevation = 1.dp,
                                shape = MaterialTheme.shapes.medium
                            ) {}
                            Text("1dp", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                tonalElevation = 3.dp,
                                shadowElevation = 3.dp,
                                shape = MaterialTheme.shapes.medium
                            ) {}
                            Text("3dp", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(60.dp),
                                tonalElevation = 6.dp,
                                shadowElevation = 6.dp,
                                shape = MaterialTheme.shapes.medium
                            ) {}
                            Text("6dp", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Información del tema
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
                            "Material You",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Material Design 3 introduce 'Material You', un sistema de diseño " +
                                    "personalizable que adapta los colores al dispositivo y preferencias del usuario.\n\n" +
                                    "Características principales:\n" +
                                    "• Colores dinámicos del wallpaper (Android 12+)\n" +
                                    "• Soporte automático de modo claro/oscuro\n" +
                                    "• Tipografía escalable y accesible\n" +
                                    "• Formas redondeadas y suaves\n" +
                                    "• Elevación basada en tonalidad",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Ejemplo de aplicación
            item {
                ComponentSection(title = "Ejemplo de Aplicación") {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                "Título del Artículo",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "Este es un ejemplo de cómo se combinan la tipografía, " +
                                        "los colores y las formas para crear una interfaz cohesiva " +
                                        "y atractiva.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = {}) {
                                    Text("Leer más")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable auxiliar para mostrar una muestra de color
 */
@Composable
fun ColorSwatch(
    name: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemingScreenPreview() {
    BaseProjectTheme {
        ThemingScreen(onBackClick = {})
    }
}
