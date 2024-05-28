package com.sharath070.wave.presentation.feature.songListings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.wave.common.Resource
import com.sharath070.wave.domain.useCase.getAlbumSongsListUseCase.GetAlbumSongsList
import com.sharath070.wave.domain.useCase.getPlaylistSongsUseCase.GetPlaylistSongs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsListingViewModel @Inject constructor(
    private val getAlbumSongsList: GetAlbumSongsList,
    private val getPlaylistSongs: GetPlaylistSongs
) : ViewModel() {

    var songsListUiStates by mutableStateOf(SongsListUiStates())
        private set

    fun songListingUiEvent(event: SongsListUiEvents) {
        when (event) {
            is SongsListUiEvents.GetAlbumSongs -> {
                getAlbumSongs(event.id)
            }
            is SongsListUiEvents.GetPlaylistSongs -> {
                getPlaylistSongsFromApi(event.id)
            }
        }
    }

    private fun getAlbumSongs(id: String) {
        viewModelScope.launch {
            getAlbumSongsList(id).collect { result ->
                if (result is Resource.Success) {
                    songsListUiStates = songsListUiStates.copy(
                        data = result.data,
                        loading = false
                    )
                } else if (result is Resource.Error) {
                    songsListUiStates = songsListUiStates.copy(
                        error = result.msg,
                        loading = false
                    )
                }
            }
        }
    }

    private fun getPlaylistSongsFromApi(id: String) {
        viewModelScope.launch {
            getPlaylistSongs(id).collect { result ->
                if (result is Resource.Success) {
                    songsListUiStates = songsListUiStates.copy(
                        data = result.data,
                        loading = false
                    )
                } else if (result is Resource.Error) {
                    songsListUiStates = songsListUiStates.copy(
                        error = result.msg,
                        loading = false
                    )
                }
            }
        }
    }

}
