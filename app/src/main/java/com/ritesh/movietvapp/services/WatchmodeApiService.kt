package com.ritesh.movietvapp.services

import com.ritesh.movietvapp.model.MovieDetails
import com.ritesh.movietvapp.model.TitlesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WatchmodeApiService {

    // Fetch list of movies or TV shows based on type (movie or tv_series)
    @GET("list-titles/")
    fun getTitles(
        @Query("apiKey") apiKey: String,
        @Query("types") type: String // "movie" or "tv_series"
    ): Single<TitlesResponse>

    @GET("title/{id}/details/")
    suspend fun getMediaDetails(
        @Path("id") titleId: Int,
        @Query("apiKey") apiKey: String
    ): MovieDetails
}
