package com.example.baseproject.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object FeaturesMenu : Screen("features_menu")
    object Vibration : Screen("vibration")
    object LocalStorage : Screen("local_storage")
    object Biometric : Screen("biometric")
    object Camera : Screen("camera")
    object Flashlight : Screen("flashlight")
    object Accelerometer : Screen("accelerometer")
    object Battery : Screen("battery")
    object Location : Screen("location")
    object Notifications : Screen("notifications")
}
