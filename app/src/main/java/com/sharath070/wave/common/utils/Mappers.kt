package com.sharath070.wave.common.utils

import com.sharath070.wave.domain.models.songListings.SongDetails
import com.sharath070.wave.domain.models.player.Music

fun SongDetails.toMusic(): Music {
    return Music(
        id = id,
        title = title,
        subtitle = subtitle ?: "",
        image = image,
        url = songUrl ?: "",
        hasLyrics = hasLyrics,
        duration = duration ?: 0L,
        kbps320 = kbps320 ?: false,
    )
}