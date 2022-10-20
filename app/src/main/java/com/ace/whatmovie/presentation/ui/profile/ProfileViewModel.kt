package com.ace.whatmovie.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.wrapper.Resource
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: LocalRepository) : ViewModel() {

    val updateResult = MutableLiveData<Resource<Number>>()


    fun updateUser(account: AccountEntity) {
        viewModelScope.launch {
            updateResult.postValue(repository.updateAccount(account))
        }
    }

    fun setAccount(username: String, email: String, password: String, accountId: Long) {
        viewModelScope.launch {
            repository.setAccount(username, email, password, accountId)
        }
    }

    fun saveLoginStatus(loginStatus: Boolean) {
        viewModelScope.launch {
            repository.setLoginStatus(loginStatus)
        }
    }

    fun getAccountPrefs(): LiveData<Prefs> {
        return repository.getAccountPrefs()
    }
}