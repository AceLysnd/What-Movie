package com.ace.whatmovie.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountDao {

    @Insert
    suspend fun registerAccount(account: AccountEntity): Long

    @Update
    suspend fun updateAccount(account: AccountEntity): Int

    @Query("SELECT * FROM account_information WHERE username = :username")
    suspend fun getUser(username: String) : AccountEntity

    @Query("SELECT * FROM ACCOUNT_INFORMATION WHERE accountId == :id LIMIT 1")
    suspend fun getAccountById(id : Long) : AccountEntity?
}