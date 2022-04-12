package id.muhammadfaisal.equran.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.muhammadfaisal.equran.utils.Constant

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.sendBroadcast(Intent("TRACK_TRACK").putExtra(Constant.Key.ACTION_NAME, p1!!.action))
    }
}