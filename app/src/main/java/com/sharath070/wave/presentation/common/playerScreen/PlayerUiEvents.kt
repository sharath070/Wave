package com.sharath070.wave.presentation.common.playerScreen

import android.app.Activity
import com.sharath070.wave.domain.models.player.Music

sealed class PlayerUiEvents {
    data class SelectedSongChanged(
        val id: String,
        val list: List<Music>,
        val activity: Activity
    ) : PlayerUiEvents()
}