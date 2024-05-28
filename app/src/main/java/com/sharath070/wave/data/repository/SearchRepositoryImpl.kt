package com.sharath070.wave.data.repository

import com.sharath070.wave.data.remote.MusicApi
import com.sharath070.wave.data.remote.topSearchesDto.TopSearchesDto
import com.sharath070.wave.data.remote.topSearchesDto.toTopSearchItem
import com.sharath070.wave.domain.models.topSearches.TopSearchesItem
import com.sharath070.wave.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: MusicApi
): SearchRepository {

    override suspend fun getTopSearches(): TopSearchesDto {
        return api.getTopSearches()
    }

    override suspend fun getSearchResults(query: String): Map<String, Any> {
        return api.getSearchResults(query)
    }

}