package com.ace.whatmovie.data.local.user

import javax.inject.Inject

class AccountDataSource @Inject constructor(private val accountDao: AccountDao) {

    suspend fun getAccountById(id: Long): AccountEntity? {
        return accountDao.getAccountById(id)
    }

    suspend fun registerAccount(account: AccountEntity): Long{
        return accountDao.registerAccount(account)
    }

    suspend fun updateAccount(account: AccountEntity): Int {
        return accountDao.updateAccount(account)
    }

    suspend fun getUser(username: String) : AccountEntity {
        return accountDao.getUser(username)
    }

}

//class AccountDataSourceImpl: AccountDataSource {
//    override suspend fun getAccountById(id: Long): AccountEntity? {
//        return accountDao.getAccountById(id)
//    }
//
//    override suspend fun registerAccount(account: AccountEntity): Long {
//        return accountDao.registerAccount(account)
//    }
//
//    override suspend fun updateAccount(account: AccountEntity): Int {
//        return accountDao.updateAccount(account)
//    }
//
//    override suspend fun getUser(username: String): AccountEntity {
//        return accountDao.getUser(username)
//    }
//}