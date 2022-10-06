package com.ace.whatmovie.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDao {

    @Insert
    suspend fun registerAccount(account: AccountEntity): Long

    @Query("SELECT * FROM account_information WHERE username = :username")
    suspend fun getUser(username: String) : AccountEntity
}