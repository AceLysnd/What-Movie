package com.ace.whatmovie.presentation.ui.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ace.whatmovie.R
import com.ace.whatmovie.databinding.ItemMovieBinding
import com.ace.whatmovie.model.Movie
import com.ace.whatmovie.presentation.ui.MainActivity.Companion.POSTER_URL


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

//        fun setMovies(movies: List<Movie>) {
//            this.movies.clear()
//            this.movies.addAll(movies)
//            notifyDataSetChanged()
//        }
//        fun clearItems(){
//            this.movies.clear()
//            notifyDataSetChanged()
//        }

    fun updateMovies(movies: MutableList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val poster : ImageView = binding.itemMoviePoster

        fun bind(movie: Movie) {
            poster.load("$POSTER_URL${movie.posterPath}") {
                crossfade(true)
            }

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }
}
