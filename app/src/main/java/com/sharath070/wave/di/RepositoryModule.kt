package com.sharath070.wave.di

import com.sharath070.wave.data.repository.HomeScreenRepositoryImpl
import com.sharath070.wave.domain.repository.HomeScreenRepository
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
        repo: HomeScreenRepositoryImpl
    ): HomeScreenRepository

}