package com.sharath070.wave.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sharath070.wave.presentation.navigation.navComposables.homeNavComposable

@Composable
fun NavigationComponent() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

        composable(route = Screens.HomeScreen.route) {
            it.homeNavComposable()
        }

    }

}