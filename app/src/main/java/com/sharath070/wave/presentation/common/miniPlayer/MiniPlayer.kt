package com.sharath070.wave.presentation.common.miniPlayer

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sharath070.wave.common.utils.highImageQuality
import com.sharath070.wave.common.utils.parse
import com.sharath070.wave.domain.player.PlayerState
import com.sharath070.wave.presentation.common.playerScreen.PlayerUiStates
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@Composable
fun MiniPlayer(
    playerUiStates: PlayerUiStates
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(bg)
            .padding(horizontal = 20.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
                model = playerUiStates.currentSong?.image?.highImageQuality(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(3.dp))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = playerUiStates.currentSong?.title?.parse() ?: "N/A",
                color = text,
                fontFamily = medium,
                fontSize = 15.sp,
                maxLines = 1,
                lineHeight = 15.sp,
                overflow = TextOverflow.Ellipsis
            )
            if (playerUiStates.currentSong != null) {
                Text(
                    text = playerUiStates.currentSong.subtitle.parse(),
                    color = textDisabled,
                    fontFamily = regular,
                    fontSize = 11.sp,
                    maxLines = 1,
                    lineHeight = 11.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = text)
        }
    }
}