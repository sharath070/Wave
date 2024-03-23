package com.sharath070.wave.domain.player

import androidx.media3.common.MediaItem
import com.sharath070.wave.domain.models.player.Music

interface PlaybackController {
    var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMediaItem: MediaItem?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean) -> Unit
    )?

    fun addMediaItemsList(musics: List<Music>)

    fun addMediaItem(music: Music)

    fun playAtIndex(mediaItemIndex: Int)

    fun playOrPause()

    fun seekTo(position: Long)

    fun skipNext()

    fun skipPrevious()

    fun toggleShuffleMode(isEnabled: Boolean)

    fun toggleRepeatMode()

    fun getCurrentPosition(): Long

    fun destroy()
}