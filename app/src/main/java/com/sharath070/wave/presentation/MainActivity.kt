package com.sharath070.wave.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.sharath070.wave.data.remote.MusicApi
import com.sharath070.wave.domain.repository.HomeScreenRepository
import com.sharath070.wave.domain.useCase.homePageUseCase.GetHomePageData
import com.sharath070.wave.presentation.navigation.NavigationComponent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

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

        setContent {
            NavigationComponent()
        }
    }
}