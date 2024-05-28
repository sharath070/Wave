package com.sharath070.wave.presentation.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharath070.wave.common.Resource
import com.sharath070.wave.domain.useCase.getSearchResultUseCase.GetSearchResult
import com.sharath070.wave.domain.useCase.getTopSearches.GetTopSearches
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getTopSearches: GetTopSearches,
    private val getSearchResult: GetSearchResult
): ViewModel() {

    private val searchText = MutableStateFlow("")

    var searchUiStates by mutableStateOf(SearchUiStates())
        private set

    init {
        getTopSearches()
    }

    fun searchUiEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.Search -> searchResults(event.query)
        }
    }

    private fun getTopSearches() {
        viewModelScope.launch {
            getTopSearches.invoke().collect{ result ->
                if (result is Resource.Success) {
                    searchUiStates = searchUiStates.copy(topSearches = result.data)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchResults(query: String) {
        searchUiStates = searchUiStates.copy(loading = true)
        searchText.value = query
        viewModelScope.launch {
            searchText.debounce(1000)
                .collect {
                    getSearchResult(it).collect{ result ->
                        if (result is Resource.Success) {
                            searchUiStates = searchUiStates.copy(loading = false, data = result.data)
                        }
                    }
                }
        }
    }

}