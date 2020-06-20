package com.AntonioEspino.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.AntonioEspino.DataHolder
import com.AntonioEspino.R
import com.AntonioEspino.adapters.FilmsAdapter
import com.AntonioEspino.adapters.FilmsListener
import com.AntonioEspino.api.ApiRest
import com.AntonioEspino.models.Film
import com.AntonioEspino.models.FilmsList
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), FilmsListener {

    val TAG = "MiApp"
    var data: ArrayList<Film> = ArrayList()
    private var adapter: FilmsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ApiRest.initService()

        getFilmsList()

        val mLayoutManager = GridLayoutManager(this, 2)
        recycler_films.layoutManager = mLayoutManager
        adapter = FilmsAdapter(data, this)
        recycler_films.adapter = adapter


    }

    private fun getFilmsList() {
        val call = ApiRest.service.getFilmsList()

        call.enqueue(object : Callback<FilmsList> {

            override fun onResponse(call: Call<FilmsList>, response: Response<FilmsList>) {

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    //Log.e(TAG, body.toString())

                    data.clear()
                    data.addAll(body.results)


                    adapter?.notifyDataSetChanged()

                } else {
                    Log.e(TAG, response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<FilmsList>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }

    override fun onClick(filmSelected: Film) {
        val filmIntent = Intent(this, CharacterActivity::class.java)
        DataHolder.filmSelected = filmSelected
        Log.e(TAG, DataHolder.filmSelected!!.characters.toString())
        startActivity(filmIntent)
    }
}