package com.example.noteapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.presentation.register.RegistrationViewModel
import com.example.noteapp.presentation.screens.LoginScreen
import com.example.noteapp.presentation.screens.RegistrationScreen

@Composable
fun AppNavigationGraph() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route,
        builder = {
            composable(route = Screens.LoginScreen.route) {
                LoginScreen(navController = navController)

            }
            composable(route = Screens.RegistrationScreen.route) {
                RegistrationScreen(navController = navController, hiltViewModel<RegistrationViewModel>())

            }
        }
    )


}



sealed class Screens(val route: String) {
    data object LoginScreen: Screens("login")
    data object RegistrationScreen: Screens("register")
}