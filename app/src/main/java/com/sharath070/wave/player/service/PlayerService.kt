package com.sharath070.wave.player.service

import android.content.Intent
import android.util.Log
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerService: MediaSessionService() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    @Inject
    lateinit var mediaSessionCallback: MediaSessionCallback

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSession.Builder(this, exoPlayer)
            .setCallback(mediaSessionCallback)
            .build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == Actions.STOP.name)
            stopPlayerService()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.stop()
            player.release()
            release()
        }
        super.onDestroy()
        Log.d("service", "service destroyed")
    }

    private fun stopPlayerService() {
        mediaSession?.run {
            player.stop()
            player.release()
            release()
        }
        stopSelf()
    }

    enum class Actions {
        STOP
    }
}