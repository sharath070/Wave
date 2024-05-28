package com.sharath070.wave.common

import com.sharath070.wave.BuildConfig

object Constants {

    //Api
    const val BASE_URL = BuildConfig.baseUrl
    const val API_STRING = BuildConfig.apiStr
    const val HOME_DATA = BuildConfig.homeData
    const val TOP_SEARCHES = BuildConfig.topSearches
    const val SEARCH_RESULTS = BuildConfig.searchResults
    const val FROM_TOKEN = BuildConfig.fromToken
    const val FEATURED_RADIO = BuildConfig.featuredRadio
    const val ARTIST_RADIO = BuildConfig.artistRadio
    const val ENTITY_RADIO = BuildConfig.entityRadio
    const val RADIO_SONGS = BuildConfig.radioSongs
    const val SONG_DETAILS = BuildConfig.songDetails
    const val PLAYLIST_DETAILS = BuildConfig.playlistDetails
    const val ALBUM_DETAILS = BuildConfig.albumDetails
    const val GET_RESULTS = BuildConfig.getResults
    const val ALBUM_RESULTS = BuildConfig.albumResults
    const val ARTIST_RESULTS = BuildConfig.artistResults
    const val PLAYLIST_RESULTS = BuildConfig.playlistResults
    const val GET_RECOMMENDATION = BuildConfig.getReco
    const val GET_ALBUM_RECOMMENDATION = BuildConfig.getAlbumReco
    const val ARTIST_OTHER_TOP_SONGS = BuildConfig.artistOtherTopSongs

    //Decode
    const val KEY = BuildConfig.key
    const val ALGORITHM = BuildConfig.algo
    private const val BLOCK = BuildConfig.block
    private const val PADDING = BuildConfig.padding
    const val TRANSFORMATION = "$ALGORITHM/$BLOCK/$PADDING"

    //Notifications
    const val PLAYER_CHANNEL_ID = "player_channel_id"
    const val NOTIFICATION_ID = 101

    //Player view model
    const val SAVED_MEDIA_ITEMS = "saved_songs_list"

}