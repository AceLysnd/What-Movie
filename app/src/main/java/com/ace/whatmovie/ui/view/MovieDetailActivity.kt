package com.ace.whatmovie.ui.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ace.whatmovie.R
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.ui.MainActivity.Companion.BACKDROP_URL
import com.ace.whatmovie.ui.MainActivity.Companion.POSTER_URL
import com.ace.whatmovie.ui.adapter.MoviesAdapter
import com.ace.whatmovie.ui.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID = "movie_id"

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    private lateinit var similarMovies: RecyclerView

    private lateinit var similarMoviesAdapter: MoviesAdapter

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        similarMovies = findViewById(R.id.rv_similar_movies)

        val extras = intent.extras

        if (extras != null) {
            getDetails(extras)

        } else {
            finish()
        }

        setLinearLayouts()
        setMovieAdapters()
        getMovies()
    }

    private fun getMovies() {
        viewModel.getSimilarMovies()

        viewModel.similarMovies.observe(this){
            fetchSimilarMovies(it.movies)
        }
    }
    private fun setLinearLayouts(){
        similarMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setMovieAdapters(){
        similarMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        similarMovies.adapter = similarMoviesAdapter
    }



    private fun showMovieDetails(movie: Movie) {
        backdrop.load("$BACKDROP_URL${movie.backdropPath}"){
                crossfade(true)
            }


        poster.load("$POSTER_URL${movie.posterPath}"){
                crossfade(true)
            }

        title.text = movie.title
        rating.text = movie.voteAverage.toString()
        releaseDate.text = movie.releaseDate
        overview.text = movie.overview
    }

    private fun fetchSimilarMovies(movies: MutableList<Movie>) {
        similarMoviesAdapter.addMovies(movies)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_getting_movies), Toast.LENGTH_SHORT).show()
    }

    private fun getDetails(extras: Bundle) {

        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
                backdrop.load("$BACKDROP_URL$backdropPath"){
                    crossfade(true)
                }
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
                poster.load("$POSTER_URL$posterPath"){
                    crossfade(true)
                }
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        rating.text = extras.getDouble(MOVIE_RATING).toString()
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }
}