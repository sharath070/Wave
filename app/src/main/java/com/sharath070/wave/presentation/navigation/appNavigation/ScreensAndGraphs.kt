package com.sharath070.wave.presentation.navigation.appNavigation

sealed class AppScreens(val route: String) {
    data object HomeScreen: AppScreens(route = "home_screen")
    data object SearchScreen: AppScreens(route = "search_screen")
    data object LibraryScreen: AppScreens(route = "library_screen")
    data object MusicApiSongsListScreen: AppScreens(route = "music_api_songs_list_screen")
}

sealed class AppNavGraphs(val route: String) {
    data object HomeGraph: AppNavGraphs(route = "home_graph")
}