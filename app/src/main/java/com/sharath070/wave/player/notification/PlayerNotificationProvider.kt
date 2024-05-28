package com.sharath070.wave.player.notification

import android.app.Notification
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.google.common.collect.ImmutableList
import com.sharath070.wave.R
import com.sharath070.wave.common.Constants.NOTIFICATION_ID
import com.sharath070.wave.common.Constants.PLAYER_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@UnstableApi
class PlayerNotificationProvider @Inject constructor(
    @ApplicationContext private val context: Context
): MediaNotification.Provider {

    override fun createNotification(
        mediaSession: MediaSession,
        customLayout: ImmutableList<CommandButton>,
        actionFactory: MediaNotification.ActionFactory,
        onNotificationChangedCallback: MediaNotification.Provider.Callback
    ): MediaNotification {

        buildNotification(mediaSession)
        val notification = NotificationCompat.Builder(context, PLAYER_CHANNEL_ID)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        return MediaNotification(NOTIFICATION_ID, notification)
    }

    override fun handleCustomCommand(
        session: MediaSession,
        action: String,
        extras: Bundle
    ) = false

    private fun buildNotification(mediaSession: MediaSession) {
        PlayerNotificationManager.Builder(
            context,
            NOTIFICATION_ID,
            PLAYER_CHANNEL_ID
        )
            .setMediaDescriptionAdapter(
                NotificationAdapter(
                    context = context,
                    pendingIntent = mediaSession.sessionActivity
                )
            )
            .setSmallIconResourceId(R.mipmap.ic_launcher)
            .build()
            .apply {
                setMediaSessionToken(mediaSession.sessionCompatToken)
                setUsePreviousActionInCompactView(true)
                setUseNextActionInCompactView(true)
                setPriority(NotificationCompat.PRIORITY_DEFAULT)
                setPlayer(mediaSession.player)
            }
    }

}