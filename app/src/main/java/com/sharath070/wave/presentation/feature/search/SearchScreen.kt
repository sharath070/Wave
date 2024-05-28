package com.sharath070.wave.presentation.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import com.sharath070.wave.presentation.common.components.CustomSearchField
import com.sharath070.wave.presentation.feature.search.components.ActionChips
import com.sharath070.wave.presentation.feature.search.components.TopSearchesItem
import com.sharath070.wave.presentation.feature.search.widgets.AlbumsAndPlaylistsLazyList
import com.sharath070.wave.presentation.feature.search.widgets.ArtistsAndTopSearchesLazyList
import com.sharath070.wave.presentation.feature.search.widgets.ShowsAndEpisodesLazyList
import com.sharath070.wave.presentation.ui.theme.bg
import com.sharath070.wave.presentation.ui.theme.text
import com.sharath070.wave.presentation.ui.theme.textDisabled

@Composable
fun SearchScreen(
    uiStates: SearchUiStates,
    uiEvents: (SearchUiEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomSearchField(
            placeholder = "Songs, albums or artists",
            onValueChange = {
                if (it.isNotEmpty() && it.trim() == it) {
                    uiEvents(SearchUiEvent.Search(it))
                }
            }
        )

        if (uiStates.topSearches != null && !uiStates.loading && uiStates.data == null) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                items(uiStates.topSearches) {
                    TopSearchesItem(data = it)
                }
            }
        } else if (uiStates.loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiStates.data != null) {

            if (uiStates.data.isNotEmpty()) {

                val categoryList = uiStates.data.keys.toList()
                var selectedCategory by remember { mutableIntStateOf(0) }

                Box(
                    Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .height(0.75.dp)
                        .background(textDisabled.copy(alpha = 0.8f))
                )
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    itemsIndexed(categoryList) { index, category ->
                        ActionChips(
                            category = formatCategoryName(category),
                            enabled = index == selectedCategory,
                            onChipSelected = { selectedCategory = index }
                        )
                    }
                    item { Spacer(modifier = Modifier.padding(8.dp)) }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(0.75.dp)
                        .background(textDisabled.copy(alpha = 0.8f))
                )

                if (
                    categoryList[selectedCategory] == "topquery" || categoryList[selectedCategory] == "songs"
                    || categoryList[selectedCategory] == "artists"
                ) {
                    ArtistsAndTopSearchesLazyList(uiStates.data[categoryList[selectedCategory]]!!)
                } else if (
                    categoryList[selectedCategory] == "playlists" || categoryList[selectedCategory] == "albums"
                ) {
                    AlbumsAndPlaylistsLazyList(searchResults = uiStates.data[categoryList[selectedCategory]]!!)
                } else {
                    ShowsAndEpisodesLazyList(searchResults = uiStates.data[categoryList[selectedCategory]]!!)
                }

            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No results found!", color = text)
                }
            }
        }
    }
}

fun formatCategoryName(category: String): String {
    return if (category == "topquery") {
        "Top"
    } else {
        category.replaceFirstChar { it.uppercase() }
    }
}
