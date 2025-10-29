package com.example.baseproject.ui.screens.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.screens.buttons.ComponentSection
import com.example.baseproject.ui.theme.BaseProjectTheme

/**
 * Pantalla que muestra todos los tipos de Cards en Material Design 3.
 * Incluye: Card, ElevatedCard, OutlinedCard, y diferentes configuraciones
 * con imágenes, acciones, y contenido variado.
 *
 * @param onBackClick Callback para volver a la pantalla anterior
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    onBackClick: () -> Unit
) {
    var clickedCard by remember { mutableStateOf("Ninguna") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cards") },
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
            // Indicador de última card clickeada
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "Última card clickeada: $clickedCard",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // Sección: Cards básicas
            item {
                ComponentSection(title = "Cards Básicas") {
                    // Card estándar (Filled)
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Card (Filled)",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Esta es una card estándar con estilo filled. " +
                                        "Tiene una elevación sutil y un fondo de color.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Elevated Card
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Elevated Card",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Esta card tiene mayor elevación para destacar más " +
                                        "del fondo y crear una jerarquía visual.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    // Outlined Card
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Outlined Card",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Esta card usa un borde en lugar de elevación. " +
                                        "Útil para diseños más planos.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // Sección: Cards clickeables
            item {
                ComponentSection(title = "Cards Interactivas") {
                    Card(
                        onClick = { clickedCard = "Card Simple" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Card Clickeable",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Haz clic en esta card para interactuar",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    ElevatedCard(
                        onClick = { clickedCard = "Elevated Card" },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Card con Ícono",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Combina íconos con contenido",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            // Sección: Cards con acciones
            item {
                ComponentSection(title = "Cards con Acciones") {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Artículo de Noticias",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Material Design 3 trae nuevos componentes y " +
                                        "mejoras para crear interfaces modernas y accesibles.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // Fila de acciones
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextButton(onClick = { clickedCard = "Compartir" }) {
                                    Icon(Icons.Default.Share, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Compartir")
                                }
                                TextButton(onClick = { clickedCard = "Leer más" }) {
                                    Text("Leer más")
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }

            // Sección: Card con imagen (simulada con color)
            item {
                ComponentSection(title = "Cards con Encabezado Visual") {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            // Área de "imagen" simulada con color
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.Image,
                                        contentDescription = null,
                                        modifier = Modifier.size(64.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            // Contenido de la card
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Lugar Hermoso",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Una descripción del lugar que aparece en la imagen. " +
                                            "Este tipo de card es común para mostrar contenido visual.",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        IconButton(onClick = { clickedCard = "Like" }) {
                                            Icon(Icons.Default.Favorite, contentDescription = "Like")
                                        }
                                        IconButton(onClick = { clickedCard = "Share" }) {
                                            Icon(Icons.Default.Share, contentDescription = "Share")
                                        }
                                    }
                                    TextButton(onClick = { clickedCard = "Ver más" }) {
                                        Text("Ver más")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Sección: Cards compactas
            item {
                ComponentSection(title = "Cards Compactas") {
                    repeat(3) { index ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { clickedCard = "Item ${index + 1}" }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier.size(40.dp),
                                    shape = MaterialTheme.shapes.small,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${index + 1}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Elemento ${index + 1}",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = "Descripción del elemento",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Icon(
                                    Icons.Default.ChevronRight,
                                    contentDescription = "Ver"
                                )
                            }
                        }
                    }
                }
            }

            // Sección: Card de perfil
            item {
                ComponentSection(title = "Card de Perfil") {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Avatar
                            Surface(
                                modifier = Modifier.size(80.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Juan Pérez",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Desarrollador Android",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                FilledTonalButton(
                                    onClick = { clickedCard = "Seguir" }
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Seguir")
                                }
                                OutlinedButton(
                                    onClick = { clickedCard = "Mensaje" }
                                ) {
                                    Icon(Icons.Default.Message, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Mensaje")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardsScreenPreview() {
    BaseProjectTheme {
        CardsScreen(onBackClick = {})
    }
}
