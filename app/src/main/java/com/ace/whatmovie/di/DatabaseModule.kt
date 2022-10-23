package com.ace.whatmovie.di

import android.content.Context
import androidx.room.Room
import com.ace.whatmovie.data.local.AppDatabase
import com.ace.whatmovie.data.local.user.AccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDao(appDatabase: AppDatabase): AccountDao {
        return appDatabase.accountDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context):
            AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}