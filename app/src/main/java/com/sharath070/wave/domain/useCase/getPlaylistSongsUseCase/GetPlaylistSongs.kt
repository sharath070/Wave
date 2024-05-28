package com.sharath070.wave.domain.useCase.getPlaylistSongsUseCase

import com.sharath070.wave.common.Resource
import com.sharath070.wave.common.utils.decode
import com.sharath070.wave.common.utils.highImageQuality
import com.sharath070.wave.common.utils.parse
import com.sharath070.wave.domain.models.songListings.SongDetails
import com.sharath070.wave.domain.models.songListings.SongsListing
import com.sharath070.wave.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPlaylistSongs @Inject constructor(
    private val homeRepo: HomeRepository
) {

    operator fun invoke(playlistId: String): Flow<Resource<SongsListing>> =
        flow {
            try {
                val data = homeRepo.getPlaylistSongs(playlistId)
                val id = data["id"] as? String
                val title = data["title"] as? String
                val subtitle = data["subtitle"] as? String
                val currentListType = data["type"] as? String
                val url = data["perma_url"] as? String
                val image = data["image"] as? String
                val listCount = data["list_count"] as? String
                val listType = data["list_type"] as? String

                val list = data["list"] as? List<*>
                val songList = mutableListOf<SongDetails>()
                list?.forEach { item ->
                    if (item is Map<*, *>) {
                        val songId = item["id"] as? String
                        val songTitle = item["title"] as? String
                        val songSubtitle = item["subtitle"] as? String
                        val songUrl = item["perma_url"] as? String
                        val songImage = item["image"] as? String
                        val type = item["type"] as? String
                        val moreInfo = item["more_info"] as? Map<*, *>
                        val hasLyrics = moreInfo?.get("has_lyrics") as? String
                        val encryptedSongUrl = moreInfo?.get("encrypted_media_url") as? String
                        val kbps320 = moreInfo?.get("320kbps") as? String
                        val duration = moreInfo?.get("duration") as? String
                        val albumUrl = moreInfo?.get("album_url") as? String
                        if (
                            songId != null && songTitle != null && hasLyrics != null
                            && songUrl != null && songImage != null && type != null
                        ) {
                            if (
                                (type == "song" && encryptedSongUrl != null && duration != null
                                        && albumUrl != null) || type != "song"
                            ) {
                                songList.add(
                                    SongDetails(
                                        id = songId,
                                        title = songTitle.parse(),
                                        subtitle = songSubtitle?.parse(),
                                        url = songUrl,
                                        image = songImage.highImageQuality(),
                                        type = type,
                                        hasLyrics = hasLyrics == "true",
                                        songUrl = if (encryptedSongUrl != null) decode(encryptedSongUrl)
                                        else null,
                                        duration = convertDurationToLong(duration),
                                        albumUrl = albumUrl,
                                        kbps320 = if (kbps320 != null) kbps320 == "true" else null
                                    )
                                )
                            }
                        }
                    }
                }
                if (
                    id != null && title != null && currentListType != null
                    && url != null && image != null &&
                    listCount != null && listType != null
                ) {
                    val formattedData = SongsListing(
                        id = id,
                        title = title,
                        subtitle = subtitle,
                        type = currentListType,
                        url = url,
                        image = image,
                        listCount = listCount,
                        listType = listType,
                        songList = songList
                    )
                    emit(Resource.Success(formattedData))
                } else {
                    emit(Resource.Error("An unexpected error occurred"))
                }

            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            }
        }

    private fun convertDurationToLong(input: String?): Long? {
        val indexOfDot = input?.indexOf('.')

        val duration = if (indexOfDot == -1) input
        else if (indexOfDot != null) input.substring(0, indexOfDot)
        else null

        return duration?.toLong()
    }

}