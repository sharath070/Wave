package com.sharath070.wave.data.remote.topSearchesDto

import com.sharath070.wave.domain.models.topSearches.TopSearchesItem

fun TopSearchesDtoItem.toTopSearchItem() =
    TopSearchesItem(
        id = id,
        image = image,
        url = perma_url,
        subtitle = subtitle,
        title = title,
        type = type
    )