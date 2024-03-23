package com.sharath070.wave.domain.repository

interface HomeScreenRepository {

    suspend fun getHomeScreenData(language: String): Map<String, Any>
    suspend fun getAlbumSongsList(albumId: String): Map<String, Any>
    suspend fun getPlaylistSongs(playlistId: String): Map<String, Any>

}