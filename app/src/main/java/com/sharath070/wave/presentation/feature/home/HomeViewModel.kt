package com.sharath070.wave.presentation.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.wave.common.Resource
import com.sharath070.wave.domain.useCase.getAlbumSongsListUseCase.GetAlbumSongsList
import com.sharath070.wave.domain.useCase.getPlaylistSongs.GetPlaylistSongs
import com.sharath070.wave.domain.useCase.homePageUseCase.GetHomePageData
import com.sharath070.wave.presentation.feature.common.musicApiSongList.SongsListUiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomePageData: GetHomePageData,
    private val getAlbumSongsList: GetAlbumSongsList,
    private val getPlaylistSongs: GetPlaylistSongs
): ViewModel() {

    var homeUiStates by mutableStateOf(HomeUiStates())
        private set

    var songsListUiStates by mutableStateOf(SongsListUiStates())
        private set

    init {
        getData()
    }

    fun homeUiEvent(event: HomeUiEvents) {
        when (event) {
            is HomeUiEvents.GetAlbumSongs -> {
                getAlbumSongs(event.id)
            }
            is HomeUiEvents.GetPlaylistSongs -> {
                getPlaylistSongsFromApi(event.id)
            }
        }
    }

    private fun getData() {
        homeUiStates = homeUiStates.copy(loading = true)
        viewModelScope.launch {
            getHomePageData("hindi").collect { result ->
                if (result is Resource.Success) {
                    homeUiStates = homeUiStates.copy(
                        success = true,
                        data = result.data ?: mapOf(),
                        loading = false
                    )
                } else if (result is Resource.Error) {
                    homeUiStates = homeUiStates.copy(
                        error = result.msg,
                        loading = false
                    )
                }
            }
        }
    }

    private fun getAlbumSongs(id: String) {
        songsListUiStates = songsListUiStates.copy(loading = true)
        viewModelScope.launch {
            getAlbumSongsList(id).collect { result ->
                if (result is Resource.Success) {
                    songsListUiStates = songsListUiStates.copy(
                        data = result.data,
                        loading = false
                    )
                } else if (result is Resource.Error){
                    songsListUiStates = songsListUiStates.copy(
                        error = result.msg,
                        loading = false
                    )
                }
            }
        }
    }

    private fun getPlaylistSongsFromApi(id: String) {
        songsListUiStates = songsListUiStates.copy(loading = true)
        viewModelScope.launch {
            getPlaylistSongs(id). collect { result ->
                if (result is Resource.Success) {
                    songsListUiStates = songsListUiStates.copy(
                        data = result.data,
                        loading = false
                    )
                } else if (result is Resource.Error){
                    songsListUiStates = songsListUiStates.copy(
                        error = result.msg,
                        loading = false
                    )
                }
            }
        }
    }

}