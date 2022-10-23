package com.ace.whatmovie.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: LocalRepository) : ViewModel() {

    val detailDataResult = MutableLiveData<AccountEntity>()
    val updateResult = MutableLiveData<Resource<Number>>()

    fun getAccountById(id: Long) {
        viewModelScope.launch {
            detailDataResult.postValue(repository.getAccountById(id))
        }
    }

    fun registerUser(account: AccountEntity) {
        viewModelScope.launch {
            repository.createAccount(account)
        }
    }
}