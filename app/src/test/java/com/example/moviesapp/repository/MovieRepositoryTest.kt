package com.example.moviesapp.repository

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.local.MovieDAO
import com.example.moviesapp.data.remote.MovieDataService
import com.example.moviesapp.utils.mockMovieDetails
import com.example.moviesapp.utils.mockMoviesResponse
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class MovieRepositoryTest {

    @MockK
    private lateinit var service: MovieDataService

    @MockK
    private lateinit var dao: MovieDAO

    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        service = mockk(relaxed = true)
        dao = mockk(relaxed = true)
        repository = MovieRepository(service, dao)
    }

    @Test
    fun `getPopularMovies should call service's getPopularMovies`() = runBlocking {
        // Given
        val page = 1
        val movies = mockMoviesResponse()
        coEvery { service.getPopularMovies(page, BuildConfig.THE_MOVIE_DB_API_KEY) } returns Response.success(movies)

        // When
        repository.getPopularMovies(page)

        // Then
        coVerify { service.getPopularMovies(page, BuildConfig.THE_MOVIE_DB_API_KEY) }
    }

    @Test
    fun `insert should call dao's insert`() = runBlocking {
        // Given
        val movieDetails = mockMovieDetails()

        // When
        repository.insert(movieDetails)

        // Then
        coVerify { dao.insert(movieDetails) }
    }

    @Test
    fun `getAll should call dao's getAll`() = runBlocking {
        // When
        repository.getAll()

        // Then
        coVerify { dao.getAll() }
    }

    @Test
    fun `delete should call dao's delete`() = runBlocking {
        // Given
        val movieDetails = mockMovieDetails()

        // When
        repository.delete(movieDetails)

        // Then
        coVerify { dao.delete(movieDetails) }
    }


}