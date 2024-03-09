package com.sharath070.wave.domain.repository

interface HomeScreenRepository {

    suspend fun getHomeScreenData(language: String): Map<String, Any>

}