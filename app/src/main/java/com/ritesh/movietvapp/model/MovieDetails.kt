package com.ritesh.movietvapp.model

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val plot_overview: String = "",
    val release_date: String = "",
    val genre_names: List<String> = emptyList(),
    val poster: String = ""
)

