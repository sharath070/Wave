package com.sharath070.wave.data.remote.topSearchesDto

data class TopSearchesDtoItem(
    val explicit_content: String,
    val id: String,
    val image: String,
    val mini_obj: Boolean,
    val more_info: MoreInfo,
    val perma_url: String,
    val subtitle: String,
    val title: String,
    val type: String
)