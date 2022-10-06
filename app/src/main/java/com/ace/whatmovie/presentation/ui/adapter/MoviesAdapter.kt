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


class MoviesAdapter(
    private var movies: List<Movie>
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

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val poster : ImageView = binding.itemMoviePoster

        fun bind(movie: Movie) {
            poster.load("https://image.tmdb.org/t/p/w342${movie.posterPath}") {
                crossfade(true)
            }
        }
    }
}
