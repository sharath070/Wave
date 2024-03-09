package com.sharath070.wave.domain.models.homeModels

import androidx.compose.runtime.Stable
import java.text.FieldPosition

@Stable
data class GenericHomeModel(
    val id: String,
    val image: String,
    val url: String,
    val subtitle: String? = null,
    val title: String,
    val type: String
)