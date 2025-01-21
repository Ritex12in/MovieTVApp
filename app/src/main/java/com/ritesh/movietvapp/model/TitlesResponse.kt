package com.ritesh.movietvapp.model

data class TitlesResponse(
    val titles: List<Title>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)
