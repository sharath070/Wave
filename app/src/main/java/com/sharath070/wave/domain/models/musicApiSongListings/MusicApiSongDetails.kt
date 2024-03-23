package com.sharath070.wave.domain.models.musicApiSongListings

data class MusicApiSongDetails(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val url: String,
    val image: String,
    val type: String,
    val hasLyrics: Boolean,
    val songUrl: String? = null,
    val duration: Long? = null,
    val albumUrl: String? = null,
    val kbps320: Boolean? = null
)
