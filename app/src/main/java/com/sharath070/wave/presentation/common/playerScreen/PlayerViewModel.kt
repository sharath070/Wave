package com.sharath070.wave.presentation.common.playerScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.domain.player.PlaybackController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val musicPlaybackController: PlaybackController,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var songsList = mutableStateListOf<Music>()
        private set

    var playerUiStates by mutableStateOf(PlayerUiStates())
        private set

    init {
        musicControllerCallback()
    }

    fun playerEvents(events: PlayerUiEvents) {
        when (events) {
            is PlayerUiEvents.SelectedSongChanged -> {
                selectedSongChanged(events.list, events.id)
            }
        }
    }

    private fun selectedSongChanged(list: List<Music>, id: String) {
        viewModelScope.launch {
            val existingSongList = songsList.toList().toSet()
            val newSongList = list.toSet()
            if (existingSongList == newSongList) {
                val index = list.indexOfFirst { it.id == id }
                musicPlaybackController.playSelectedMusic(index)
            } else {
                updateSongList(list)
                selectedSongChanged(list, id)
            }
        }
    }

    private fun updateSongList(list: List<Music>) {
        songsList.clear()
        songsList.addAll(list)
        musicPlaybackController.addMusicList(list)
    }

    private fun formatDuration(duration: Long): String {
        val minute = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds = (minute) - minute * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        return String.format("%02d:%02d", minute, seconds)
    }

    private fun musicControllerCallback() {
        musicPlaybackController.mediaControllerCallback =
            { playerState, currentMusicIndex, currentPosition,
              totalDuration, isShuffleEnabled, repeatModeState ->

                findCurrentSong(currentMusicIndex)
                playerUiStates = playerUiStates.copy(
                    playerState = playerState,
                    isShuffleEnabled = isShuffleEnabled,
                    repeatModeState = repeatModeState
                )
            }
    }

    private fun findCurrentSong(currentMusicIndex: Int?) {
        val currentSong = if (currentMusicIndex != null)
            songsList[currentMusicIndex]
        else null
        playerUiStates = playerUiStates.copy(currentSong = currentSong)
    }

    override fun onCleared() {
        super.onCleared()
        musicPlaybackController.destroy()
    }

}