package com.ace.whatmovie.data.repository

import com.ace.whatmovie.data.services.ApiHelper
import com.ace.whatmovie.ui.view.HomeActivity.Companion.MOVIE_ID_INT


class MoviesRepository(private val apiHelper: ApiHelper){

    suspend fun getPopularMovies() = apiHelper.getPopularMovies()

    suspend fun getNowPlayingMovies() = apiHelper.getNowPlayingMovies()

    suspend fun getUpcomingMovies() = apiHelper.getUpcomingMovies()

    suspend fun getSimilarMovies() = apiHelper.getSimilarMovies(1, MOVIE_ID_INT)
}

//object MoviesRepository {
//
//    private val api: MovieApiService by lazy {
//        MovieApiService.invoke()
//    }
//
//    fun getPopularMovies(
//        page: Int = 1,
//        onSuccess: (movies: MutableList<Movie>) -> Unit,
//        onError: () -> Unit
//    ) {
//        api.getPopularMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "error getting movies", t)
//                }
//            })
//    }
//
//    fun getNowPlayingMovies(
//        page: Int = 1,
//        onSuccess: (movies: MutableList<Movie>) -> Unit,
//        onError: () -> Unit
//    ) {
//        api.getNowPlayingMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "error getting movies", t)
//                }
//            })
//    }
//
//    fun getUpcomingMovies(
//        page: Int = 1,
//        onSuccess: (movies: MutableList<Movie>) -> Unit,
//        onError: () -> Unit
//    ) {
//        api.getUpcomingMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "error getting movies", t)
//                }
//            })
//    }
//
//    fun getSimilarMovies(
//        page: Int = 1,
//        movieId: Int = MOVIE_ID_INT,
//        onSuccess: (movies: MutableList<Movie>) -> Unit,
//        onError: () -> Unit,
//    ) {
//        api.getSimilarMovies(page = page, movieId = movieId)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies as MutableList<Movie>)
//                        } else {
//                            onError.invoke()
//                        }
//                    } else {
//                        onError.invoke()
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "error getting movies", t)
//                }
//            })
//    }
//}