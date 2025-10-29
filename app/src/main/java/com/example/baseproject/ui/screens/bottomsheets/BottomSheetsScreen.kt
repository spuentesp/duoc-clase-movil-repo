package com.example.baseproject.ui.screens.bottomsheets

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
import kotlinx.coroutines.launch

/**
 * Pantalla que muestra BottomSheets y Snackbars.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetsScreen(
    onBackClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val bottomSheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var actionResult by remember { mutableStateOf("Ninguna acción") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bottom Sheets & Snackbars") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                ComponentSection(title = "Snackbars") {
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Snackbar simple")
                                actionResult = "Snackbar simple mostrado"
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mostrar Snackbar Simple")
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "¿Deshacer la acción?",
                                    actionLabel = "Deshacer",
                                    duration = SnackbarDuration.Long
                                )
                                actionResult = when (result) {
                                    SnackbarResult.ActionPerformed -> "Acción deshecha"
                                    SnackbarResult.Dismissed -> "Snackbar cerrado"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Snackbar con Acción")
                    }

                    Text(
                        "Los Snackbars aparecen en la parte inferior y proporcionan " +
                                "feedback breve sobre operaciones.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                ComponentSection(title = "Modal Bottom Sheet") {
                    Button(
                        onClick = { showBottomSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.SwipeUp, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mostrar Bottom Sheet")
                    }

                    Text(
                        "Bottom Sheet es un panel que se desliza desde la parte inferior " +
                                "de la pantalla, útil para mostrar opciones o contenido adicional.",
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
                            "Cuándo usar cada uno",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Snackbar:\n" +
                                    "• Feedback de operaciones\n" +
                                    "• Mensajes temporales\n" +
                                    "• Acciones de deshacer\n\n" +
                                    "Bottom Sheet:\n" +
                                    "• Menús de acciones\n" +
                                    "• Formularios cortos\n" +
                                    "• Compartir/Selección",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Modal Bottom Sheet
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    actionResult = "Bottom Sheet cerrado"
                },
                sheetState = bottomSheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Opciones",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val options = listOf(
                        "Compartir" to Icons.Default.Share,
                        "Editar" to Icons.Default.Edit,
                        "Eliminar" to Icons.Default.Delete,
                        "Información" to Icons.Default.Info
                    )

                    options.forEach { (title, icon) ->
                        ListItem(
                            headlineContent = { Text(title) },
                            leadingContent = {
                                Icon(icon, contentDescription = null)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ListItemDefaults.colors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                        if (options.last() != (title to icon)) {
                            Divider()
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            showBottomSheet = false
                            actionResult = "Bottom Sheet - Acción ejecutada"
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cerrar")
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetsScreenPreview() {
    BaseProjectTheme {
        BottomSheetsScreen(onBackClick = {})
    }
}
