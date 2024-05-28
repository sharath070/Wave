package com.sharath070.wave.presentation.feature.search

import com.sharath070.wave.domain.models.searchResults.SearchResultsItem
import com.sharath070.wave.domain.models.topSearches.TopSearchesItem

data class SearchUiStates(
    val topSearches: List<TopSearchesItem>? = null,
    val loading: Boolean = false,
    val data: Map<String, List<SearchResultsItem>>? = null
)

sealed class SearchUiEvent {
    data class Search(val query: String) : SearchUiEvent()
}