package com.ace.whatmovie.data.repository

import com.ace.whatmovie.data.model.GetMoviesResponse
import com.ace.whatmovie.data.services.ApiHelper
import com.ace.whatmovie.data.services.MovieApiService
import io.mockk.MockK
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class MoviesRepositoryTest {

    private lateinit var service: MovieApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var repo: MoviesRepository

    @Before
    fun setUp() {
        service = mockk()
        apiHelper = ApiHelper(service)
        repo = MoviesRepository(apiHelper)
    }

    @Test
    fun getPopularMovies(): Unit = runBlocking {
        // Mocking (GIVEN)
        val respPopMovie = mockk<GetMoviesResponse>()

        every {
            runBlocking {
                service.getPopularMovies("88bc9fbafce3d281e0ce675f7a6382c7", 1)
            }
        } returns respPopMovie

        // System Under Test (WHEN)
        repo.getPopularMovies()

        // (THEN)
        verify {
            runBlocking { service.getPopularMovies("88bc9fbafce3d281e0ce675f7a6382c7", 1) }
        }
    }
}