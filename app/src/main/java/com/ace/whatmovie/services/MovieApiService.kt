package com.ace.whatmovie.services


import com.ace.whatmovie.BuildConfig
import com.ace.whatmovie.model.GetMoviesResponse
import com.ace.whatmovie.model.Movie
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieApiService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey:String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}