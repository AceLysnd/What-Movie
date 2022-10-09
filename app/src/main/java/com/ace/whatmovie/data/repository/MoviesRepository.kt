package com.ace.whatmovie.data.repository

import android.util.Log
import com.ace.whatmovie.data.model.GetMoviesResponse
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.presentation.ui.home.HomeActivity.Companion.MOVIE_ID_INT
import com.ace.whatmovie.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MoviesRepository {

    private val api: MovieApiService by lazy {
        MovieApiService.invoke()
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "error getting movies", t)
                }
            })
    }

    fun getNowPlayingMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getNowPlayingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "error getting movies", t)
                }
            })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "error getting movies", t)
                }
            })
    }

    fun getSimilarMovies(
        page: Int = 1,
        movieId: Int = MOVIE_ID_INT,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit,
    ) {
        api.getSimilarMovies(page = page, movieId = movieId)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    Log.e("Repository", "error getting movies", t)
                }
            })
    }
}