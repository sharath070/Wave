package com.sharath070.wave.common.utils

import android.app.Activity
import android.content.Intent
import com.sharath070.wave.player.service.PlayerService


fun Activity.startService() {
    Intent(this, PlayerService::class.java).also {
        //it.action = PlayerService.Actions.START.name
        startService(it)
    }
}