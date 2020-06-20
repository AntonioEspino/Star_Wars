package com.AntonioEspino.models

data class FilmsList(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: ArrayList<Film>
)