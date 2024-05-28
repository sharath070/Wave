package com.sharath070.wave.data.remote


import com.sharath070.wave.common.Constants.ALBUM_DETAILS
import com.sharath070.wave.common.Constants.API_STRING
import com.sharath070.wave.common.Constants.HOME_DATA
import com.sharath070.wave.common.Constants.PLAYLIST_DETAILS
import com.sharath070.wave.common.Constants.SEARCH_RESULTS
import com.sharath070.wave.common.Constants.TOP_SEARCHES
import com.sharath070.wave.data.remote.topSearchesDto.TopSearchesDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MusicApi {

    @GET(API_STRING + HOME_DATA)
    suspend fun getHomePageData(
        @Header("cookie") languages: String = "hindi"
    ): Map<String, Any>

    @GET(API_STRING + ALBUM_DETAILS)
    suspend fun getAlbumSongsList(
        @Query("albumid") albumId: String
    ): Map<String, Any>

    @GET(API_STRING + PLAYLIST_DETAILS)
    suspend fun getPlaylistSongs(
        @Query("listid") playlistId: String
    ): Map<String, Any>

    @GET(API_STRING + TOP_SEARCHES)
    suspend fun getTopSearches(): TopSearchesDto

    @GET(API_STRING + SEARCH_RESULTS)
    suspend fun getSearchResults(
        @Query("query") query: String
    ): Map<String, Any>

}