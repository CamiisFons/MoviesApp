package com.example.moviesapp.viewmodel

import com.example.moviesapp.common.core.State
import com.example.moviesapp.model.MovieDetails
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.favoritelist.FavoriteListViewModel
import com.example.moviesapp.utils.CoroutinesTestRule
import com.example.moviesapp.utils.mockMovieDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteListViewModelTest {
    private lateinit var repository: MovieRepository
    private lateinit var viewModel: FavoriteListViewModel

    @Rule
    @JvmField
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        viewModel = FavoriteListViewModel(repository)
    }

    @Test
    fun `fetchFavoriteMovies should emit State Empty when repository returns an empty list`() = coroutinesTestRule.runBlockingTest{
        // Given
        coEvery { repository.getAll() } returns flowOf(emptyList())

        // When
        val favoritesList = mutableListOf<State<List<MovieDetails>>>()
        val job = launch(coroutineContext) {
            viewModel.favorites.collectLatest { state ->
                favoritesList.add(state)
            }
        }

        // Then
        viewModel.fetchFavoriteMovies()

        val expectedState = State.Empty<List<MovieDetails>>()
        val actualState = favoritesList.last()

        assertEquals(expectedState.javaClass, actualState.javaClass)
        job.cancel()

    }

    @Test
    fun `fetchFavoriteMovies should emit State Success with movie list when repository returns non-empty list`() = coroutinesTestRule.runBlockingTest {
        // Given
        val movieList = listOf(mockMovieDetails(), mockMovieDetails())
        coEvery { repository.getAll() } returns flowOf(movieList)

        // When
        val favoritesList = mutableListOf<State<List<MovieDetails>>>()
        val job = launch(Dispatchers.Main) {
            viewModel.favorites.collectLatest { state ->
                favoritesList.add(state)
            }
        }
        delay(100)

        // Then
        viewModel.fetchFavoriteMovies()

        job.cancel()

        val expectedState = State.Success(mockMovieDetails())
        val actualState = favoritesList.last()

        assertEquals(expectedState.javaClass, actualState.javaClass)
        job.cancel()

    }

    @Test
    fun `delete should call repository's delete`() = coroutinesTestRule.runBlockingTest {
        // Given
        val movieDetails = mockMovieDetails()

        // When
        viewModel.delete(movieDetails)

        // Then
        coVerify { repository.delete(movieDetails) }
    }
}

