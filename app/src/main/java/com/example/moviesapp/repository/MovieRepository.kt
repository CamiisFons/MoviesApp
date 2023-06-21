package com.example.moviesapp.repository

import com.example.moviesapp.BuildConfig.THE_MOVIE_DB_API_KEY
import com.example.moviesapp.data.local.MovieDAO
import com.example.moviesapp.data.remote.MovieDataService
import com.example.moviesapp.model.MovieDetails
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieDataService,
    private val dao: MovieDAO
) {

    private val apiKey = THE_MOVIE_DB_API_KEY

    suspend fun getPopularMovies(page: Int) = service.getPopularMovies(page, apiKey)

    suspend fun insert(movieDetails: MovieDetails) = dao.insert(movieDetails)
    fun getAll() = dao.getAll()
    suspend fun delete(movieDetails: MovieDetails) = dao.delete(movieDetails)
}

