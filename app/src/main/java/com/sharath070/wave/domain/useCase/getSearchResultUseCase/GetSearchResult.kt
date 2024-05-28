package com.sharath070.wave.domain.useCase.getSearchResultUseCase

import android.util.Log
import com.sharath070.wave.common.Resource
import com.sharath070.wave.common.utils.highImageQuality
import com.sharath070.wave.domain.models.searchResults.SearchResultsItem
import com.sharath070.wave.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSearchResult @Inject constructor(
    private val repository: SearchRepository
) {

    operator fun invoke(query: String): Flow<Resource<Map<String, List<SearchResultsItem>>>> =
        flow {
            emit(Resource.Loading())
            try {

                val data = repository.getSearchResults(query)
                val formattedData = getFormattedData(data)
                emit(Resource.Success(formattedData))

            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            }
        }

    private fun getPositionOrderMap(resultMap: Map<String, Any>): Map<Double, String> {
        val positionOrderMap: MutableMap<Double, String> = mutableMapOf()
        resultMap.forEach { (category, contentTemp) ->
            val content = contentTemp as? Map<*, *>
            val position = content?.get("position") as? Double
            if (position != null) {
                positionOrderMap[position] = category
            }
        }
        return positionOrderMap
    }

    private fun getFormattedData(resultMap: Map<String, Any>): Map<String, List<SearchResultsItem>> {

        val formattedData = mutableMapOf<String, List<SearchResultsItem>>()

        val positionOrderMap: Map<Double, String> = getPositionOrderMap(resultMap)
        val positionList = positionOrderMap.keys.sorted().toList()
        positionList.forEach { position ->

            val dataItemsList = mutableListOf<SearchResultsItem>()
            val category = positionOrderMap[position]

            val content = resultMap[category] as? Map<*, *>
            val data = content?.get("data") as? List<*>
            data?.forEach { dataItem ->
                if (dataItem is Map<*, *>) {
                    val id = dataItem["id"] as? String
                    val title = dataItem["title"] as? String
                    val subtitle = dataItem["subtitle"] as? String
                    val description = dataItem["description"] as? String
                    val type = dataItem["type"] as? String
                    val url = dataItem["perma_url"] as? String
                    val imageUrl = dataItem["image"] as? String

                    if (
                        id != null && title != null && imageUrl != null
                        && type != null && url != null
                    ) {
                        dataItemsList.add(
                            SearchResultsItem(
                                id, title, subtitle, description, type, url, imageUrl.highImageQuality()
                            )
                        )
                    }
                }
            }
            if (dataItemsList.isNotEmpty() && category != null) {
                formattedData[category] = dataItemsList
            }

        }
        return formattedData
    }

}