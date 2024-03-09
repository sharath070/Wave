package com.sharath070.wave.data.repository

import android.util.Log
import com.sharath070.wave.data.remote.MusicApi
import com.sharath070.wave.domain.repository.HomeScreenRepository
import javax.inject.Inject

class HomeScreenRepositoryImpl @Inject constructor(
    private val api: MusicApi
): HomeScreenRepository {

    override suspend fun getHomeScreenData(language: String): Map<String, Any> {
        return api.getHomePageData(language)
    }
}