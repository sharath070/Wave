package com.sharath070.wave.presentation.feature.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharath070.wave.R
import com.sharath070.wave.domain.models.topSearches.TopSearchesItem
import com.sharath070.wave.presentation.ui.theme.regular
import com.sharath070.wave.presentation.ui.theme.text

@Composable
fun TopSearchesItem(
    data: TopSearchesItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(
            id = R.drawable.trending),
            contentDescription = "trending",
            tint = text,
            modifier = Modifier.size(38.dp)
        )
        Text(
            text = data.title,
            color = text,
            fontFamily = regular,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 18.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}