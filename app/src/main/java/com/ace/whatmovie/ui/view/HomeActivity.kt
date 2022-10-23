package com.ace.whatmovie.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace.whatmovie.R
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.ui.adapter.MoviesAdapter
import com.ace.whatmovie.ui.adapter.MoviesAdapterLarge
import com.ace.whatmovie.ui.viewmodel.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var nowPlayingMovies: RecyclerView
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var username: TextView

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var nowPlayingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesAdapter: MoviesAdapterLarge

    private val viewModel: HomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        popularMovies = findViewById(R.id.rv_popular_movies)
        nowPlayingMovies = findViewById(R.id.rv_now_playing_movies)
        upcomingMovies = findViewById(R.id.rv_upcoming_movies)
        username = findViewById(R.id.tv_username)

        setUsername()
        setLinearLayouts()
        setMovieAdapters()
        getMovies()
    }

    private fun setUsername() {
        viewModel.getAccountPrefs().observe(this){
            username.text = it.username
        }
    }

    private fun getMovies() {
        viewModel.getPopularMovies()
        viewModel.getUpcomingMovies()
        viewModel.getNowPlayingMovies()

        viewModel.popularMovies.observe(this){
            fetchPopularMovies(it.movies)
        }

        viewModel.nowPlayingMovies.observe(this){
            fetchNowPlayingMovies(it.movies)
        }

        viewModel.upcomingMovies.observe(this){
            fetchUpcomingMovies(it.movies)
        }
    }

    private fun setLinearLayouts() {

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
        upcomingMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setMovieAdapters() {
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        popularMovies.adapter = popularMoviesAdapter

        nowPlayingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        nowPlayingMovies.adapter = nowPlayingMoviesAdapter

        upcomingMoviesAdapter =
            MoviesAdapterLarge(mutableListOf()) { movie -> showMovieDetails(movie) }
        upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun fetchNowPlayingMovies(movies: MutableList<Movie>) {
        nowPlayingMoviesAdapter.addMovies(movies)
    }

    private fun fetchPopularMovies(movies: MutableList<Movie>) {
        popularMoviesAdapter.setMovies(movies)
    }

    private fun fetchUpcomingMovies(movies: MutableList<Movie>) {
        upcomingMoviesAdapter.setMovies(movies)
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

        intent.putExtra(MOVIE_ID, movie.id)

        MOVIE_ID_INT = movie.id!!
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                goToProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToProfile() {
        val i = Intent(this, ProfileActivity::class.java)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        setUsername()
    }

    private var backButtonCount = 0
    override fun onBackPressed() {
        if (backButtonCount < 1) {
            Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_SHORT).show()
            backButtonCount += 1
        } else {
            moveTaskToBack(true)
            backButtonCount = 0
        }
    }

    companion object {
        var MOVIE_ID_INT: Int = 0
    }
}
