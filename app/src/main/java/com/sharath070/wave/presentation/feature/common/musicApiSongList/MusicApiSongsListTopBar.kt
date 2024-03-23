package com.sharath070.wave.presentation.feature.common.musicApiSongList

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import com.sharath070.wave.domain.models.musicApiSongListings.MusicApiSongsListing
import com.sharath070.wave.presentation.ui.theme.bold
import com.sharath070.wave.presentation.ui.theme.text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicApiSongsListTopBar(
    data: MusicApiSongsListing?
) {
    TopAppBar(
        title = {
            if (data != null) {
                Text(
                    text = data.title,
                    fontFamily = bold,
                    color = text,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {

                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "navigate back",
                    tint = text
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .layoutId("top_bar")
    )
}