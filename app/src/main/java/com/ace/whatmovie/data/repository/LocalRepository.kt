package com.ace.whatmovie.data.repository

import com.ace.whatmovie.data.local.user.AccountDataSource
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.wrapper.Resource

interface LocalRepository {

    suspend fun getAccountById(id: Long): Resource<AccountEntity?>
    suspend fun createAccount(account: AccountEntity): Resource<Number>

    suspend fun updateAccount(account: AccountEntity): Resource<Number>

    suspend fun getAccount(username: String): Resource<AccountEntity>
}

class LocalRepositoryImpl(
    private val accountDataSource: AccountDataSource,
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


    private suspend fun <T> proceed(coroutine: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

}