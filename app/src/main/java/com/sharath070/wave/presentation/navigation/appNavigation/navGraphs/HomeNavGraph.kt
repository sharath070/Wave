package com.sharath070.wave.presentation.navigation.appNavigation.navGraphs

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sharath070.wave.common.utils.toMusic
import com.sharath070.wave.domain.models.musicApiSongListings.MusicApiSongDetails
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.presentation.common.playerScreen.PlayerUiEvents
import com.sharath070.wave.presentation.common.playerScreen.PlayerViewModel
import com.sharath070.wave.presentation.feature.common.musicApiSongList.MusicApiSongsListScreen
import com.sharath070.wave.presentation.feature.home.HomeScreen
import com.sharath070.wave.presentation.feature.home.HomeUiEvents
import com.sharath070.wave.presentation.feature.home.HomeViewModel
import com.sharath070.wave.presentation.navigation.appNavigation.AppNavGraphs
import com.sharath070.wave.presentation.navigation.appNavigation.AppScreens
import com.sharath070.wave.presentation.navigation.rootNavigation.sharedViewModel
import kotlin.math.log

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    paddingValues: PaddingValues,
    playerViewModel: PlayerViewModel
) {
    navigation(
        route = AppNavGraphs.HomeGraph.route,
        startDestination = AppScreens.HomeScreen.route
    ) {
        composable(route = AppScreens.HomeScreen.route) {

            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            val uiStates = viewModel.homeUiStates

            HomeScreen(
                uiStates = uiStates,
                paddingValues = paddingValues,
                onAlbumClicked = { id: String, type: String, url: String ->
                    if (type == "album") {
                        viewModel.homeUiEvent(HomeUiEvents.GetAlbumSongs(id))
                        navController.navigate(AppScreens.MusicApiSongsListScreen.route)
                    } else if (type == "playlist") {
                        viewModel.homeUiEvent(HomeUiEvents.GetPlaylistSongs(id))
                        navController.navigate(AppScreens.MusicApiSongsListScreen.route)
                    }
                }
            )

        }

        composable(route = AppScreens.MusicApiSongsListScreen.route) { entry ->

            val viewModel = entry.sharedViewModel<HomeViewModel>(navController)
            val uiStates = viewModel.songsListUiStates
            val musicList = mutableListOf<Music>()
            val activity = LocalContext.current as Activity

            if (uiStates.data != null) {
                uiStates.data.songList.map {
                    if (it.type == "song") {
                        musicList.add(it.toMusic())
                    }
                }
            }

            MusicApiSongsListScreen(
                uiState = uiStates,
                paddingValues = paddingValues
            ) { songId: String, type: String ->
                if (type == "song") {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
                        playerViewModel.playerEvents(
                            PlayerUiEvents.SelectedSongChanged(
                                id = songId,
                                list = musicList.toList(),
                                activity = activity
                            )
                        )
                    }
                }
            }

        }
    }
}
