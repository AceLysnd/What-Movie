package com.ace.whatmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.databinding.ItemMovieLargeBinding
import com.ace.whatmovie.ui.MainActivity.Companion.BACKDROP_URL

class MoviesAdapterLarge(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapterLarge.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieLargeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setMovies(movies: MutableList<Movie>) {
        this.movies.clear()
        this.movies = movies
        notifyDataSetChanged()
    }


    inner class MovieViewHolder(private val binding: ItemMovieLargeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val poster: ImageView = binding.itemMoviePosterLarge
        private val title: TextView = binding.movieTitle

        fun bind(movie: Movie) {
            title.text = movie.title
            poster.load("$BACKDROP_URL${movie.backdropPath}") {
                crossfade(true)
            }

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }


}