package id.muhammadfaisal.equran.notification.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.muhammadfaisal.equran.notification.CreateNotification
import id.muhammadfaisal.equran.notification.Playable
import id.muhammadfaisal.equran.notification.Track
import id.muhammadfaisal.equran.utils.Constant

class NotificationBroadcast() : BroadcastReceiver(), Playable {

    private var isPlay = false

    override fun onReceive(p0: Context?, p1: Intent?) {
        val track = Track("aok", "aok", 0)
        if (isPlay) {
            onPlayTrack(p0!!, track)
        } else {
            onPauseTrack()
        }
    }

    override fun onPlayTrack(context: Context, track: Track) {
        isPlay = true
        CreateNotification.createNotification(context, track, 0,1,1)
    }

    override fun onPauseTrack() {
        isPlay = false
    }
}