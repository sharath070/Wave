package com.sharath070.wave.presentation.feature.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun ShowsAndEpisodesSearchResultItem(
    data: SearchResultsItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(91.dp)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = data.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            Modifier.fillMaxSize()
                .padding(start = 20.dp)
        ) {
            Text(
                text = data.title.parse(),
                color = text,
                fontFamily = medium,
                fontSize = 18.sp,
                maxLines = 1,
                lineHeight = 16.sp,
                overflow = TextOverflow.Ellipsis
            )
            if (!data.subtitle.isNullOrEmpty() || !data.description.isNullOrEmpty()) {
                Text(
                    text = (data.description?.parse() ?: data.subtitle?.parse())!!,
                    fontFamily = regular,
                    color = textDisabled,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 5.dp)
                )
            }
        }
    }
}