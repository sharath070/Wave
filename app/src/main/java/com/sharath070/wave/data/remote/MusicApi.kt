package com.sharath070.wave.data.remote


import com.sharath070.wave.common.Constants.ALBUM_DETAILS
import com.sharath070.wave.common.Constants.API_STRING
import com.sharath070.wave.common.Constants.API_STRING_V4
import com.sharath070.wave.common.Constants.HOME_DATA
import com.sharath070.wave.common.Constants.PLAYLIST_DETAILS
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MusicApi {

    @GET(API_STRING_V4 + HOME_DATA)
    suspend fun getHomePageData(
        @Header("cookie") languages: String = "hindi"
    ): Map<String, Any>

    @GET(API_STRING_V4 + ALBUM_DETAILS)
    suspend fun getAlbumSongsList(
        @Query("albumid") albumId: String
    ): Map<String, Any>

    @GET(API_STRING_V4 + PLAYLIST_DETAILS)
    suspend fun getPlaylistSongs(
        @Query("listid") playlistId: String
    ): Map<String, Any>
}