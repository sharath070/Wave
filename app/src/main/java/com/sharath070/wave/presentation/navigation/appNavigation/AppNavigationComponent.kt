package com.sharath070.wave.presentation.navigation.appNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sharath070.wave.presentation.common.playerScreen.PlayerViewModel
import com.sharath070.wave.presentation.navigation.appNavigation.navGraphs.homeGraph

@Composable
fun AppNavigationComponent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    playerViewModel: PlayerViewModel
) {

    NavHost(navController = navController, startDestination = AppNavGraphs.HomeGraph.route) {

        this.homeGraph(navController, paddingValues,playerViewModel)

    }

}
