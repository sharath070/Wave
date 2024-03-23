package com.sharath070.wave.presentation.feature.home

import com.sharath070.wave.domain.models.home.GenericHomeModel

data class HomeUiStates(
    val loading: Boolean = false,
    val success: Boolean = false,
    val data: Map<String, List<GenericHomeModel>> = mapOf(),
    val error: String? = null
)

sealed class HomeUiEvents {
    data class GetAlbumSongs(val id: String): HomeUiEvents()
    data class GetPlaylistSongs(val id: String): HomeUiEvents()
}

