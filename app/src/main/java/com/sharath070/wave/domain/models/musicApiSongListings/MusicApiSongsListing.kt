package com.sharath070.wave.domain.models.musicApiSongListings

data class MusicApiSongsListing(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val type: String,
    val url: String,
    val image: String,
    val duration: String? = null,
    val listCount: String,
    val listType: String,
    val songList: List<MusicApiSongDetails>
)
