package com.example.noteapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.presentation.screens.DetailScreen
import com.example.noteapp.presentation.screens.HomeScreen
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
                RegistrationScreen(navController = navController)
            }
            composable(route = Screens.HomeScreen.route+ "?firstLog={firstLog}") {
                val data = it.arguments?.getString("firstLog")
                HomeScreen(navController = navController, data)
                BackHandler {
                    navController.clearBackStack(Screens.HomeScreen.route)
                }
            }
            composable(route = Screens.DetailScreen.route + "?id={id}") {
                val data = it.arguments?.getString("id")
                DetailScreen(navController = navController, data)
            }
        }
    )
}



sealed class Screens(val route: String) {
    data object LoginScreen: Screens("login")
    data object RegistrationScreen: Screens("register")
    data object HomeScreen:Screens("home")
    data object DetailScreen: Screens("detail")
}