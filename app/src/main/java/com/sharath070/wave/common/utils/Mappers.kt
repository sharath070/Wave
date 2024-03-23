package com.sharath070.wave.common.utils

import com.sharath070.wave.domain.models.musicApiSongListings.MusicApiSongDetails
import com.sharath070.wave.domain.models.player.Music

fun MusicApiSongDetails.toMusic(): Music {
    return Music(
        id = id,
        title = title,
        subtitle = subtitle!!,
        image = image,
        url = songUrl!!,
        hasLyrics = hasLyrics,
        duration = duration!!,
        kbps320 = kbps320!!,
    )
}