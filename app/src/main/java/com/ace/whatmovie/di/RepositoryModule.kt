package com.ace.whatmovie.di

import android.content.Context
import androidx.room.Room
import com.ace.whatmovie.data.local.AppDatabase
import com.ace.whatmovie.data.local.user.AccountDao
import com.ace.whatmovie.data.local.user.AccountDataSource

import com.ace.whatmovie.data.model.AccountDataStoreManager
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.repository.MoviesRepository
import com.ace.whatmovie.data.services.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(apiHelper: ApiHelper) = MoviesRepository(apiHelper)

    @ViewModelScoped
    @Provides
    fun provideDataSource(accountDataSource: AccountDataSource, prefs: AccountDataStoreManager) =
        LocalRepository(accountDataSource, prefs)


    @ViewModelScoped
    @Provides
    fun provideContext(@ApplicationContext context: Context) = AccountDataStoreManager(context)
}