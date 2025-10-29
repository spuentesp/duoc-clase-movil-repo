package com.example.baseproject.ui.screens.icons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
 * Pantalla que muestra íconos, badges y avatares.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Icons & Badges") },
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
            // Íconos básicos
            item {
                ComponentSection(title = "Íconos Básicos") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                            Text("Default", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Star",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text("Primary", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Favorite",
                                tint = MaterialTheme.colorScheme.error
                            )
                            Text("Error", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Check",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Text("Large", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Galería de íconos comunes
            item {
                ComponentSection(title = "Íconos Comunes") {
                    val commonIcons = listOf(
                        Icons.Default.Home to "Home",
                        Icons.Default.Person to "Person",
                        Icons.Default.Settings to "Settings",
                        Icons.Default.Search to "Search",
                        Icons.Default.Notifications to "Notifications",
                        Icons.Default.Email to "Email",
                        Icons.Default.Phone to "Phone",
                        Icons.Default.Edit to "Edit",
                        Icons.Default.Delete to "Delete",
                        Icons.Default.Share to "Share",
                        Icons.Default.Add to "Add",
                        Icons.Default.Check to "Check",
                        Icons.Default.Close to "Close",
                        Icons.Default.Menu to "Menu",
                        Icons.Default.MoreVert to "More",
                        Icons.Default.Favorite to "Favorite"
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.height(300.dp)
                    ) {
                        items(commonIcons) { (icon, name) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = name,
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    name,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            // Badges
            item {
                ComponentSection(title = "Badges") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            BadgedBox(badge = { Badge() }) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notificaciones",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text("Badge", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            BadgedBox(
                                badge = {
                                    Badge { Text("5") }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Mensajes",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text("Con número", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            BadgedBox(
                                badge = {
                                    Badge { Text("99+") }
                                }
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Carrito",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text("99+", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = MaterialTheme.colorScheme.error
                                    ) {
                                        Text("New")
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.NewReleases,
                                    contentDescription = "Nuevo",
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                            Text("Custom", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Avatares
            item {
                ComponentSection(title = "Avatares") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                            Text("Small", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(64.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        "JD",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }
                            }
                            Text("Medium", style = MaterialTheme.typography.labelSmall)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(80.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                            }
                            Text("Large", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }

            // Avatar con badge
            item {
                ComponentSection(title = "Avatares con Badge") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            }
                        ) {
                            Surface(
                                modifier = Modifier.size(56.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }

                        BadgedBox(
                            badge = {
                                Badge { Text("3") }
                            }
                        ) {
                            Surface(
                                modifier = Modifier.size(56.dp),
                                shape = MaterialTheme.shapes.extraLarge,
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        "AB",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Chips con íconos
            item {
                ComponentSection(title = "Chips con Íconos") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AssistChip(
                            onClick = {},
                            label = { Text("Assist") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        FilterChip(
                            selected = false,
                            onClick = {},
                            label = { Text("Filter") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InputChip(
                            selected = false,
                            onClick = {},
                            label = { Text("Input") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        SuggestionChip(
                            onClick = {},
                            label = { Text("Suggestion") }
                        )
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
                            "Material Icons",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Material Design incluye más de 2,000 íconos oficiales. " +
                                    "Están disponibles en cinco estilos: Filled, Outlined, Rounded, " +
                                    "Sharp, y Two-tone.\n\n" +
                                    "En Compose usamos Icons.Default (Filled) por defecto.",
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
fun IconsScreenPreview() {
    BaseProjectTheme {
        IconsScreen(onBackClick = {})
    }
}
