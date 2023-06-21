package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val results: List<MovieDetails>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
)