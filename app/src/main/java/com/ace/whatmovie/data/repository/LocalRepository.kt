package com.ace.whatmovie.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ace.whatmovie.data.local.user.AccountDataSource
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.AccountDataStoreManager
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.wrapper.Resource
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val accountDataSource: AccountDataSource,
    private val prefs: AccountDataStoreManager,
) {

    suspend fun getAccountById(id: Long): AccountEntity? {
        return accountDataSource.getAccountById(id)
    }

    suspend fun createAccount(account: AccountEntity): Long {
        return accountDataSource.registerAccount(account)
    }

    suspend fun updateAccount(account: AccountEntity): Int {
        return accountDataSource.updateAccount(account)
    }

    suspend fun getAccount(username: String): AccountEntity {
        return accountDataSource.getUser(username)
    }

    suspend fun setAccount(username: String, email: String, password:String, accountId: Long) {
        prefs.setAccount(username, email, password, accountId)
    }

    suspend fun setLoginStatus(loginStatus: Boolean) {
        prefs.setLoginStatus(loginStatus)
    }

    suspend fun setProfilePicture(profilePicture: String) {
        prefs.setProfilePicture(profilePicture)
    }

    fun getAccountPrefs(): LiveData<Prefs> {
        return prefs.getAccount().asLiveData()
    }

    fun getLoginStatus(): LiveData<Boolean> {
        return prefs.getLoginStatus().asLiveData()
    }

    fun getAccountId(): LiveData<Long> {
        return prefs.getAccountId().asLiveData()
    }

    fun getProfilePicture(): LiveData<String> {
        return prefs.getProfilePicture().asLiveData()
    }
}