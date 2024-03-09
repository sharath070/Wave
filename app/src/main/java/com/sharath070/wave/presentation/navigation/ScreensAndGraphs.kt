package com.sharath070.wave.presentation.navigation

sealed class Screens(val route: String) {
    data object HomeScreen: Screens(route = "home_screen")
    data object SearchScreen: Screens(route = "search_screen")
    data object LibraryScreen: Screens(route = "library_screen")
}

sealed class NavGraphs(val route: String) {
    data object HomeGraph: NavGraphs(route = "home_graph")
}