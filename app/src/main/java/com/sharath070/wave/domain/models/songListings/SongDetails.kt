package com.sharath070.wave.domain.models.songListings

data class SongDetails(
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
