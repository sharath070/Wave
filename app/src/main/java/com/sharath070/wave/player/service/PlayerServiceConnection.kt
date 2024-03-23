package com.sharath070.wave.player.service

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.domain.player.PlaybackController
import com.sharath070.wave.domain.player.PlayerState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(UnstableApi::class)
class PlayerServiceConnection @Inject constructor(
    @ApplicationContext private val context: Context
) : PlaybackController {

    private var mediaController: MediaController? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override var mediaControllerCallback: (
        (playerState: PlayerState,
         currentMediaItem: MediaItem?,
         currentPosition: Long,
         totalDuration: Long,
         isShuffleEnabled: Boolean) -> Unit
    )? = null

    init {
        coroutineScope.launch {
            val sessionToken =
                SessionToken(context, ComponentName(context, PlayerService::class.java))
            mediaController = MediaController.Builder(context, sessionToken)
                .buildAsync().await().apply {
                    addListener(PlayerListener())
                }
            Log.d("@@@@", "mediaController pre@@@@pared")
        }
    }


    private fun Int.toPlayerState(isPlaying: Boolean) =
        when (this) {
            Player.STATE_IDLE -> PlayerState.STOPPED
            Player.STATE_ENDED -> PlayerState.STOPPED
            Player.STATE_BUFFERING -> PlayerState.BUFFERING
            else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
        }

    override fun addMediaItemsList(musics: List<Music>) {
        val mediaItems = musics.map {
            it.toMediaItem()
        }
        mediaController?.run {
            setMediaItems(mediaItems)
            prepare()
        }
    }

    override fun addMediaItem(music: Music) {
        mediaController?.run {
            addMediaItem(music.toMediaItem())
            prepare()
        }
    }

    override fun playAtIndex(mediaItemIndex: Int) {
        mediaController?.apply {
            seekToDefaultPosition(mediaItemIndex)
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


    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun skipNext() {
        mediaController?.seekToNext()
    }

    override fun skipPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun toggleShuffleMode(isEnabled: Boolean) {
        // TODO
    }

    override fun toggleRepeatMode() {
        // TODO
    }

    override fun getCurrentPosition() = mediaController?.currentPosition ?: 0L

    override fun destroy() {
        mediaController = null
        mediaControllerCallback = null
    }

    private inner class PlayerListener : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)

            with(player) {
                mediaControllerCallback?.invoke(
                    playbackState.toPlayerState(isPlaying),
                    currentMediaItem,
                    currentPosition.coerceAtLeast(0L),
                    duration.coerceAtLeast(0L),
                    shuffleModeEnabled
                )
            }
        }
    }

    private fun Music.toMediaItem(): MediaItem {
        return MediaItem.Builder()
            .setMediaId(id)
            .setUri(Uri.parse(url))
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(subtitle)
                    .setArtworkUri(Uri.parse(image))
                    .build()
            ).build()
    }

}