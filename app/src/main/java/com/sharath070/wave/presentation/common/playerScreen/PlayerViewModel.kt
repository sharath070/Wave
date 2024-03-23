package com.sharath070.wave.presentation.common.playerScreen

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.wave.common.utils.startService
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.player.service.PlayerServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerServiceConnection: PlayerServiceConnection,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var songsList = mutableStateListOf<Music>()
        private set

    private var serviceEnabled by mutableStateOf(false)

    fun playerEvents(events: PlayerUiEvents) {
        when (events) {
            is PlayerUiEvents.SelectedSongChanged ->
                selectedSongChanged(events.list, events.id, events.activity)
        }
    }

    private fun selectedSongChanged(list: List<Music>, id: String, activity: Activity) {
        if (!serviceEnabled) {
            activity.startService()
            serviceEnabled = true
        }
        viewModelScope.launch {
            val existingSongList = songsList.toList().toSet()
            val newSongList = list.toSet()
            if (existingSongList == newSongList) {
                val index = list.indexOfFirst { it.id == id }
                playerServiceConnection.playAtIndex(index)
            } else  {
                updateSongList(list)
                selectedSongChanged(list, id, activity)
            }
        }
    }

    private fun updateSongList(list: List<Music>) {
        songsList.clear()
        songsList.addAll(list)
        setMediaItems(list)
    }

    private fun setMediaItems(list: List<Music>) {
        playerServiceConnection.addMediaItemsList(list)
    }

    private fun formatDuration(duration: Long): String {
        val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        return String.format("%02d:%02d", minute, seconds)
    }

}