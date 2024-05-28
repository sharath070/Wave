package com.sharath070.wave.presentation.feature.songListings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sharath070.wave.common.utils.highImageQuality
import com.sharath070.wave.common.utils.parse
import com.sharath070.wave.domain.models.songListings.SongDetails
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@Composable
fun SongsListItem(
    data: SongDetails,
    onSongItemClicked: (String, String) -> Unit,
    onSongOptionsClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clickable { onSongItemClicked(data.id, data.type) },
        colors = CardDefaults.cardColors(bg),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .padding(start = 22.dp, end = 16.dp)
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = data.image.highImageQuality(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = data.title.parse(),
                    color = text,
                    fontFamily = medium,
                    fontSize = 16.sp,
                    maxLines = 1,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
                if (!data.subtitle.isNullOrEmpty()) {
                    Text(
                        text = data.subtitle.parse(),
                        color = textDisabled,
                        fontFamily = regular,
                        fontSize = 12.sp,
                        maxLines = 1,
                        lineHeight = 12.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            IconButton(
                onClick = {
                    onSongOptionsClicked()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "options",
                    tint = text,
                    modifier = Modifier.rotate(90f)
                )
            }
        }
    }
}