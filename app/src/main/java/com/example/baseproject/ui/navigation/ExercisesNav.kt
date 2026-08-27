package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baseproject.ui.screens.exercises.ExercisesMenuScreen
import com.example.baseproject.ui.screens.exercises.e01_counter.CounterExerciseScreen
import com.example.baseproject.ui.screens.exercises.e02_list.ListExerciseScreen
import com.example.baseproject.ui.screens.exercises.e03_form.FormExerciseScreen
import com.example.baseproject.ui.screens.exercises.e04_navigation.NavigationExerciseScreen
import com.example.baseproject.ui.screens.exercises.e05_viewmodel.ViewModelExerciseScreen
import com.example.baseproject.ui.screens.exercises.e06_repository.RepositoryExerciseScreen
import com.example.baseproject.ui.screens.exercises.e07_network.NetworkExerciseScreen
import com.example.baseproject.ui.screens.exercises.e08_room.RoomExerciseScreen

@Composable
fun ExercisesNav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "exercises/menu") {
        composable("exercises/menu") { ExercisesMenuScreen(navController) }
        composable("exercises/counter") { CounterExerciseScreen() }
        composable("exercises/list") { ListExerciseScreen() }
        composable("exercises/form") { FormExerciseScreen() }
        composable("exercises/navigation") { NavigationExerciseScreen() }
        composable("exercises/viewmodel") { ViewModelExerciseScreen() }
        composable("exercises/repository") { RepositoryExerciseScreen() }
        composable("exercises/network") { NetworkExerciseScreen() }
        composable("exercises/room") { RoomExerciseScreen() }
    }
}
