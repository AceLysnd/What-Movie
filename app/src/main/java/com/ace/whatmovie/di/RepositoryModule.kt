package com.ace.whatmovie.di

import com.ace.whatmovie.data.local.user.AccountDataSource
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.repository.MoviesRepository
import com.ace.whatmovie.data.services.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(apiHelper: ApiHelper) = MoviesRepository(apiHelper)

//    @ViewModelScoped
//    @Provides
//    fun provideDataSource(accountDataSource: AccountDataSource) = Lo(account)
}