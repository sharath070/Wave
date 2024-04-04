package com.sharath070.wave.di

import android.content.ComponentName
import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.sharath070.wave.player.notification.PlayerNotificationManager
import com.sharath070.wave.player.service.MediaSessionCallback
import com.sharath070.wave.player.service.PlayerService
import com.sharath070.wave.player.service.MusicPlaybackController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MediaModule {

    @Provides
    @Singleton
    fun providesAudioAttributes(): AudioAttributes =
        AudioAttributes.Builder()
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(USAGE_MEDIA)
            .build()

    @Provides
    @Singleton
    fun providesExoPlayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideMediaLibrarySessionCallback() = MediaSessionCallback()

    @Provides
    @Singleton
    fun provideMediaControllerFuture(
        @ApplicationContext context: Context
    ): ListenableFuture<MediaController> {
        val sessionToken = SessionToken(context, ComponentName(context, PlayerService::class.java))
        return MediaController.Builder(context, sessionToken).buildAsync()
    }


    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun providesNotificationProvider(
        @ApplicationContext context: Context,
        exoPlayer: ExoPlayer
    ): PlayerNotificationManager = PlayerNotificationManager(context, exoPlayer)


    @Provides
    @Singleton
    fun providesServiceConnection(
        @ApplicationContext context: Context,
        mediaControllerFuture: ListenableFuture<MediaController>
    ): MusicPlaybackController = MusicPlaybackController(context, mediaControllerFuture)

}