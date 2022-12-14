package com.ace.whatmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ace.whatmovie.data.model.Movie
import com.ace.whatmovie.databinding.ItemMovieBinding
import com.ace.whatmovie.ui.MainActivity.Companion.POSTER_URL


class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun addMovies(movies: MutableList<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val poster: ImageView = binding.itemMoviePoster

        fun bind(movie: Movie) {
            poster.load("$POSTER_URL${movie.posterPath}") {
                crossfade(true)
            }

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }
}
