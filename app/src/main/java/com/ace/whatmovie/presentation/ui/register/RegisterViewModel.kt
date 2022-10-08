package com.ace.whatmovie.presentation.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.wrapper.Resource
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: LocalRepository) : ViewModel() {

    val updateResult = MutableLiveData<Resource<Number>>()

    fun registerUser(account: AccountEntity) {
        viewModelScope.launch {
            repository.createAccount(account)
        }
    }
    fun updateUser(account: AccountEntity) {
        viewModelScope.launch {
            updateResult.postValue(repository.updateAccount(account))
        }
    }
}