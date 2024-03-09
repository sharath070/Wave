package com.sharath070.wave.data.remote


import com.sharath070.wave.common.Constants.API_STRING
import com.sharath070.wave.common.Constants.API_STRING_V4
import com.sharath070.wave.common.Constants.HOME_DATA
import retrofit2.http.GET
import retrofit2.http.Header

interface MusicApi {

    @GET(API_STRING_V4 + HOME_DATA)
    suspend fun getHomePageData(
        @Header("cookie") languages: String = "hindi"
    ): Map<String, Any>

}