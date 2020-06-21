package com.AntonioEspino.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.AntonioEspino.DataHolder
import com.AntonioEspino.R
import com.AntonioEspino.adapters.CharacterAdapter
import com.AntonioEspino.api.ApiRest
import com.AntonioEspino.models.*
import kotlinx.android.synthetic.main.activity_character.*
import kotlinx.android.synthetic.main.character_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class CharacterActivity : AppCompatActivity() {

    var data: ArrayList<Character> = ArrayList()
    val TAG = "MiApp"
    private var adapter: CharacterAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        getCharacterList()
        loadAdapter()

    }

    private fun getCharacterList() {
        loading.visibility = View.VISIBLE
        DataHolder.filmSelected?.characters?.forEach { character ->
            val call = ApiRest.service.getCharacterByUrl(character)

            call.enqueue(object : Callback<Character> {

                override fun onResponse(call: Call<Character>, response: Response<Character>) {

                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        val characterApi: Character = body
                        getSpeciesByUrl(characterApi)

                    } else {
                        Log.e(TAG, response.errorBody()?.string()!!)
                    }


                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    Log.e(TAG, t.message!!)
                    loading.visibility = View.GONE

                }
            })

        }
    }

    private fun getSpeciesByUrl(character: Character) {

        if (character.species.isNotEmpty()) {

            val call = ApiRest.service.getSpeciesByUrl(character.species[0].toString())

            call.enqueue(object : Callback<Specie> {

                override fun onResponse(call: Call<Specie>, response: Response<Specie>) {

                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        character.species.add(0, body.name)
                        getHomeWorldByUrl(character)
                    } else {
                        Log.e(TAG, response.errorBody()?.string()!!)
                    }
                }

                override fun onFailure(call: Call<Specie>, t: Throwable) {
                    Log.e(TAG, t.message!!)
                    loading.visibility = View.GONE

                }
            })
        }
    }

    private fun getHomeWorldByUrl(character: Character) {

        val call = ApiRest.service.getHomeWorldByUrl(character.homeworld)

        call.enqueue(object : Callback<HomeWorld> {

            override fun onResponse(call: Call<HomeWorld>, response: Response<HomeWorld>) {

                val body = response.body()
                if (response.isSuccessful && body != null) {
                    character.homeworld = body.name
                    data.add(character)
                    adapter?.notifyDataSetChanged()

                } else {
                    Log.e(TAG, response.errorBody()?.string()!!)
                }
                Handler().postDelayed({
                    loading.visibility = View.GONE
                }, 5000)

            }

            override fun onFailure(call: Call<HomeWorld>, t: Throwable) {
                Log.e(TAG, t.message!!)
                loading.visibility = View.GONE

            }
        })
    }

    private fun loadAdapter() {
        val mLayoutManager = GridLayoutManager(this, 2)
        recycler_characters.layoutManager = mLayoutManager
        adapter = CharacterAdapter(data)
        recycler_characters.adapter = adapter
    }

}