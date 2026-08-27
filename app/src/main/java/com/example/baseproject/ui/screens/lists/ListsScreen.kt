package com.example.baseproject.ui.screens.lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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

/**
 * Pantalla que muestra diferentes tipos de listas en Jetpack Compose.
 * Incluye: ListItem con diferentes configuraciones, LazyColumn, LazyRow,
 * listas con íconos, avatares, checkboxes, y más.
 *
 * @param onBackClick Callback para volver a la pantalla anterior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    onBackClick: () -> Unit
) {
    var selectedItem by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lists") },
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
            // Indicador de elemento seleccionado
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Elemento seleccionado: ${selectedItem ?: "Ninguno"}",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Sección: ListItem básico
            item {
                ComponentSection(title = "ListItem - Elementos Básicos") {
                    // ListItem de una línea
                    ListItem(
                        headlineContent = { Text("Elemento de una línea") }
                    )
                    Divider()

                    // ListItem de dos líneas
                    ListItem(
                        headlineContent = { Text("Elemento de dos líneas") },
                        supportingContent = { Text("Texto de soporte adicional") }
                    )
                    Divider()

                    // ListItem de tres líneas
                    ListItem(
                        headlineContent = { Text("Elemento de tres líneas") },
                        supportingContent = {
                            Text("Este es un texto de soporte más largo que puede " +
                                    "ocupar varias líneas para mostrar más información.")
                        }
                    )
                }
            }

            // Sección: ListItem con íconos
            item {
                ComponentSection(title = "ListItem con Íconos") {
                    ListItem(
                        headlineContent = { Text("Inicio") },
                        leadingContent = {
                            Icon(Icons.Default.Home, contentDescription = null)
                        }
                    )
                    Divider()

                    ListItem(
                        headlineContent = { Text("Configuración") },
                        supportingContent = { Text("Ajustes de la aplicación") },
                        leadingContent = {
                            Icon(Icons.Default.Settings, contentDescription = null)
                        },
                        trailingContent = {
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    )
                    Divider()

                    ListItem(
                        headlineContent = { Text("Notificaciones") },
                        supportingContent = { Text("Gestionar notificaciones") },
                        leadingContent = {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingContent = {
                            Switch(
                                checked = false,
                                onCheckedChange = {}
                            )
                        }
                    )
                }
            }

            // Sección: ListItem con avatares
            item {
                ComponentSection(title = "ListItem con Avatares") {
                    val contacts = listOf(
                        "Ana García" to "ana@email.com",
                        "Juan Pérez" to "juan@email.com",
                        "María López" to "maria@email.com"
                    )

                    contacts.forEach { (name, email) ->
                        ListItem(
                            headlineContent = { Text(name) },
                            supportingContent = { Text(email) },
                            leadingContent = {
                                Surface(
                                    modifier = Modifier.size(40.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = name.first().toString(),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                            },
                            trailingContent = {
                                IconButton(onClick = { selectedItem = name }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "Más")
                                }
                            }
                        )
                        if (contacts.last() != (name to email)) {
                            Divider()
                        }
                    }
                }
            }

            // Sección: Lista con Checkboxes
            item {
                ComponentSection(title = "Lista con Checkboxes") {
                    val checkedStates = remember {
                        mutableStateListOf(false, true, false, true)
                    }
                    val tasks = listOf(
                        "Revisar correo electrónico",
                        "Completar reporte mensual",
                        "Llamar al cliente",
                        "Actualizar documentación"
                    )

                    tasks.forEachIndexed { index, task ->
                        ListItem(
                            headlineContent = { Text(task) },
                            leadingContent = {
                                Checkbox(
                                    checked = checkedStates[index],
                                    onCheckedChange = {
                                        checkedStates[index] = it
                                        selectedItem = task
                                    }
                                )
                            }
                        )
                        if (index < tasks.lastIndex) {
                            Divider()
                        }
                    }
                }
            }

            // Sección: Lista con RadioButtons
            item {
                ComponentSection(title = "Lista con RadioButtons") {
                    var selectedOption by remember { mutableStateOf(0) }
                    val options = listOf(
                        "Opción 1" to "Primera opción disponible",
                        "Opción 2" to "Segunda opción disponible",
                        "Opción 3" to "Tercera opción disponible"
                    )

                    options.forEachIndexed { index, (title, description) ->
                        ListItem(
                            headlineContent = { Text(title) },
                            supportingContent = { Text(description) },
                            leadingContent = {
                                RadioButton(
                                    selected = selectedOption == index,
                                    onClick = {
                                        selectedOption = index
                                        selectedItem = title
                                    }
                                )
                            }
                        )
                        if (index < options.lastIndex) {
                            Divider()
                        }
                    }
                }
            }

            // Sección: LazyRow horizontal
            item {
                ComponentSection(title = "LazyRow - Lista Horizontal") {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(10) { index ->
                            Card(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(120.dp),
                                onClick = { selectedItem = "Card ${index + 1}" }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Image,
                                        contentDescription = null,
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Item ${index + 1}",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Sección: Lista agrupada
            item {
                ComponentSection(title = "Lista con Encabezados") {
                    val groupedItems = mapOf(
                        "Frutas" to listOf("Manzana", "Banana", "Naranja"),
                        "Verduras" to listOf("Zanahoria", "Lechuga", "Tomate"),
                        "Lácteos" to listOf("Leche", "Queso", "Yogurt")
                    )

                    groupedItems.forEach { (category, items) ->
                        // Encabezado de categoría
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            },
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )

                        // Elementos de la categoría
                        items.forEach { item ->
                            ListItem(
                                headlineContent = { Text(item) },
                                leadingContent = {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            Divider(modifier = Modifier.padding(start = 56.dp))
                        }
                    }
                }
            }

            // Sección: Lista de ejemplo realista
            item {
                ComponentSection(title = "Ejemplo: Lista de Mensajes") {
                    val messages = listOf(
                        Triple("Ana García", "Hola, ¿cómo estás?", "10:30 AM"),
                        Triple("Juan Pérez", "Reunión mañana a las 10", "Ayer"),
                        Triple("María López", "Gracias por tu ayuda", "Lunes")
                    )

                    messages.forEach { (name, message, time) ->
                        ListItem(
                            headlineContent = { Text(name) },
                            supportingContent = { Text(message) },
                            leadingContent = {
                                Surface(
                                    modifier = Modifier.size(56.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Person,
                                            contentDescription = null,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            },
                            trailingContent = {
                                Text(
                                    text = time,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListsScreenPreview() {
    BaseProjectTheme {
        ListsScreen(onBackClick = {})
    }
}
