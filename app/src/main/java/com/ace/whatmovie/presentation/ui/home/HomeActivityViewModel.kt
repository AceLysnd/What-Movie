package com.ace.whatmovie.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.data.repository.LocalRepository

class HomeActivityViewModel(private val repository: LocalRepository) : ViewModel(){

    fun getAccountPrefs(): LiveData<Prefs> {
        return repository.getAccountPrefs()
    }
}