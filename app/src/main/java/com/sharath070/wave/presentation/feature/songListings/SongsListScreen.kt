package com.sharath070.wave.presentation.feature.songListings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.swipeable
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.sharath070.wave.R
import com.sharath070.wave.domain.models.player.Music
import com.sharath070.wave.domain.models.songListings.SongsListing
import com.sharath070.wave.presentation.common.playerScreen.PlayerUiEvents
import com.sharath070.wave.presentation.feature.songListings.components.SongsListItem
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.bold
import com.sharath070.wave.presentation.ui.theme.brandColor
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@OptIn(ExperimentalMotionApi::class, ExperimentalWearMaterialApi::class)
@Composable
fun SongsListScreen(
    songsListUiStates: SongsListUiStates = SongsListUiStates(),
    playerUiEvents: (PlayerUiEvents) -> Unit,
    musicList: List<Music>,
    navController: NavController
) {

    val context = LocalContext.current
    val data = remember { mutableStateOf(songsListUiStates.data) }
    val palette = remember { mutableStateOf<Palette?>(null) }

    LaunchedEffect(key1 = data) {
        if (data.value != null) {
            val bitmap = getBitmapFromUrl(data.value!!.image, context)
            palette.value = Palette.from(bitmap).generate()
        }
    }


    if (songsListUiStates.loading || palette.value == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (data.value != null) {

        val subtitle = if (data.value!!.type == "album" && !data.value!!.subtitle.isNullOrEmpty()) data.value!!.subtitle
        else if (data.value!!.type == "playlist" && data.value!!.duration != null) "${data.value!!.listCount} songs • ${data.value!!.duration}"
        else null

        var searchEnabled by remember { mutableStateOf(false) }

        val screenScrollState = rememberScreenScrollState()

        val motionScene = remember {
            context.resources
                .openRawResource(R.raw.songs_listing_motion_scene)
                .readBytes()
                .decodeToString()
        }

        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = screenScrollState.motionProgress,
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
        ) {
            Box(
                Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                getBackgroundColor(palette.value),
                                bg
                            )
                        )
                    )
                    .layoutId("header_color")
                    .swipeable(
                        state = screenScrollState.swipeableState,
                        anchors = screenScrollState.anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) }
                    )
            )

            Box(
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .layoutId("status_bar_padding")
            )

            Box(Modifier.layoutId("top_bar"))

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "navigate back",
                tint = text,
                modifier = Modifier
                    .fillMaxSize()
                    .layoutId("back_btn")
                    .clickable { searchEnabled = false }
                    .swipeable(
                        state = screenScrollState.swipeableState,
                        anchors = screenScrollState.anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) }
                    )
            )

            /*
            Text(
                text = data.title,
                color = text,
                fontSize = 20.sp,
                fontFamily = bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("top_bar_title")
            )*/


            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "search",
                tint = text,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { searchEnabled = true }
                    .layoutId("search_btn")
            )

            AsyncImage(
                model = data.value!!.image,
                contentDescription = null,
                modifier = Modifier
                    .layoutId("image")
                    .swipeable(
                        state = screenScrollState.swipeableState,
                        anchors = screenScrollState.anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) }
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("title_and_subtitle")
                    .swipeable(
                        state = screenScrollState.swipeableState,
                        anchors = screenScrollState.anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) }
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = data.value!!.title,
                    color = text,
                    fontSize = 25.sp,
                    fontFamily = bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                if (!subtitle.isNullOrEmpty()) {
                    Text(
                        text = subtitle,
                        color = text,
                        fontSize = 18.sp,
                        fontFamily = medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .layoutId("action_row")
                    .swipeable(
                        state = screenScrollState.swipeableState,
                        anchors = screenScrollState.anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) }
                    )
            ) {
                ActionRow(
                    addPlaylistClicked = { /*TODO*/ },
                    queuePlaylistClicked = { /*TODO*/ },
                    downloadPlaylistClicked = { /*TODO*/ },
                    shufflePlaylistClicked = { /*TODO*/ }
                )
            }

            Box(Modifier
                .layoutId("content")
                .swipeable(
                    state = screenScrollState.swipeableState,
                    anchors = screenScrollState.anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) }
                )) {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(
                        items = data.value!!.songList,
                        key = { it.id }
                    ) { songDetails ->
                        SongsListItem(
                            data = songDetails,
                            onSongItemClicked = { id: String, type: String ->
                                if (type == "song") {
                                    playerUiEvents(
                                        PlayerUiEvents.SelectedSongChanged(
                                            id = id,
                                            list = musicList.toList()
                                        )
                                    )
                                }
                            },
                            onSongOptionsClicked = {}
                        )
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .layoutId("play_all"),
                onClick = {}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brandColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "play",
                        tint = bg,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

        }
    }
}

@Composable
private fun ActionRow(
    addPlaylistClicked: () -> Unit,
    queuePlaylistClicked: () -> Unit,
    downloadPlaylistClicked: () -> Unit,
    shufflePlaylistClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            IconButton(
                onClick = addPlaylistClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "add playlist",
                    modifier = Modifier.size(25.dp),
                    tint = textDisabled
                )
            }
            IconButton(
                onClick = queuePlaylistClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_to_queue),
                    contentDescription = "add to queue",
                    modifier = Modifier.size(25.dp),
                    tint = textDisabled
                )
            }
            IconButton(
                onClick = downloadPlaylistClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "download",
                    modifier = Modifier.size(25.dp),
                    tint = textDisabled
                )
            }
        }
        IconButton(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            onClick = shufflePlaylistClicked
        ) {
            Icon(
                painter = painterResource(id = R.drawable.shuffle),
                contentDescription = "shuffle",
                tint = textDisabled,
                modifier = Modifier
                    .size(37.dp)
            )
        }
    }
}


suspend fun getBitmapFromUrl(url: String, context: Context): Bitmap {
    val loading = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(url)
        .build()
    val result = (loading.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
}

fun Palette.Swatch.getColor(): Color = Color(rgb)

fun getBackgroundColor(palette: Palette?): Color {
    val dominantSwatch = palette?.dominantSwatch?.getColor()
    val vibrantSwatch = palette?.vibrantSwatch?.getColor()
    val lightVibrantSwatch = palette?.lightVibrantSwatch?.getColor()
    val lightMutedSwatch = palette?.lightMutedSwatch?.getColor()
    val mutedSwatch = palette?.mutedSwatch?.getColor()

    val colorsList = listOf(
        lightVibrantSwatch,
        lightMutedSwatch,
        vibrantSwatch,
        mutedSwatch,
        dominantSwatch
    )
    colorsList.forEach {
        if (it != null) return it
    }
    return bg
}

fun resetDataAndPallete(
    data: MutableState<SongsListing?>,
    palette: MutableState<Palette?>,
    navController: NavController
) {
    data.value = null
    palette.value = null
}
