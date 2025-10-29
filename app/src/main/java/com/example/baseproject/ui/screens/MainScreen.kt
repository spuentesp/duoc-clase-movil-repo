package com.example.baseproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.baseproject.ui.theme.BaseProjectTheme

/**
 * Pantalla principal del Material Design Showcase.
 * Muestra una cuadrícula de categorías donde cada una agrupa componentes relacionados.
 *
 * @param onCategoryClick Callback que se ejecuta cuando el usuario selecciona una categoría.
 *                        Recibe la ruta de navegación de la categoría seleccionada.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCategoryClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Material Design 3 Showcase",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Encabezado descriptivo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Catálogo Interactivo",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Explora todos los componentes de Material Design 3 disponibles en Jetpack Compose. " +
                                "Cada categoría contiene ejemplos funcionales con código comentado.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Grid de categorías
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(showcaseCategories) { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category.route) }
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta individual para representar una categoría de componentes.
 * Muestra el icono, título y descripción de la categoría.
 *
 * @param category Datos de la categoría a mostrar
 * @param onClick Callback que se ejecuta cuando se hace clic en la tarjeta
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    category: ShowcaseCategory,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícono de la categoría
            Icon(
                imageVector = category.icon,
                contentDescription = category.title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Título de la categoría
            Text(
                text = category.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripción breve
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}

/**
 * Preview de la pantalla principal en modo claro
 */
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BaseProjectTheme {
        MainScreen(onCategoryClick = {})
    }
}

/**
 * Preview de la pantalla principal en modo oscuro
 */
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenDarkPreview() {
    BaseProjectTheme {
        MainScreen(onCategoryClick = {})
    }
}
