package com.sharath070.wave.presentation.feature.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sharath070.wave.common.utils.parse
import com.sharath070.wave.domain.models.searchResults.SearchResultsItem
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@Composable
fun ArtistsAndTopSearchResultsItem(
    data: SearchResultsItem,
    onSongClick: (String) -> Unit,
    onAlbumClick: (String, String) -> Unit,
    onPlaylistClick: (String, String) -> Unit,
    onArtistClick: (String) -> Unit,
    onShowClick: (String) -> Unit,
    onEpisodeClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                when (data.type) {
                    "album" -> onPlaylistClick(data.id, data.type)
                    "playlist" -> onPlaylistClick(data.id, data.type)
                }
            }
            .fillMaxWidth()
            .height(61.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = data.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(
                    if (data.type == "artists") CircleShape
                    else RoundedCornerShape(5.dp)
                )
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize(),
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
            if (
                formatTopSearchResultsSubtitle(
                    subtitle = data.subtitle,
                    description = data.description,
                    type = data.type
                ) != null
            ) {
                Text(
                    text = formatTopSearchResultsSubtitle(
                        subtitle = data.subtitle,
                        description = data.description,
                        type = data.type
                    )!!,
                    color = textDisabled,
                    fontFamily = regular,
                    fontSize = 12.sp,
                    maxLines = 1,
                    lineHeight = 12.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

fun formatTopSearchResultsSubtitle(
    subtitle: String?,
    description: String?,
    type: String
): String? {
    when (type) {
        "artist" -> return null
        "songs" -> return subtitle ?: description
        else -> {
            val formattedType = if (type.lowercase().startsWith("ep")) "EP"
            else type.replaceFirstChar { it.uppercase() }
            return if (!subtitle.isNullOrEmpty()) "$formattedType • ${subtitle.parse()}"
            else formattedType
        }
    }
}