package com.sharath070.wave.presentation.common.baseScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.swipeable
import com.sharath070.wave.R
import com.sharath070.wave.domain.player.PlayerState
import com.sharath070.wave.presentation.common.miniPlayer.MiniPlayer
import com.sharath070.wave.presentation.common.playerScreen.PlayerUiEvents
import com.sharath070.wave.presentation.common.playerScreen.PlayerUiStates
import com.sharath070.wave.presentation.navigation.appNavigation.AppNavGraphs
import com.sharath070.wave.presentation.navigation.appNavigation.AppNavigationComponent
import com.sharath070.wave.presentation.navigation.appNavigation.AppScreens
import com.sharath070.wave.presentation.rememberPlayerSwipeState
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.layer
import com.sharath070.wave.presentation.ui.theme.navItemDisabled

@OptIn(ExperimentalMotionApi::class, ExperimentalWearMaterialApi::class)
@Composable
fun BaseScreen(
    playerUiStates: PlayerUiStates,
    playerUiEvents: (PlayerUiEvents) -> Unit,
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.base_screen_motion_scene)
            .readBytes()
            .decodeToString()
    }
    val playerSwipeState = rememberPlayerSwipeState()

    val miniPlayerSwipeOrientation =
        if (playerUiStates.playerState != PlayerState.IDLE )
            Orientation.Vertical else Orientation.Horizontal

    val miniPlayerSwipeThreshold =
        if (playerUiStates.playerState == PlayerState.IDLE ) 100f else 0.3f

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = playerSwipeState.motionProgress,
        modifier = Modifier.fillMaxSize()
    ) {

        Box(Modifier.layoutId("content")) {
            AppNavigationComponent(playerUiEvents, navController)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("mini_player")
                .swipeable(
                    state = playerSwipeState.swipeableState,
                    anchors = playerSwipeState.anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) }
                )
        ) {
            MiniPlayer(playerUiStates = playerUiStates)
            Log.d("playerState", playerUiStates.playerState.name)
        }

        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("full_player")
                .swipeable(
                    state = playerSwipeState.swipeableState,
                    anchors = playerSwipeState.anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) }
                )
        )

        Box(Modifier.layoutId("bottom_bar")) {
            BottomNavBar(navController = navController)
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
            unselectedIcon = painterResource(id = R.drawable.home),
            route = AppScreens.HomeScreen
        ),
        BottomNavigationItem(
            title = "Search",
            selectedIcon = painterResource(id = R.drawable.search_filled),
            unselectedIcon = painterResource(id = R.drawable.search),
            route = AppScreens.SearchScreen
        ),
        BottomNavigationItem(
            title = "Library",
            selectedIcon = painterResource(id = R.drawable.library_filled),
            unselectedIcon = painterResource(id = R.drawable.library),
            route = AppScreens.HomeScreen
        ),
        BottomNavigationItem(
            title = "My Account",
            selectedIcon = painterResource(id = R.drawable.account_filled),
            unselectedIcon = painterResource(id = R.drawable.account),
            route = AppScreens.HomeScreen
        )
    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .background(bg)
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(layer)
            .padding(top = 1.5.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            containerColor = bg,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            navItems.forEachIndexed { index, item ->
                val isSelected = selectedItemIndex == index
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.route){
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title,
                            modifier = Modifier.size(
                                when (item.title) {
                                    "My Account" -> 36.dp
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
}