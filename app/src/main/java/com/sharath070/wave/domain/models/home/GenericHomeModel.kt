package com.sharath070.wave.domain.models.home

import androidx.compose.runtime.Stable

@Stable
data class GenericHomeModel(
    val id: String,
    val image: String,
    val url: String,
    val subtitle: String? = null,
    val title: String,
    val type: String
)