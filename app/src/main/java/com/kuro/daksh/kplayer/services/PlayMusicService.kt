package com.kuro.daksh.kplayer.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class PlayMusicService:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}