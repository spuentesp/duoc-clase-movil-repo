package com.example.baseproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baseproject.ui.screens.native.FeaturesMenuScreen
import com.example.baseproject.ui.screens.native.features.*

@Composable
fun NativeNav(navController: NavHostController) {
    NavHost(
        navController = navController,
        // TODO(Task 5): reintroducir WelcomeScreen y restaurar Screen.Welcome como startDestination.
        startDestination = Screen.FeaturesMenu.route
    ) {
        composable(Screen.FeaturesMenu.route) {
            FeaturesMenuScreen(
                onFeatureClick = { route ->
                    navController.navigate(route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Vibration.route) {
            VibrationScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.LocalStorage.route) {
            LocalStorageScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Biometric.route) {
            BiometricScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Camera.route) {
            CameraScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Flashlight.route) {
            FlashlightScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Accelerometer.route) {
            AccelerometerScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Battery.route) {
            BatteryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Location.route) {
            LocationScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
