package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.MainScreen
import com.example.baseproject.ui.screens.appbars.AppBarsScreen
import com.example.baseproject.ui.screens.bottomsheets.BottomSheetsScreen
import com.example.baseproject.ui.screens.buttons.ButtonsScreen
import com.example.baseproject.ui.screens.cards.CardsScreen
import com.example.baseproject.ui.screens.dialogs.DialogsScreen
import com.example.baseproject.ui.screens.icons.IconsScreen
import com.example.baseproject.ui.screens.lists.ListsScreen
import com.example.baseproject.ui.screens.navigation.NavigationScreen
import com.example.baseproject.ui.screens.selectioncontrols.SelectionControlsScreen
import com.example.baseproject.ui.screens.textfields.TextFieldsScreen
import com.example.baseproject.ui.screens.theming.ThemingScreen

/**
 * Objeto que define las rutas de navegación del showcase.
 * Centraliza todas las rutas en un solo lugar para facilitar el mantenimiento.
 */
object ShowcaseRoutes {
    const val MAIN = "main"
    const val BUTTONS = "buttons"
    const val TEXT_FIELDS = "textfields"
    const val CARDS = "cards"
    const val LISTS = "lists"
    const val DIALOGS = "dialogs"
    const val NAVIGATION = "navigation"
    const val BOTTOM_SHEETS = "bottomsheets"
    const val APP_BARS = "appbars"
    const val SELECTION_CONTROLS = "selectioncontrols"
    const val ICONS = "icons"
    const val THEMING = "theming"
}

/**
 * Composable principal que configura el NavHost y define todas las rutas de navegación.
 *
 * El NavHost es el contenedor que gestiona la navegación entre diferentes pantallas.
 * Cada ruta (composable) define una pantalla del showcase.
 *
 * @param navController Controlador de navegación que maneja las transiciones entre pantallas
 */
@Composable
fun ShowcaseNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ShowcaseRoutes.MAIN
    ) {
        // Pantalla principal con el grid de categorías
        composable(ShowcaseRoutes.MAIN) {
            MainScreen(
                onCategoryClick = { route ->
                    navController.navigate(route)
                }
            )
        }

        // Pantalla de Botones
        composable(ShowcaseRoutes.BUTTONS) {
            ButtonsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Text Fields
        composable(ShowcaseRoutes.TEXT_FIELDS) {
            TextFieldsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Cards
        composable(ShowcaseRoutes.CARDS) {
            CardsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Lists
        composable(ShowcaseRoutes.LISTS) {
            ListsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Dialogs
        composable(ShowcaseRoutes.DIALOGS) {
            DialogsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Navigation (Drawer)
        composable(ShowcaseRoutes.NAVIGATION) {
            NavigationScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Bottom Sheets y Snackbars
        composable(ShowcaseRoutes.BOTTOM_SHEETS) {
            BottomSheetsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de App Bars
        composable(ShowcaseRoutes.APP_BARS) {
            AppBarsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Selection Controls
        composable(ShowcaseRoutes.SELECTION_CONTROLS) {
            SelectionControlsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Icons y Badges
        composable(ShowcaseRoutes.ICONS) {
            IconsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Theming
        composable(ShowcaseRoutes.THEMING) {
            ThemingScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
