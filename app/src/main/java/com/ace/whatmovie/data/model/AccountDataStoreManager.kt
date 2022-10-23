package com.ace.whatmovie.data.model

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountDataStoreManager @Inject constructor(@ActivityContext private val context: Context) {

    suspend fun setAccount(username: String, email: String, password: String, accountId: Long) {
        context.accountDataStore.edit { preferences ->
            preferences[ACCOUNT_USERNAME] = username
            preferences[ACCOUNT_EMAIL] = email
            preferences[ACCOUNT_PASSWORD] = password
            preferences[ACCOUNT_ID] = accountId
        }
    }
    suspend fun setLoginStatus(loginStatus: Boolean) {
        context.accountDataStore.edit { preferences ->
            preferences[LOGGED_IN_STATUS] = loginStatus
        }
    }
    suspend fun setProfilePicture(profilePicture: String) {
        context.accountDataStore.edit { preferences ->
            preferences[ACCOUNT_PROFILE_PICTURE] = profilePicture
        }
    }

    fun getAccount(): Flow<Prefs> {
        return context.accountDataStore.data.map { preferences ->
            Prefs(
                preferences[ACCOUNT_ID] ?: 0,
                preferences[ACCOUNT_USERNAME] ?: "",
                preferences[ACCOUNT_EMAIL] ?: "",
                preferences[ACCOUNT_PASSWORD] ?: "",
                preferences[LOGGED_IN_STATUS] ?: false,
                preferences[ACCOUNT_PROFILE_PICTURE] ?: ""
            )
        }
    }

    fun getLoginStatus(): Flow<Boolean> {
        return context.accountDataStore.data.map { preferences ->
            preferences[LOGGED_IN_STATUS] ?: false
        }
    }

    fun getAccountId(): Flow<Long> {
        return context.accountDataStore.data.map { preferences ->
            preferences[ACCOUNT_ID] ?: 0
        }
    }

    fun getProfilePicture(): Flow<String> {
        return context.accountDataStore.data.map { preferences ->
            preferences[ACCOUNT_PROFILE_PICTURE] ?: ""
        }
    }


    companion object {
        private const val DATASTORE_NAME = "account_prefs"

        private val ACCOUNT_USERNAME = stringPreferencesKey("account_username")

        private val ACCOUNT_EMAIL = stringPreferencesKey("account_email")

        private val ACCOUNT_PASSWORD = stringPreferencesKey("account_password")

        private val ACCOUNT_PROFILE_PICTURE = stringPreferencesKey("account_profile_picture")

        private val ACCOUNT_ID = longPreferencesKey("account_id")

        private val LOGGED_IN_STATUS = booleanPreferencesKey("logged_in_status")

        private val Context.accountDataStore by preferencesDataStore(name = DATASTORE_NAME)
    }
}