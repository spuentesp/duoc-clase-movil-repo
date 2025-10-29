package com.example.baseproject.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Modelo de datos que representa una categoría de componentes en el showcase.
 * Cada categoría agrupa un conjunto de componentes relacionados de Material Design 3.
 *
 * @property title Título de la categoría que se mostrará en la UI
 * @property icon Ícono representativo de la categoría
 * @property route Ruta de navegación para esta categoría
 * @property description Descripción breve de lo que contiene la categoría
 */
data class ShowcaseCategory(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val description: String
)

/**
 * Lista de todas las categorías disponibles en el showcase.
 * Esta lista define la estructura de navegación principal de la aplicación.
 */
val showcaseCategories = listOf(
    ShowcaseCategory(
        title = "Buttons",
        icon = Icons.Default.TouchApp,
        route = "buttons",
        description = "Botones, FABs, IconButtons y variantes"
    ),
    ShowcaseCategory(
        title = "Text Fields",
        icon = Icons.Default.Edit,
        route = "textfields",
        description = "Campos de texto y sus variantes"
    ),
    ShowcaseCategory(
        title = "Cards",
        icon = Icons.Default.CreditCard,
        route = "cards",
        description = "Tarjetas y contenedores"
    ),
    ShowcaseCategory(
        title = "Lists",
        icon = Icons.Default.List,
        route = "lists",
        description = "Listas, LazyColumn y elementos"
    ),
    ShowcaseCategory(
        title = "Dialogs",
        icon = Icons.Default.Message,
        route = "dialogs",
        description = "Diálogos y alertas"
    ),
    ShowcaseCategory(
        title = "Navigation",
        icon = Icons.Default.Menu,
        route = "navigation",
        description = "Drawers y navegación lateral"
    ),
    ShowcaseCategory(
        title = "Bottom Sheets",
        icon = Icons.Default.SwipeUp,
        route = "bottomsheets",
        description = "Snackbars y Bottom Sheets"
    ),
    ShowcaseCategory(
        title = "App Bars",
        icon = Icons.Default.AppSettingsAlt,
        route = "appbars",
        description = "TopAppBar, BottomAppBar y variantes"
    ),
    ShowcaseCategory(
        title = "Selection Controls",
        icon = Icons.Default.CheckBox,
        route = "selectioncontrols",
        description = "Switches, Sliders, Checkboxes, RadioButtons"
    ),
    ShowcaseCategory(
        title = "Icons & Badges",
        icon = Icons.Default.Star,
        route = "icons",
        description = "Iconos, avatares y badges"
    ),
    ShowcaseCategory(
        title = "Theming",
        icon = Icons.Default.Palette,
        route = "theming",
        description = "Typography, Colors y Shapes"
    )
)
