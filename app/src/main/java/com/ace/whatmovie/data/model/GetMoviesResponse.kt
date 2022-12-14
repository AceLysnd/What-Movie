package com.ace.whatmovie.data.model

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: MutableList<Movie>,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("movie_id")val movieId: Int,
)