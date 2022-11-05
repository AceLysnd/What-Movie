package com.ace.whatmovie.ui.viewmodel

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ace.whatmovie.data.local.AppDatabase
import com.ace.whatmovie.data.local.user.AccountDao
import com.ace.whatmovie.data.local.user.AccountDataSource
import com.ace.whatmovie.data.model.AccountDataStoreManager
import com.ace.whatmovie.data.model.GetMoviesResponse
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.repository.MoviesRepository
import com.ace.whatmovie.data.services.ApiHelper
import com.ace.whatmovie.data.services.MovieApiService
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeActivityViewModelTest {

    private lateinit var service: MovieApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var repo: MoviesRepository
    private lateinit var localRepo: LocalRepository
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var accountDataSource: AccountDataSource
    private lateinit var prefs: AccountDataStoreManager
    private lateinit var dao: AccountDao
    private lateinit var database: AppDatabase
    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()      //executes each task synchronously

    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.accountDao

        Dispatchers.setMain(dispatcher)
        service = mockk()
        apiHelper = ApiHelper(service)
        repo = MoviesRepository(apiHelper)
        accountDataSource = AccountDataSource(dao)
        prefs = AccountDataStoreManager(context)
        localRepo = LocalRepository(accountDataSource,prefs)
        viewModel = HomeActivityViewModel(repo,localRepo)
    }

    @Test
    fun getPopularMoviesFromViewModel(): Unit = runBlocking {
        // Mocking (GIVEN)
        val respPopMovie = mockk<GetMoviesResponse>()

        every {
            runBlocking {
                repo.getPopularMovies()
            }
        } returns respPopMovie

        // System Under Test (WHEN)
        viewModel.getPopularMovies()

        // THEN
        val result = repo.getPopularMovies()
        assertEquals(respPopMovie,result)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    try {
        afterObserve.invoke()
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}