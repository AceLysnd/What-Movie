package com.ace.whatmovie.di

import android.content.Context
import com.ace.whatmovie.data.local.AppDatabase
import com.ace.whatmovie.data.local.user.*
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.repository.LocalRepositoryImpl

object ServiceLocator {

    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    fun provideAccountDao(context: Context): AccountDao {
        return provideAppDatabase(context).accountDao
    }

    fun provideUserDataSource(context: Context): AccountDataSource {
        return AccountDataSourceImpl(provideAccountDao(context))
    }

    fun provideServiceLocator(context: Context): LocalRepository {
        return LocalRepositoryImpl(
            provideUserDataSource(context),
        )
    }
}