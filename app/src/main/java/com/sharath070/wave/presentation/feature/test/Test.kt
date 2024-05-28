package com.sharath070.wave.presentation.feature.test

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.sharath070.wave.common.utils.decode
import com.sharath070.wave.presentation.ui.theme.brandColor
import java.lang.Float.max
import kotlin.math.min

@Preview
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun CollapsableToolbar() {
    val swipeableState = rememberSwipeableState(
        initialValue = 0,
        animationSpec = tween()
    )
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenAreaOffset = 400

    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    val swipeAreaHeight = screenHeight - screenAreaOffset
    val anchors by remember { mutableStateOf(mapOf(0f to 1, -swipeAreaHeight to 0)) }
    val swipeProgress by remember {
        mutableFloatStateOf(swipeableState.offset.value / -swipeAreaHeight)
    }
    val motionProgress by remember {
        mutableFloatStateOf(max(0f, min(swipeProgress, 1f)))
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = motionProgress.toString())
        Box(
            Modifier.width(70.dp)
                .height(40.dp)
                .background(brandColor)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) }
                )
        )
    }
}