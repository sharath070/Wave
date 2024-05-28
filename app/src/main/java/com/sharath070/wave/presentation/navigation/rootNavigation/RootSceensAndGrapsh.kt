package com.sharath070.wave.presentation.navigation.rootNavigation

sealed class RootScreens(val route: String) {
    data object BaseScreen: RootScreens(route = "base_screen")
    data object PlayerScreen: RootScreens(route = "player_screen")
}

sealed class RootNavGraphs(val route: String) {
}