package com.ace.whatmovie.presentation.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.ace.whatmovie.R
import com.ace.whatmovie.presentation.ui.MainActivity.Companion.BACKDROP_URL
import com.ace.whatmovie.presentation.ui.MainActivity.Companion.POSTER_URL

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

    }

    private fun populateDetails(extras: Bundle) {

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
//        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")

    }
}