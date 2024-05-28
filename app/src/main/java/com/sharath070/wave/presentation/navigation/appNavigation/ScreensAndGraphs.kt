package com.sharath070.wave.presentation.navigation.appNavigation

import kotlinx.serialization.Serializable

sealed class AppScreens() {

    @Serializable
    data object HomeScreen: AppScreens()

    @Serializable
    data class SongsListScreen(
        val id: String,
        val type: String
    ): AppScreens()

    @Serializable
    data object SearchScreen: AppScreens()
}

sealed class AppNavGraphs(val route: String) {
    data object HomeGraph: AppNavGraphs(route = "home_graph")
}