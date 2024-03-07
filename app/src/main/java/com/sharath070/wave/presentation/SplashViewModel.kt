package com.sharath070.wave.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    var splashAnimating by mutableStateOf(true)
        private set

    init {
        splashAnimationTimeline()
    }

    private fun splashAnimationTimeline() =
        viewModelScope.launch(Dispatchers.Main) {
            delay(1500)
            splashAnimating = false
        }

}