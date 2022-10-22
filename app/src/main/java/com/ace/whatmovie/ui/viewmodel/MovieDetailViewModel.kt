package com.ace.whatmovie.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.model.GetMoviesResponse
import com.ace.whatmovie.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    val _similarMovies = MutableLiveData<GetMoviesResponse>()
    val similarMovies: LiveData<GetMoviesResponse>
    get() = _similarMovies

    fun getSimilarMovies() {
        viewModelScope.launch {
            _similarMovies.postValue(moviesRepository.getSimilarMovies())
        }
    }
}