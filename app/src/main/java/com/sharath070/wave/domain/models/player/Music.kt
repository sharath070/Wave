package com.sharath070.wave.domain.models.player

data class Music(
    val id: String,
    val title: String,
    val subtitle: String,
    val image: String,
    val hasLyrics: Boolean,
    val url: String,
    val duration: Long,
    val kbps320: Boolean
)
