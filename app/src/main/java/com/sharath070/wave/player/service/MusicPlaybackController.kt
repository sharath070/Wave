package com.sharath070.wave.player.service

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.domain.player.PlaybackController
import com.sharath070.wave.domain.player.PlayerState
import com.sharath070.wave.domain.player.RepeatModeState
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MusicPlaybackController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaControllerFuture: ListenableFuture<MediaController>
) : PlaybackController {

    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMusicIndex: Int?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean,
         repeatModeState: RepeatModeState) -> Unit
    )? = null


    init {
        mediaControllerFuture.addListener(
            { controllerListener() },
            ContextCompat.getMainExecutor(context)
        )
    }

    override fun addMusicList(list: List<Music>) {
        val mediaItems = list.map { it.toMediaItem() }
        mediaController?.run {
            setMediaItems(mediaItems)
            prepare()
        }
    }

    override fun addMusicItem(music: Music) {
        mediaController?.run {
            addMediaItem(music.toMediaItem())
            prepare()
        }
    }

    override fun playSelectedMusic(songIndex: Int) {
        Log.d("controller", "player ready, playing: $songIndex")
        Log.d("controller", "media items count: ${mediaController?.mediaItemCount}")

        mediaController?.apply {
            seekToDefaultPosition(songIndex)
            playWhenReady = true
            prepare()
        }
    }

    override fun playOrPause() {
        mediaController?.run {
            if (isPlaying) pause()
            else play()
        }
    }

    override fun seekToNext() {
        mediaController?.seekToNext()
    }

    override fun seekToPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun toggleShuffleMode() {
        // TODO
    }

    override fun toggleRepeatMode() {
        // TODO
    }

    override fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }

    private fun controllerListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)

                with(player) {
                    mediaControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        currentMediaItemIndex,
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L),
                        shuffleModeEnabled,
                        repeatMode.toRepeatModeState()
                    )
                }
            }
        })
    }

    // Helper functions
    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.IDLE
            Player.STATE_ENDED -> PlayerState.STOPPED
            Player.STATE_BUFFERING -> PlayerState.BUFFERING
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    private fun Int.toRepeatModeState() =
        when (this) {
            Player.REPEAT_MODE_ALL -> RepeatModeState.REPEAT_ALL
            Player.REPEAT_MODE_ONE -> RepeatModeState.REPEAT_ONE
            else -> RepeatModeState.REPEAT_OFF
        }

    private fun Music.toMediaItem(): MediaItem {
        return MediaItem.Builder()
            .setMediaId(url)
            .setUri(url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(subtitle)
                    .setArtworkUri(Uri.parse(image))
                    .build()
            ).build()
    }
}