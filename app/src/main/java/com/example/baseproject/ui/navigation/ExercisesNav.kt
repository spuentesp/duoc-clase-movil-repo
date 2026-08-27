package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.exercises.ExercisesMenuScreen

/**
 * NavGraph de la sección Ejercicios Android.
 * Las rutas concretas de cada mini-app se completan en Task 8.
 */
@Composable
fun ExercisesNav(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "exercises/menu") {
        composable("exercises/menu") { ExercisesMenuScreen(navController) }
    }
}
