package com.sharath070.wave.presentation.feature.common.musicApiSongList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sharath070.wave.R
import com.sharath070.wave.domain.models.musicApiSongListings.MusicApiSongDetails
import com.sharath070.wave.domain.models.musicApiSongListings.MusicApiSongsListing
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.brandColor
import com.sharath070.wave.presentation.ui.theme.textDisabled
import kotlin.random.Random

@Composable
fun MusicApiSongsListScreen(
    uiState: SongsListUiStates = SongsListUiStates(),
    paddingValues: PaddingValues,
    onSongsItemClicked: (String, String) -> Unit
) {

    if (uiState.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (uiState.data != null) {

        val data = uiState.data
        Column(Modifier.fillMaxWidth()) {
            HeaderSection(
                data = data,
                progress = Random.nextFloat()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bg)
                    .navigationBarsPadding()
            ) {
                item {
                    ActionRow(
                        addPlaylistClicked = {},
                        queuePlaylistClicked = {},
                        downloadPlaylistClicked = {},
                        shufflePlaylistClicked = {},
                        playAllClicked = {}
                    )
                }
                items(
                    items = data.songList,
                    key = { it.id }
                ) { songDetails ->
                    MusicApiSongsListItem(
                        data = songDetails,
                        onSongItemClicked = { id: String, type: String ->
                            onSongsItemClicked(id, type)
                        },
                        onSongOptionsClicked = {}
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(paddingValues.calculateBottomPadding()))
                }
            }
        }

    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
private fun HeaderSection(
    data: MusicApiSongsListing,
    progress: Float
) {

    val subtitle = if (data.type == "album" && data.subtitle != null) data.subtitle
    else if (data.type == "playlist" && data.duration != null) "${data.listCount} songs • ${data.duration}"
    else null

    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.songs_listing_motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = 0f,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 0.dp, max = 500.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("pic")
        )

        /*Box(
            modifier = Modifier
                .layoutId("container_box")
        )
        IconButton(
            modifier = Modifier
                .statusBarsPadding()
                .layoutId("back_btn"),
            onClick = {},
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "back",
                tint = text,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(Color(0x22000000))
                .layoutId("text_box")
                .statusBarsPadding()
        )

        Text(
            text = data.title,
            color = text,
            fontSize = 27.sp,
            fontFamily = headline,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .layoutId("title_text")
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                color = text,
                fontSize = 18.sp,
                fontFamily = medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .layoutId("subtitle_text")
            )
        }
        ActionRow(modifier = Modifier.layoutId("action_row"))
*/
    }
}

@Composable
private fun ActionRow(
    addPlaylistClicked: () -> Unit,
    queuePlaylistClicked: () -> Unit,
    downloadPlaylistClicked: () -> Unit,
    shufflePlaylistClicked: () -> Unit,
    playAllClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 5.dp)
            .padding(bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            IconButton(
                onClick = {
                    addPlaylistClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "add playlist",
                    modifier = Modifier.size(25.dp),
                    tint = textDisabled
                )
            }
            IconButton(
                onClick = {
                    queuePlaylistClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_to_queue),
                    contentDescription = "add to queue",
                    modifier = Modifier.size(25.dp),
                    tint = textDisabled
                )
            }
            IconButton(
                onClick = {
                    downloadPlaylistClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = "download",
                    modifier = Modifier.size(25.dp),
                    tint = textDisabled
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                onClick = {
                    shufflePlaylistClicked()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.shuffle),
                    contentDescription = "shuffle",
                    tint = textDisabled,
                    modifier = Modifier
                        .size(37.dp)
                )
            }
            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                onClick = {
                    playAllClicked()
                }
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
