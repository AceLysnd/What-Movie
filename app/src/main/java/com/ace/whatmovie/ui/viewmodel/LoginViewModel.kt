package com.ace.whatmovie.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LocalRepository) : ViewModel() {

    private var _getUserResult = MutableLiveData<AccountEntity>()
    val getUser: LiveData<AccountEntity> get() = _getUserResult

    fun getUser(username: String) {
        viewModelScope.launch {
            _getUserResult.postValue(repository.getAccount(username))
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

    fun getLoginStatus(): LiveData<Boolean> {
        return repository.getLoginStatus()
    }
}