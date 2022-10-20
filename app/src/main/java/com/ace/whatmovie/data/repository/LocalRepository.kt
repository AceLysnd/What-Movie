package com.ace.whatmovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ace.whatmovie.data.local.user.AccountDataSource
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.AccountDataStoreManager
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.wrapper.Resource

interface LocalRepository {

    suspend fun getAccountById(id: Long): Resource<AccountEntity?>
    suspend fun createAccount(account: AccountEntity): Resource<Number>

    suspend fun updateAccount(account: AccountEntity): Resource<Number>

    suspend fun getAccount(username: String): Resource<AccountEntity>

    suspend fun setAccount(username: String, email: String, password:String, accountId: Long)

    suspend fun setLoginStatus(loginStatus: Boolean)

    fun getAccountPrefs(): LiveData<Prefs>

    fun getLoginStatus(): LiveData<Boolean>

    fun getAccountId(): LiveData<Long>
}

class LocalRepositoryImpl(
    private val accountDataSource: AccountDataSource,
    private val prefs: AccountDataStoreManager
) : LocalRepository {

    override suspend fun getAccountById(id: Long): Resource<AccountEntity?> {
        return proceed {
            accountDataSource.getAccountById(id)
        }
    }

    override suspend fun createAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.registerAccount(account)
        }
    }

    override suspend fun updateAccount(account: AccountEntity): Resource<Number> {
        return proceed {
            accountDataSource.updateAccount(account)
        }
    }

    override suspend fun getAccount(username: String): Resource<AccountEntity> {
        return proceed {
            accountDataSource.getUser(username)
        }
    }

    override suspend fun setAccount(username: String, email: String, password: String, accountId: Long){
        prefs.setAccount(username, email, password, accountId)
    }
    override suspend fun setLoginStatus(loginStatus: Boolean){
        prefs.setLoginStatus(loginStatus)
    }

    override fun getAccountPrefs(): LiveData<Prefs> {
        return prefs.getAccount().asLiveData()
    }

    override fun getLoginStatus(): LiveData<Boolean> {
        return prefs.getLoginStatus().asLiveData()
    }

    override fun getAccountId(): LiveData<Long> {
        return prefs.getAccountId().asLiveData()
    }


    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}