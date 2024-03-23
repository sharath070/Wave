package com.sharath070.wave.presentation.common.baseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sharath070.wave.R
import com.sharath070.wave.presentation.common.playerScreen.PlayerViewModel
import com.sharath070.wave.presentation.navigation.appNavigation.AppNavigationComponent
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.navItemDisabled

@Composable
fun BaseScreen(
    playerViewModel: PlayerViewModel,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { values ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .navigationBarsPadding()
        ) {
            AppNavigationComponent(navController, values, playerViewModel)
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val navItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = painterResource(id = R.drawable.home_filled),
            unselectedIcon = painterResource(id = R.drawable.home)
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = painterResource(id = R.drawable.search_filled),
            unselectedIcon = painterResource(id = R.drawable.search)
        ),
        BottomNavigationItem(
            title = "YT Music",
            selectedIcon = painterResource(id = R.drawable.yt_filled),
            unselectedIcon = painterResource(id = R.drawable.yt)
        ),
        BottomNavigationItem(
            title = "Your Library",
            selectedIcon = painterResource(id = R.drawable.library2_filled),
            unselectedIcon = painterResource(id = R.drawable.library2)
        )
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier
            .navigationBarsPadding()
            .height(63.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        bg.copy(0.6f),
                        bg.copy(0.8f),
                        bg
                    ),
                    startY = -0.4f
                )
            ),
    ) {
        navItems.forEachIndexed { index, item ->
            val isSelected = selectedItemIndex == index
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedItemIndex = index
                },
                icon = {
                    Icon(
                        painter = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(
                            when (item.title) {
                                "YT Music" -> 38.dp
                                "Search" -> 33.dp
                                else -> 26.dp
                            }
                        )
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedIndicatorColor = Color.Transparent,
                    selectedTextColor = Color.White,
                    unselectedIconColor = navItemDisabled,
                    unselectedTextColor = navItemDisabled,
                    disabledTextColor = navItemDisabled,
                    disabledIconColor = navItemDisabled
                )
            )
        }
    }
}