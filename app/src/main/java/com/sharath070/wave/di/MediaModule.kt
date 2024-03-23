package com.sharath070.wave.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.C.SPATIALIZATION_BEHAVIOR_AUTO
import androidx.media3.common.C.USAGE_MEDIA
import androidx.media3.common.C.WAKE_MODE_NETWORK
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.sharath070.wave.player.notification.PlayerNotificationManager
import com.sharath070.wave.player.service.PlayerServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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

    /*@OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun providesExoPLayer(
        @ApplicationContext context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer {
        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
    }*/

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun provideExoPlayer(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): ExoPlayer {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(USAGE_MEDIA)
            .setSpatializationBehavior(SPATIALIZATION_BEHAVIOR_AUTO)
            .build()

        return ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context)
                    .setDataSourceFactory {
                        OkHttpDataSource.Factory(okHttpClient)
                            .createDataSource()
                    }
            )
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()
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
        exoPlayer: ExoPlayer
    ): PlayerServiceConnection = PlayerServiceConnection(context)

}