package com.sharath070.wave.presentation.feature.songListings

import com.sharath070.wave.domain.models.songListings.SongsListing


data class SongsListUiStates(
    val loading: Boolean = true,
    val success: Boolean = false,
    val data: SongsListing? = null,
    val error: String? = null
)

sealed class SongsListUiEvents {
    data class GetAlbumSongs(val id: String): SongsListUiEvents()
    data class GetPlaylistSongs(val id: String): SongsListUiEvents()
}
