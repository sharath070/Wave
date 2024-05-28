package com.sharath070.wave.presentation.feature.search.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sharath070.wave.domain.models.searchResults.SearchResultsItem
import com.sharath070.wave.presentation.feature.search.components.ArtistsAndTopSearchResultsItem
import com.sharath070.wave.presentation.ui.theme.text

@Composable
fun ArtistsAndTopSearchesLazyList(
    searchResults: List<SearchResultsItem>
) {
    if (searchResults.isNotEmpty()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 15.dp)
        ) {
            items(
                items = searchResults,
                key = { it.id }
            ) {data ->
                ArtistsAndTopSearchResultsItem(data = data)
            }
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