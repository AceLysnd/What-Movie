package com.ace.whatmovie.data.services

import android.util.Log
import com.ace.whatmovie.data.model.GetMoviesResponse
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.ui.view.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHelper(private val api: MovieApiService) {
    suspend fun getPopularMovies(
        page: Int = 1,
    ) = api.getPopularMovies(page = page)

    suspend fun getNowPlayingMovies(
        page: Int = 1
    ) = api.getNowPlayingMovies(page = page)

    suspend fun getUpcomingMovies(
        page: Int = 1
    ) = api.getUpcomingMovies(page = page)

    suspend fun getSimilarMovies(
        page: Int = 1,
        movieId: Int
//        = HomeActivity.MOVIE_ID_INT
    ) = api.getSimilarMovies(page = page, movieId = movieId)

}