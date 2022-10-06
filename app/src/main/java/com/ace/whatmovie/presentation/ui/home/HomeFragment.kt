package com.ace.whatmovie.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace.whatmovie.R
import com.ace.whatmovie.databinding.FragmentHomeBinding
import com.ace.whatmovie.databinding.FragmentLoginBinding
import com.ace.whatmovie.model.Movie
import com.ace.whatmovie.presentation.ui.adapter.MoviesAdapter
import com.ace.whatmovie.repositories.MoviesRepository

class HomeFragment : Fragment() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularMovies = requireActivity().findViewById(R.id.rv_popular_movies)
        popularMovies.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMoviesAdapter = MoviesAdapter(listOf())
        popularMovies.adapter = popularMoviesAdapter


//        MoviesRepository.getPopularMovies(
//            onSuccess = ::onPopularMoviesFetched,
//            onError = ::onError
//        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.updateMovies(movies)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


}