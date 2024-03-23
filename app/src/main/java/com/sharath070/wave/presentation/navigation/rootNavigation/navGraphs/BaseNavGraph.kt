package com.sharath070.wave.presentation.navigation.rootNavigation.navGraphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharath070.wave.presentation.common.baseScreen.BaseScreen
import com.sharath070.wave.presentation.common.playerScreen.PlayerViewModel
import com.sharath070.wave.presentation.navigation.rootNavigation.RootNavGraphs
import com.sharath070.wave.presentation.navigation.rootNavigation.RootScreens
import com.sharath070.wave.presentation.navigation.rootNavigation.sharedViewModel

fun NavGraphBuilder.baseNavGraph(
    navController: NavController
) {
    navigation(
        route = RootNavGraphs.Base.route,
        startDestination = RootScreens.BaseScreen.route
    ) {
        composable(route = RootScreens.BaseScreen.route) {
            val playerViewModel: PlayerViewModel = it.sharedViewModel(navController)
            BaseScreen(playerViewModel)
        }
        composable(route = RootScreens.PlayerScreen.route) {
            val playerViewModel: PlayerViewModel = it.sharedViewModel(navController)
        }
    }
}