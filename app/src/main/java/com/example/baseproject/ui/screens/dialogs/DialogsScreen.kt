package com.example.baseproject.ui.screens.dialogs

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

/**
 * Pantalla que muestra diferentes tipos de diálogos en Material Design 3.
 * Incluye: AlertDialog, Dialog personalizado, diálogos con diferentes configuraciones.
 *
 * @param onBackClick Callback para volver a la pantalla anterior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsScreen(
    onBackClick: () -> Unit
) {
    var showSimpleDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showIconDialog by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var showListDialog by remember { mutableStateOf(false) }
    var dialogResult by remember { mutableStateOf("Ninguna acción") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dialogs") },
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
            // Resultado de la última acción
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Última acción: $dialogResult",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Sección: Diálogo simple
            item {
                ComponentSection(title = "AlertDialog Simple") {
                    Button(
                        onClick = { showSimpleDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mostrar Diálogo Simple")
                    }

                    if (showSimpleDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showSimpleDialog = false
                                dialogResult = "Diálogo cerrado (fuera)"
                            },
                            title = {
                                Text("Título del Diálogo")
                            },
                            text = {
                                Text("Este es el contenido del diálogo. Aquí puedes mostrar " +
                                        "información importante al usuario.")
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showSimpleDialog = false
                                        dialogResult = "Aceptar presionado"
                                    }
                                ) {
                                    Text("Aceptar")
                                }
                            }
                        )
                    }
                }
            }

            // Sección: Diálogo de confirmación
            item {
                ComponentSection(title = "Diálogo de Confirmación") {
                    Button(
                        onClick = { showConfirmDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mostrar Confirmación")
                    }

                    if (showConfirmDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showConfirmDialog = false
                                dialogResult = "Confirmación cancelada"
                            },
                            icon = {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null
                                )
                            },
                            title = {
                                Text("¿Eliminar elemento?")
                            },
                            text = {
                                Text("Esta acción no se puede deshacer. ¿Estás seguro de que " +
                                        "deseas eliminar este elemento?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showConfirmDialog = false
                                        dialogResult = "Elemento eliminado"
                                    }
                                ) {
                                    Text("Eliminar")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showConfirmDialog = false
                                        dialogResult = "Eliminación cancelada"
                                    }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }

            // Sección: Diálogo con ícono
            item {
                ComponentSection(title = "Diálogo con Ícono") {
                    Button(
                        onClick = { showIconDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mostrar con Ícono")
                    }

                    if (showIconDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showIconDialog = false
                                dialogResult = "Diálogo con ícono cerrado"
                            },
                            icon = {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            title = {
                                Text("Operación Exitosa")
                            },
                            text = {
                                Text("La operación se completó correctamente. Todos los cambios " +
                                        "han sido guardados.")
                            },
                            confirmButton = {
                                FilledTonalButton(
                                    onClick = {
                                        showIconDialog = false
                                        dialogResult = "Operación confirmada"
                                    }
                                ) {
                                    Text("Entendido")
                                }
                            }
                        )
                    }
                }
            }

            // Sección: Diálogo con lista de opciones
            item {
                ComponentSection(title = "Diálogo con Lista") {
                    Button(
                        onClick = { showListDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mostrar Lista de Opciones")
                    }

                    if (showListDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showListDialog = false
                                dialogResult = "Lista cerrada"
                            },
                            title = {
                                Text("Selecciona una opción")
                            },
                            text = {
                                Column {
                                    val options = listOf(
                                        "Opción 1" to Icons.Default.Home,
                                        "Opción 2" to Icons.Default.Favorite,
                                        "Opción 3" to Icons.Default.Settings,
                                        "Opción 4" to Icons.Default.Person
                                    )

                                    options.forEach { (text, icon) ->
                                        TextButton(
                                            onClick = {
                                                showListDialog = false
                                                dialogResult = "$text seleccionada"
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                icon,
                                                contentDescription = null,
                                                modifier = Modifier.size(24.dp)
                                            )
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Text(
                                                text = text,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            },
                            confirmButton = {},
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showListDialog = false
                                        dialogResult = "Lista cancelada"
                                    }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }

            // Sección: Diálogo personalizado
            item {
                ComponentSection(title = "Diálogo Personalizado") {
                    Button(
                        onClick = { showCustomDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mostrar Diálogo Personalizado")
                    }

                    if (showCustomDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                showCustomDialog = false
                                dialogResult = "Diálogo personalizado cerrado"
                            },
                            title = {
                                Text("Formulario de Contacto")
                            },
                            text = {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    var nombre by remember { mutableStateOf("") }
                                    var email by remember { mutableStateOf("") }

                                    OutlinedTextField(
                                        value = nombre,
                                        onValueChange = { nombre = it },
                                        label = { Text("Nombre") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    OutlinedTextField(
                                        value = email,
                                        onValueChange = { email = it },
                                        label = { Text("Email") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Text(
                                        text = "Ingresa tu información de contacto",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        showCustomDialog = false
                                        dialogResult = "Formulario enviado"
                                    }
                                ) {
                                    Text("Enviar")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showCustomDialog = false
                                        dialogResult = "Formulario cancelado"
                                    }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }

            // Información adicional
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
                            text = "Buenas Prácticas",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "• Usa diálogos para decisiones importantes\n" +
                                    "• Mantén el contenido breve y claro\n" +
                                    "• Siempre proporciona una forma de cerrar el diálogo\n" +
                                    "• Usa confirmButton para la acción principal\n" +
                                    "• Usa dismissButton para cancelar o cerrar",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DialogsScreenPreview() {
    BaseProjectTheme {
        DialogsScreen(onBackClick = {})
    }
}
