package com.ace.whatmovie.data.model

data class Prefs(
    val accountId: Long,
    val username: String,
    val email: String,
    val password: String,
    val loginStatus: Boolean
)
