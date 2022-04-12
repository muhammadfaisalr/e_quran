package id.muhammadfaisal.equran.notification

import android.content.Context

interface Playable {
    fun onPlayTrack(context: Context, track: Track)
    fun onPauseTrack()
}