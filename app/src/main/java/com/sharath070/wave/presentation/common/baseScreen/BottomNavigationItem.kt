package com.sharath070.wave.presentation.common.baseScreen

import androidx.compose.ui.graphics.painter.Painter
import com.sharath070.wave.presentation.navigation.appNavigation.AppScreens

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val route: AppScreens
)
