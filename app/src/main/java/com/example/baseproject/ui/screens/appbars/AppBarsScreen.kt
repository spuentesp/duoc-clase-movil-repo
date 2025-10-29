package com.example.baseproject.ui.screens.appbars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.buttons.ComponentSection
import com.example.baseproject.ui.theme.BaseProjectTheme

/**
 * Pantalla que muestra diferentes tipos de App Bars en Material Design 3.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarsScreen(
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var actionResult by remember { mutableStateOf("Ninguna acción") }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("App Bars") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { actionResult = "Buscar presionado" }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = { actionResult = "Más presionado" }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Más")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { actionResult = "Home presionado" }) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { actionResult = "Favoritos presionado" }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                    }
                    IconButton(onClick = { actionResult = "Perfil presionado" }) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { actionResult = "FAB presionado" }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar")
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
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Última acción: $actionResult",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            item {
                ComponentSection(title = "TopAppBar") {
                    Text(
                        "La TopAppBar que ves arriba es un ejemplo de TopAppBar estándar " +
                                "con botón de navegación y acciones.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Componentes:\n" +
                                "• Botón de navegación (← Volver)\n" +
                                "• Título (App Bars)\n" +
                                "• Acciones (Buscar, Más)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                ComponentSection(title = "Variantes de TopAppBar") {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Small TopAppBar",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "TopAppBar estándar, altura fija",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Medium TopAppBar",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "TopAppBar con altura media, el título se coloca abajo",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Large TopAppBar",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "TopAppBar grande con título prominente, ideal para pantallas principales",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }

            item {
                ComponentSection(title = "BottomAppBar") {
                    Text(
                        "La BottomAppBar que ves abajo es un ejemplo de barra inferior " +
                                "con acciones y un FAB integrado.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Características:\n" +
                                "• Acciones principales en la parte inferior\n" +
                                "• FAB integrado (botón +)\n" +
                                "• Fácil acceso con el pulgar\n" +
                                "• Ideal para apps móviles",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                ComponentSection(title = "Scroll Behaviors") {
                    Text(
                        "Las TopAppBar pueden tener diferentes comportamientos al hacer scroll:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "• Pinned: Siempre visible (actual)\n" +
                                "• Enter Always: Se muestra al hacer scroll hacia arriba\n" +
                                "• Exit Until Collapsed: Se colapsa al hacer scroll\n" +
                                "• Snap: Se ajusta automáticamente",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
                            "Buenas Prácticas",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "• Usa TopAppBar para navegación y contexto\n" +
                                    "• Limita las acciones a 3-4 íconos\n" +
                                    "• BottomAppBar mejora el alcance en pantallas grandes\n" +
                                    "• Usa LargeTopAppBar en pantallas principales\n" +
                                    "• Integra el FAB en BottomAppBar cuando sea relevante",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Espaciadores para demostrar scroll
            items(5) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(16.dp)
                    ) {
                        Text(
                            "Contenido ${it + 1} - Desplázate para ver el comportamiento de scroll",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarsScreenPreview() {
    BaseProjectTheme {
        AppBarsScreen(onBackClick = {})
    }
}
