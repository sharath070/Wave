package com.sharath070.wave.presentation.navigation.navGraphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharath070.wave.presentation.feature.home.HomeScreen
import com.sharath070.wave.presentation.feature.home.HomeViewModel
import com.sharath070.wave.presentation.navigation.NavGraphs
import com.sharath070.wave.presentation.navigation.Screens

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(
        route = NavGraphs.HomeGraph.route,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(route = Screens.HomeScreen.route) {

            val viewModel: HomeViewModel = hiltViewModel()
            val uiStates = viewModel.uiStates

            HomeScreen(
                uiStates = uiStates,
                onAlbumClicked = { id: String, type: String, url: String -> }
            )

        }
    }
}