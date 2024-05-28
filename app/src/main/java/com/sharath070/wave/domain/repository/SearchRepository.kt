package com.sharath070.wave.domain.repository

import com.sharath070.wave.data.remote.topSearchesDto.TopSearchesDto
import com.sharath070.wave.domain.models.topSearches.TopSearchesItem

interface SearchRepository {

    suspend fun getTopSearches(): TopSearchesDto
    suspend fun getSearchResults(query: String): Map<String, Any>
}