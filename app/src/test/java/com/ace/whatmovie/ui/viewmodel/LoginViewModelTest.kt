package com.ace.whatmovie.ui.viewmodel

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ace.whatmovie.data.local.AppDatabase
import com.ace.whatmovie.data.local.user.AccountDao
import com.ace.whatmovie.data.local.user.AccountDataSource
import com.ace.whatmovie.data.model.AccountDataStoreManager
import com.ace.whatmovie.data.repository.LocalRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest {

    private lateinit var localRepo: LocalRepository
    private lateinit var viewModel: LoginViewModel
    private lateinit var accountDataSource: AccountDataSource
    private lateinit var prefs: AccountDataStoreManager
    private lateinit var dao: AccountDao
    private lateinit var database: AppDatabase
    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        // Ini database per test harus dibikin terus karena repo butuh account datasource
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        dao = database.accountDao

        Dispatchers.setMain(dispatcher)
        accountDataSource = AccountDataSource(dao)
        prefs = AccountDataStoreManager(context)
        localRepo = LocalRepository(accountDataSource, prefs)
        viewModel = LoginViewModel(localRepo)
    }

    @Test
    fun saveLoginStatus(): Unit = runBlocking  {
        val loginStatus = true
        viewModel.saveLoginStatus(loginStatus)
        val getLoginStatus = prefs.getLoginStatus()
        assertThat(getLoginStatus.equals(loginStatus))

    }
}

//@VisibleForTesting(otherwise = VisibleForTesting.NONE)
//fun <T> LiveData<T>.getOrAwaitValue(
//    time: Long = 2,
//    timeUnit: TimeUnit = TimeUnit.SECONDS,
//    afterObserve: () -> Unit = {}
//): T {
//    var data: T? = null
//    val latch = CountDownLatch(1)
//    val observer = object : Observer<T> {
//        override fun onChanged(o: T?) {
//            data = o
//            latch.countDown()
//            this@getOrAwaitValue.removeObserver(this)
//        }
//    }
//    this.observeForever(observer)
//    try {
//        afterObserve.invoke()
//        if (!latch.await(time, timeUnit)) {
//            throw TimeoutException("LiveData value was never set.")
//        }
//    } finally {
//        this.removeObserver(observer)
//    }
//    @Suppress("UNCHECKED_CAST")
//    return data as T
//}