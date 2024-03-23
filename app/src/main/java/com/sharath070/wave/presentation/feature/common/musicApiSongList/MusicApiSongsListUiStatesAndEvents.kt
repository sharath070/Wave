package com.sharath070.wave.presentation.feature.common.musicApiSongList

import com.sharath070.wave.domain.models.musicApiSongListings.MusicApiSongsListing


data class SongsListUiStates(
    val loading: Boolean = false,
    val success: Boolean = false,
    val data: MusicApiSongsListing? = null,
    val error: String? = null
)