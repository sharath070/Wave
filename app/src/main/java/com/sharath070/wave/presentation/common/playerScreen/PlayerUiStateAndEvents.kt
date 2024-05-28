package com.sharath070.wave.presentation.common.playerScreen

import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.domain.player.PlayerState
import com.sharath070.wave.domain.player.RepeatModeState

data class PlayerUiStates(
    val playerState: PlayerState = PlayerState.IDLE,
    val totalDuration: Long = 0L,
    val currentSong: Music? = null,
    val isShuffleEnabled: Boolean = false,
    val repeatModeState: RepeatModeState = RepeatModeState.REPEAT_OFF
)

sealed class PlayerUiEvents {
    data class SelectedSongChanged(
        val id: String,
        val list: List<Music>
    ) : PlayerUiEvents()
}