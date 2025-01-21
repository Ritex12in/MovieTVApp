package com.ritesh.movietvapp.repository

import com.ritesh.movietvapp.model.MovieDetails
import com.ritesh.movietvapp.model.Title
import com.ritesh.movietvapp.services.WatchmodeApiService
import io.reactivex.rxjava3.core.Single

class MediaRepository(private val apiService: WatchmodeApiService) {

    // return list of titles
    fun getTitles(apiKey: String, type: String): Single<List<Title>> {
        return apiService.getTitles(apiKey, type)
            .map { response -> response.titles }
    }

    // return movie details
    suspend fun getMediaDetails(titleId: Int, apiKey: String): MovieDetails {
        return apiService.getMediaDetails(titleId, apiKey)
    }
}
