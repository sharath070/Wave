package com.sharath070.wave.presentation.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sharath070.wave.common.utils.highImageQuality
import com.sharath070.wave.common.utils.parse
import com.sharath070.wave.domain.models.home.GenericHomeModel
import com.sharath070.wave.presentation.ui.theme.medium
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@Composable
fun HomeAlbumCardItem(
    category: String,
    data: GenericHomeModel,
    onAlbumClicked: (String, String, String) -> Unit
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .padding(start = 10.dp, top = 12.dp)
            .width((screenWidth / 2.5).dp)
            .clickable {
                onAlbumClicked(data.id, data.type, data.url)
            }
    ) {
        Column {
            AlbumImage(category, data.image)
            Text(
                text = data.title.parse(),
                fontSize = 16.sp,
                color = text,
                fontFamily = medium,
                maxLines = 1,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 5.dp, start = 3.dp, end = 3.dp)
            )
            if (!data.subtitle.isNullOrEmpty()) {
                Text(
                    text = data.subtitle.parse(),
                    fontSize = 12.sp,
                    color = textDisabled,
                    fontFamily = regular,
                    maxLines = 1,
                    lineHeight = 12.sp,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 5.dp)
                )
            }
        }
    }
}

@Composable
fun AlbumImage(category: String, image: String) {

    if (category == "Recommended Artist Stations") {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.highImageQuality())
                .crossfade(true)
                .build(),
            contentDescription = null,
            filterQuality = FilterQuality.None,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(10.dp)
                .clip(CircleShape)
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.highImageQuality())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
        )
    }

}
