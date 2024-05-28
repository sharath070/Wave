package com.sharath070.wave.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.SwipeableState
import androidx.wear.compose.material.rememberSwipeableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalWearMaterialApi::class)
@SuppressLint("ComposableNaming")
@Composable
fun rememberPlayerSwipeState() : PlayerSwipeState {

    val swipeableState = rememberSwipeableState(
        initialValue = 0,
        animationSpec = tween()
    )
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenAreaOffset = 400

    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    val swipeAreaHeight = screenHeight - screenAreaOffset

    return remember(
        swipeableState,
        swipeAreaHeight,
        coroutineScope
    ) {
        PlayerSwipeState(
            swipeableState,
            swipeAreaHeight,
            coroutineScope
        )
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
class PlayerSwipeState(
    val swipeableState: SwipeableState<Int>,
    private val swipeAreaHeight: Float,
    private val coroutineScope: CoroutineScope
) {
    val anchors = mapOf(0f to 0, -swipeAreaHeight to 1)
    private val swipeProgress @Composable get() = swipeableState.offset.value / -swipeAreaHeight
    val motionProgress @Composable get() = max(0f, min(swipeProgress, 1f))
    val isPlayerOpened @Composable get() = swipeableState.currentValue == 1

    fun openPlayer() = coroutineScope.launch { swipeableState.animateTo(1) }
    fun closePlayer() = coroutineScope.launch { swipeableState.animateTo(0) }

}