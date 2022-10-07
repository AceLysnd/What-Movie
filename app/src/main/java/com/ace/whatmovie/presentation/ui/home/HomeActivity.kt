package com.ace.whatmovie.presentation.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace.whatmovie.R
import com.ace.whatmovie.model.Movie
import com.ace.whatmovie.presentation.adapter.MoviesAdapter
import com.ace.whatmovie.presentation.ui.detail.*
import com.ace.whatmovie.data.repository.MoviesRepository

class HomeActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var nowPlayingMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        popularMovies = findViewById(R.id.rv_popular_movies)
        nowPlayingMovies = findViewById(R.id.rv_now_playing_movies)

        setLinearLayouts()
        setMovieAdapters()
        getMovies()
    }

    private fun getMovies() {
        MoviesRepository.getPopularMovies(
            onSuccess = ::fetchPopularMovies,
            onError = ::onError
        )
        MoviesRepository.getNowPlayingMovie(
            onSuccess = ::fetchNowPlayingMovie,
            onError = ::onError
        )
    }

    fun setLinearLayouts(){
        popularMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        nowPlayingMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    fun setMovieAdapters(){
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        nowPlayingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        nowPlayingMovies.adapter = nowPlayingMoviesAdapter
    }

    private fun fetchNowPlayingMovie(movies: MutableList<Movie>) {
        nowPlayingMoviesAdapter.addMovies(movies)
    }

    private fun fetchPopularMovies(movies: MutableList<Movie>) {
        popularMoviesAdapter.setMovies(movies)
    }

    private fun onError() {
        Toast.makeText(this, "error getting movies", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.voteAverage)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }
}
