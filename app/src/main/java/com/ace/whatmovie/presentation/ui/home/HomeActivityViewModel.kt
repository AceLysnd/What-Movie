package com.ace.whatmovie.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.local.user.AccountEntity
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.repository.MoviesRepository
import com.ace.whatmovie.wrapper.Resource
import kotlinx.coroutines.launch

class HomeActivityViewModel(private val repository: LocalRepository) : ViewModel(){

    private var _getUserResult = MutableLiveData<Resource<AccountEntity>>()
    val getUser: LiveData<Resource<AccountEntity>> get() = _getUserResult

    fun getUser(username: String) {
        viewModelScope.launch {
            _getUserResult.postValue(repository.getAccount(username))
        }
    }

}