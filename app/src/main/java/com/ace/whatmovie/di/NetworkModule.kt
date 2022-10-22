package com.ace.whatmovie.di

import com.ace.whatmovie.BuildConfig
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.services.ApiHelper
import com.ace.whatmovie.data.services.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttp(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideApiHelper(apiService: MovieApiService): ApiHelper {
        return ApiHelper(apiService)
    }

}