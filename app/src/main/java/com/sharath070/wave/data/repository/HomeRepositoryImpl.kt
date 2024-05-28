package com.sharath070.wave.data.repository

import com.sharath070.wave.data.remote.MusicApi
import com.sharath070.wave.data.remote.topSearchesDto.toTopSearchItem
import com.sharath070.wave.domain.models.topSearches.TopSearchesItem
import com.sharath070.wave.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: MusicApi
): HomeRepository {

    override suspend fun getHomeScreenData(language: String): Map<String, Any> {
        return api.getHomePageData(language)
    }

    override suspend fun getAlbumSongsList(albumId: String): Map<String, Any> {
        return api.getAlbumSongsList(albumId)
    }

    override suspend fun getPlaylistSongs(playlistId: String): Map<String, Any> {
        return api.getPlaylistSongs(playlistId)
    }

}