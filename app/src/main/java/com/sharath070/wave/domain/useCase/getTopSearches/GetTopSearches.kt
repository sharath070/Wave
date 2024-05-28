package com.sharath070.wave.domain.useCase.getTopSearches

import com.sharath070.wave.common.Resource
import com.sharath070.wave.data.remote.topSearchesDto.toTopSearchItem
import com.sharath070.wave.domain.models.topSearches.TopSearchesItem
import com.sharath070.wave.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTopSearches @Inject constructor(
    private val repository: SearchRepository
) {

    operator fun invoke(): Flow<Resource<List<TopSearchesItem>>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = repository.getTopSearches()
                val mappedData = data.map { it.toTopSearchItem() }
                emit(Resource.Success(mappedData))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            }
        }

}