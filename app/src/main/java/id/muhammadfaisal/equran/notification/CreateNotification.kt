package id.muhammadfaisal.equran.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.muhammadfaisal.equran.R
import id.muhammadfaisal.equran.utils.Constant

class CreateNotification {
    companion object {
        const val CHANNEL_ID = "CHN_ID_10010"
        const val CHANNEL_PLAY = "CHN_PLY_10010"
        const val CHANNEL_NEXT = "CHN_NXT_10010"

        const val ACTION_PREVIOUS = "ACT_PREV"
        const val ACTION_PLAY = "ACT_PLAY"

        lateinit var notification: Notification

        fun createNotification(context: Context, track: Track, playButton: Int, position: Int, size: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManagerCompat = NotificationManagerCompat.from(context)
                val mediaSessionCompat = MediaSessionCompat(context, CreateNotification::class.java.simpleName)

                val icon = BitmapFactory.decodeResource(context.resources, track.image)

                val intent = Intent(context, NotificationActionService::class.java).setAction(ACTION_PLAY)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                //Build Notification
                this.notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_round_search_24)
                    .setContentTitle(track.title)
                    .setContentText(track.description)
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)
                    .addAction(R.drawable.ic_round_play_arrow_24, "Play", pendingIntent)
                    .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0, 1, 2).setMediaSession(mediaSessionCompat.sessionToken))
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build()

                notificationManagerCompat.notify(1, notification)
            }
        }
    }
}