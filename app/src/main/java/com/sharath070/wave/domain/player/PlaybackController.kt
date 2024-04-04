package com.sharath070.wave.domain.player

import com.sharath070.wave.domain.models.player.Music

interface PlaybackController {

    var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMediaItemIndex: Int?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean,
         repeatModeState: RepeatModeState) -> Unit
    )?

    fun addMusicList(list: List<Music>)
    fun addMusicItem(music: Music)
    fun playSelectedMusic(songIndex: Int)
    fun playOrPause()
    fun seekToNext()
    fun seekToPrevious()
    fun seekTo(position: Long)
    fun toggleShuffleMode()
    fun toggleRepeatMode()
    fun destroy()
}