package com.sharath070.wave.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.media3.common.util.UnstableApi
import com.sharath070.wave.player.service.PlayerService
import com.sharath070.wave.presentation.navigation.rootNavigation.RootNavigationComponent
import com.sharath070.wave.presentation.ui.theme.WaveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SplashViewModel by viewModels()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashAnimating
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            WaveTheme(dynamicColor = false) {
                RootNavigationComponent()
            }
        }
    }

    override fun onDestroy() {
        Intent(applicationContext, PlayerService::class.java).also {
            it.action = PlayerService.Actions.STOP.name
            startService(it)
        }
        super.onDestroy()
    }

}