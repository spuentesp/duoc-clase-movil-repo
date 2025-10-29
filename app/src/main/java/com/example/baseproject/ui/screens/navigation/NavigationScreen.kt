package com.example.baseproject.ui.screens.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.buttons.ComponentSection
import com.example.baseproject.ui.theme.BaseProjectTheme
import kotlinx.coroutines.launch

/**
 * Pantalla que muestra componentes de navegación lateral como NavigationDrawer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScreen(
    onBackClick: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf("Inicio") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Menú de Navegación",
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Divider()

                val items = listOf(
                    "Inicio" to Icons.Default.Home,
                    "Perfil" to Icons.Default.Person,
                    "Configuración" to Icons.Default.Settings,
                    "Favoritos" to Icons.Default.Favorite,
                    "Mensajes" to Icons.Default.Message
                )

                items.forEach { (title, icon) ->
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(title) },
                        selected = selectedItem == title,
                        onClick = {
                            selectedItem = title
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Navigation") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
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
                            text = "Seleccionado: $selectedItem",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                item {
                    ComponentSection(title = "Navigation Drawer") {
                        Text(
                            "Navigation Drawer es un panel lateral que se desliza desde el borde " +
                                    "de la pantalla. Usa el botón del menú en la barra superior para abrirlo.",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Abrir Navigation Drawer")
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
                                "Características",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "• Navegación entre secciones principales\n" +
                                        "• Se puede abrir deslizando desde el borde\n" +
                                        "• Ideal para apps con múltiples secciones\n" +
                                        "• Soporta íconos y badges",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationScreenPreview() {
    BaseProjectTheme {
        NavigationScreen(onBackClick = {})
    }
}
