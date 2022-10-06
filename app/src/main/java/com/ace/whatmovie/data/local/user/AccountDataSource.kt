package com.ace.whatmovie.data.local.user

interface AccountDataSource {
    suspend fun registerAccount(account: AccountEntity): Long

    suspend fun getUser(username: String) : AccountEntity
}

class AccountDataSourceImpl(private val accountDao: AccountDao): AccountDataSource {

    override suspend fun registerAccount(account: AccountEntity): Long {
        return accountDao.registerAccount(account)
    }

    override suspend fun getUser(username: String): AccountEntity {
        return accountDao.getUser(username)
    }
}