package com.example.baseproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baseproject.ui.screens.WelcomeScreen
import com.example.baseproject.ui.screens.FeaturesMenuScreen
import com.example.baseproject.ui.screens.features.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.FeaturesMenu.route)
                }
            )
        }

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
