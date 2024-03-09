package com.sharath070.wave.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sharath070.wave.presentation.navigation.navGraphs.homeGraph

@Composable
fun NavigationComponent() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraphs.HomeGraph.route) {

        this.homeGraph(navController)

    }

}