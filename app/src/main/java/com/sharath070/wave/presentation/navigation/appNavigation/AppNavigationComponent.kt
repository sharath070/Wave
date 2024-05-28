package com.sharath070.wave.presentation.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sharath070.wave.common.utils.toMusic
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.presentation.common.playerScreen.PlayerUiEvents
import com.sharath070.wave.presentation.feature.home.HomeScreen
import com.sharath070.wave.presentation.feature.home.HomeViewModel
import com.sharath070.wave.presentation.feature.search.SearchScreen
import com.sharath070.wave.presentation.feature.search.SearchViewModel
import com.sharath070.wave.presentation.feature.songListings.SongsListScreen
import com.sharath070.wave.presentation.feature.songListings.SongsListUiEvents
import com.sharath070.wave.presentation.feature.songListings.SongsListingViewModel
import com.sharath070.wave.presentation.navigation.rootNavigation.sharedViewModel

@Composable
fun AppNavigationComponent(
    playerUiEvents: (PlayerUiEvents) -> Unit,
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen
    ) {

        composable<AppScreens.HomeScreen> {

            val viewModel: HomeViewModel = hiltViewModel()
            val uiStates = viewModel.homeUiStates

            HomeScreen(
                uiStates = uiStates,
                navController = navController
            )

        }

        composable<AppScreens.SongsListScreen> { entry ->

            val args = entry.toRoute<AppScreens.SongsListScreen>()

            val viewModel: SongsListingViewModel = hiltViewModel()
            if (args.type == "album") {
                viewModel.songListingUiEvent(SongsListUiEvents.GetAlbumSongs(args.id))
            } else if (args.type == "playlist") {
                viewModel.songListingUiEvent(SongsListUiEvents.GetPlaylistSongs(args.id))
            }
            val songsListUiStates = viewModel.songsListUiStates
            val musicList = mutableListOf<Music>()

            if (songsListUiStates.data != null) {
                songsListUiStates.data.songList.forEach {
                    if (it.type == "song") {
                        musicList.add(it.toMusic())
                    }
                }
            }

            SongsListScreen(
                songsListUiStates = songsListUiStates,
                playerUiEvents = playerUiEvents,
                musicList = musicList,
                navController = navController
            )
        }

        composable<AppScreens.SearchScreen> {
            val viewModel: SearchViewModel = hiltViewModel()
            val uiStates = viewModel.searchUiStates
            SearchScreen(
                uiStates = uiStates,
                uiEvents = viewModel::searchUiEvent
            )
        }

    }

}
