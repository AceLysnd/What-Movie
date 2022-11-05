package com.ace.whatmovie.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ace.whatmovie.data.local.user.AccountDao
import com.ace.whatmovie.data.local.user.AccountEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: AccountDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.accountDao
    }

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun createAndReadAccount(): Unit = runBlocking {
        val account = AccountEntity(1,"test","test","test")
        dao.registerAccount(account)
        val accountById = dao.getAllAccount()
        assertThat(accountById.contains(account)).isTrue()
    }
}