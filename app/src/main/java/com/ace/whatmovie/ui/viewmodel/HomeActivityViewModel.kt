package com.ace.whatmovie.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.whatmovie.data.model.GetMoviesResponse
import com.ace.whatmovie.data.model.Prefs
import com.ace.whatmovie.data.repository.LocalRepository
import com.ace.whatmovie.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val repository: LocalRepository
) : ViewModel() {

    val _popularMovies = MutableLiveData<GetMoviesResponse>()
    val popularMovies: LiveData<GetMoviesResponse>
    get() = _popularMovies

    val _nowPlayingMovies = MutableLiveData<GetMoviesResponse>()
    val nowPlayingMovies: LiveData<GetMoviesResponse>
    get() = _nowPlayingMovies

    val _upcomingMovies = MutableLiveData<GetMoviesResponse>()
    val upcomingMovies: LiveData<GetMoviesResponse>
    get() = _upcomingMovies

    fun getPopularMovies(){
        viewModelScope.launch {
            _popularMovies.postValue(moviesRepository.getPopularMovies())
        }
    }

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            _nowPlayingMovies.postValue(moviesRepository.getNowPlayingMovies())
        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch {
            _upcomingMovies.postValue(moviesRepository.getUpcomingMovies())
        }
    }

    fun getAccountPrefs(): LiveData<Prefs> {
        return repository.getAccountPrefs()
    }
}