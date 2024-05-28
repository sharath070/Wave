package com.sharath070.wave.domain.useCase.homePageUseCase

import com.sharath070.wave.common.Resource
import com.sharath070.wave.domain.models.home.GenericHomeModel
import com.sharath070.wave.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetHomePageData @Inject constructor(
    private val homeRepo: HomeRepository
) {

    operator fun invoke(languages: String): Flow<Resource<Map<String, List<GenericHomeModel>>>> =
        flow {
            emit(Resource.Loading())
            try {
                val formattedDataMap = mutableMapOf<String, List<GenericHomeModel>>()
                val data = homeRepo.getHomeScreenData(languages)
                val modules = data["modules"] as Map<*, *>
                modules.forEach { (category, contentTemp) ->
                    val content = contentTemp as? Map<*, *>
                    when (category) {
                        "new_trending" -> formatTrendingMusic(data, formattedDataMap, content)
                        "top_playlists" -> formatTopPlaylists(data, formattedDataMap, content)
                        "new_albums" -> formatNewAlbums(data, formattedDataMap, content)
                        "charts" -> formatCharts(data, formattedDataMap, content)
                        "artist_recos" -> formatRecommendations(data, formattedDataMap, content)
                        else -> {
                            if (
                                category is String && category.contains("promo:vx:data:")
                                && content is Map<*, *>
                            ) {
                                formatPromos(content, data, category, formattedDataMap)
                            }
                        }
                    }
                }

                emit(Resource.Success(formattedDataMap))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            }
        }

    private fun formatPromos(
        content: Map<*, *>,
        data: Map<String, Any>,
        category: Any?,
        formattedDataMap: MutableMap<String, List<GenericHomeModel>>
    ) {
        val keyName = content["title"] as? String
        if (keyName != null) {
            val categoryName = content["title"] as? String
            val promos = mutableListOf<GenericHomeModel>()
            val info = data[category] as? List<*>
            info?.forEach { item ->
                if (item is Map<*, *>) {
                    val image = item["image"] as? String
                    val url = item["perma_url"] as? String
                    val title = item["title"] as? String
                    val subtitle = item["subtitle"] as? String
                    val type = item["type"] as? String
                    val id = item["id"] as? String
                    if (
                        title != null && image != null && url != null
                        && id != null && type != null && categoryName != null
                    ) {
                        promos.add(
                            GenericHomeModel(
                                title = title,
                                subtitle = subtitle,
                                image = image,
                                url = url,
                                type = type,
                                id = id
                            )
                        )
                    }
                }
            }
            if (promos.isNotEmpty() && categoryName != null) {
                formattedDataMap[categoryName] = promos
            }
        }
    }

    private fun formatRecommendations(
        data: Map<String, Any>,
        formattedDataMap: MutableMap<String, List<GenericHomeModel>>,
        content: Map<*, *>?
    ) {
        val categoryName = content?.get("title") as? String
        val recommendations = mutableListOf<GenericHomeModel>()
        val info = data["artist_recos"] as? List<*>
        info?.forEach { item ->
            if (item is Map<*, *>) {
                val image = item["image"] as? String
                val title = item["title"] as? String
                val subtitle = item["subtitle"] as? String
                val url = item["perma_url"] as? String
                val type = item["type"] as? String
                val id = item["id"] as? String
                if (
                    title != null && subtitle != null && image != null
                    && url != null && id != null && type != null && categoryName != null
                ) {
                    recommendations.add(
                        GenericHomeModel(
                            title = title,
                            subtitle = subtitle,
                            image = image,
                            url = url,
                            type = type,
                            id = id
                        )
                    )
                }
            }
        }
        if (recommendations.isNotEmpty() && categoryName != null) {
            formattedDataMap[categoryName] = recommendations
        }
    }

    private fun formatCharts(
        data: Map<String, Any>,
        formattedDataMap: MutableMap<String, List<GenericHomeModel>>,
        content: Map<*, *>?
    ) {
        val categoryName = content?.get("title") as? String
        val chartItems = mutableListOf<GenericHomeModel>()
        val info = data["charts"] as? List<*>
        info?.forEach { item ->
            if (item is Map<*, *>) {
                val image = item["image"] as? String
                val title = item["title"] as? String
                val url = item["perma_url"] as? String
                val type = item["type"] as? String
                val id = item["id"] as? String
                if (
                    title != null && image != null && url != null &&
                    id != null && type != null && categoryName != null
                ) {
                    chartItems.add(
                        GenericHomeModel(
                            title = title,
                            image = image,
                            url = url,
                            type = type,
                            id = id
                        )
                    )
                }
            }
        }
        if (chartItems.isNotEmpty() && categoryName != null) {
            formattedDataMap[categoryName] = chartItems
        }
    }

    private fun formatNewAlbums(
        data: Map<String, Any>,
        formattedDataMap: MutableMap<String, List<GenericHomeModel>>,
        content: Map<*, *>?
    ) {
        val categoryName = content?.get("title") as? String
        val newAlbumItems = mutableListOf<GenericHomeModel>()
        val info = data["new_albums"] as? List<*>
        info?.forEach { item ->
            if (item is Map<*, *>) {
                val title = item["title"] as? String
                val image = item["image"] as? String
                val url = item["perma_url"] as? String
                val type = item["type"] as? String
                val id = item["id"] as? String
                val moreInfo = item["more_info"] as? Map<*, *>
                val artistMap = moreInfo?.get("artistMap") as? Map<*, *>
                val artists = artistMap?.get("artists") as? List<*>
                val artistNames = mutableListOf<String>()
                artists?.forEach { artist ->
                    if (artist is Map<*, *>) {
                        val artistName = artist["name"] as? String
                        if (artistName != null) {
                            artistNames.add(artistName)
                        }
                    }
                }
                val artistNamesStr = artistNames.toString()
                if (
                    title != null && image != null && url != null &&
                    id != null && type != null && categoryName != null
                ) {
                    newAlbumItems.add(
                        GenericHomeModel(
                            title = title,
                            image = image,
                            url = url,
                            subtitle = artistNamesStr.substring(1, (artistNamesStr.length - 1)),
                            type = type,
                            id = id
                        )
                    )
                }
            }
        }
        if (newAlbumItems.isNotEmpty() && categoryName != null) {
            formattedDataMap[categoryName] = newAlbumItems
        }
    }

    private fun formatTopPlaylists(
        data: Map<String, Any>,
        formattedDataMap: MutableMap<String, List<GenericHomeModel>>,
        content: Map<*, *>?
    ) {
        val categoryName = content?.get("title") as? String
        val topPlaylistItems = mutableListOf<GenericHomeModel>()
        val info = data["top_playlists"] as? List<*>
        info?.forEach { item ->
            if (item is Map<*, *>) {
                val title = item["title"] as? String
                val image = item["image"] as? String
                val url = item["perma_url"] as? String
                val type = item["type"] as? String
                val id = item["id"] as? String
                val moreInfo = item["more_info"] as? Map<*, *>
                val songsCount = moreInfo?.get("song_count") as? String ?: ""
                if (
                    title != null && image != null && url != null &&
                    id != null && type != null && categoryName != null
                ) {
                    topPlaylistItems.add(
                        GenericHomeModel(
                            image = image,
                            url = url,
                            title = title,
                            subtitle = "$songsCount songs",
                            type = type,
                            id = id
                        )
                    )
                }
            }
        }
        if (topPlaylistItems.isNotEmpty() && categoryName != null) {
            formattedDataMap[categoryName] = topPlaylistItems
        }
    }

    private fun formatTrendingMusic(
        data: Map<String, Any>,
        formattedDataMap: MutableMap<String, List<GenericHomeModel>>,
        content: Map<*, *>?
    ) {
        val categoryName = content?.get("title") as? String
        val trendingItems = mutableListOf<GenericHomeModel>()
        val info = data["new_trending"] as? List<*>
        info?.forEach { item ->
            if (item is Map<*, *>) {
                val title = item["title"] as? String
                val image = item["image"] as? String
                val url = item["perma_url"] as? String
                val type = item["type"] as? String
                val id = item["id"] as? String
                val moreInfo = item["more_info"] as? Map<*, *>
                val artistMap = moreInfo?.get("artistMap") as? Map<*, *>
                val artists = artistMap?.get("artists") as? List<*>
                val artistNames = mutableListOf<String>()
                artists?.forEach { artist ->
                    if (artist is Map<*, *>) {
                        val artistName = artist["name"] as? String
                        if (artistName != null) {
                            artistNames.add(artistName)
                        }
                    }
                }
                val artistNamesStr = artistNames.toString()
                if (
                    title != null && image != null && url != null &&
                    id != null && type != null && categoryName != null
                ) {
                    trendingItems.add(
                        GenericHomeModel(
                            image = image,
                            url = url,
                            title = title,
                            subtitle = artistNamesStr.substring(1, (artistNamesStr.length - 1)),
                            type = type,
                            id = id
                        )
                    )
                }
            }
        }
        if (trendingItems.isNotEmpty() && categoryName != null) {
            formattedDataMap[categoryName] = trendingItems
        }
    }

}