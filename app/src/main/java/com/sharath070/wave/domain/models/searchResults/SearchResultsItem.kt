package com.sharath070.wave.domain.models.searchResults

data class SearchResultsItem(
    val id: String,
    val title: String,
    val subtitle: String?,
    val description: String?,
    val type: String,
    val url: String,
    val image: String,
    val hasLyrics: Boolean? = null,
    val duration: Long? = null,
    val albumUrl: String? = null,
    val kbps320: Boolean? = null
)
