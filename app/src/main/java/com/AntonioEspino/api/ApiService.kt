package com.AntonioEspino.api

import com.AntonioEspino.models.Character
import com.AntonioEspino.models.FilmsList
import com.AntonioEspino.models.HomeWorld
import com.AntonioEspino.models.Specie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("films/")
    fun getFilmsList(): Call<FilmsList>
    @GET
    fun getCharacterByUrl(@Url url: String): Call<Character>
    @GET
    fun getHomeWorldByUrl(@Url url: String): Call<HomeWorld>
    @GET
    fun getSpeciesByUrl(@Url url: String): Call<Specie>
}