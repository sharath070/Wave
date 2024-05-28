package com.sharath070.wave.di

import com.sharath070.wave.data.repository.HomeRepositoryImpl
import com.sharath070.wave.data.repository.SearchRepositoryImpl
import com.sharath070.wave.domain.repository.HomeRepository
import com.sharath070.wave.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(
        repo: HomeRepositoryImpl
    ): HomeRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        repo: SearchRepositoryImpl
    ): SearchRepository

}