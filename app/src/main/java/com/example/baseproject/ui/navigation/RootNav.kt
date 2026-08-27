package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.RootMenuScreen

/**
 * NavHost raíz. Tres destinos top-level:
 *  - "root"     → menú principal
 *  - "material" → MaterialNav (showcase de componentes)
 *  - "native"   → NativeNav (capacidades nativas)
 *  - "exercises"→ ExercisesNav (mini-apps para practicar)
 */
@Composable
fun RootNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "root") {
        composable("root") { RootMenuScreen(navController) }
        composable("material") { MaterialNav() }
        composable("native") { NativeNav() }
        composable("exercises") { ExercisesNav() }
    }
}
