package com.ace.whatmovie.data.repository

import com.ace.whatmovie.data.services.ApiHelper
import com.ace.whatmovie.ui.view.HomeActivity.Companion.MOVIE_ID_INT


class MoviesRepository(private val apiHelper: ApiHelper){

    suspend fun getPopularMovies() = apiHelper.getPopularMovies()

    suspend fun getNowPlayingMovies() = apiHelper.getNowPlayingMovies()

    suspend fun getUpcomingMovies() = apiHelper.getUpcomingMovies()

    suspend fun getSimilarMovies() = apiHelper.getSimilarMovies(1, MOVIE_ID_INT)
}