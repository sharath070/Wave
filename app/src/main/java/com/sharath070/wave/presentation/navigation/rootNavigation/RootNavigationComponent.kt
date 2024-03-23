package com.sharath070.wave.presentation.navigation.rootNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sharath070.wave.presentation.navigation.rootNavigation.navGraphs.baseNavGraph

@Composable
fun RootNavigationComponent() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = RootNavGraphs.Base.route) {
        this.baseNavGraph(navController)
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}