package com.sharath070.wave.presentation.feature.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sharath070.wave.domain.models.home.GenericHomeModel
import com.sharath070.wave.presentation.feature.home.components.HomeAlbumCardItem
import com.sharath070.wave.presentation.navigation.appNavigation.AppScreens
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.bold
import com.sharath070.wave.presentation.ui.theme.text

@Composable
fun HomeScreen(
    uiStates: HomeUiStates,
    navController: NavController
) {
    if (uiStates.loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val categoryList = uiStates.data.keys.toList()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
                .statusBarsPadding()
        ) {
            items(categoryList) { category ->
                val data = uiStates.data[category]
                CategoryAlbumRow(
                    category = category,
                    data = data,
                    onAlbumClicked = { id: String, type: String, url: String ->
                        if (type == "album" || type == "playlist") {
                            navController.navigate(
                                AppScreens.SongsListScreen(
                                    id = id,
                                    type = type
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryAlbumRow(
    category: String,
    data: List<GenericHomeModel>?,
    onAlbumClicked: (String, String, String) -> Unit
) {
    if (!data.isNullOrEmpty()) {
        Column {
            Text(
                text = category,
                fontSize = 24.sp,
                color = text,
                fontFamily = bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 21.dp)
                    .padding(top = 34.dp)
            )
            LazyRow {
                item { Spacer(modifier = Modifier.width(4.dp)) }
                items(
                    items = data,
                    key = { it.url }
                ) { dataItem ->
                    HomeAlbumCardItem(
                        category = category,
                        data = dataItem,
                        onAlbumClicked = { id: String, type: String, url: String ->
                            Log.d("albumUrl", "$id\n$type\n$url")
                            onAlbumClicked(id, type, url)
                        }
                    )
                }
                item { Spacer(modifier = Modifier.width(10.dp)) }
            }
        }
    }
}