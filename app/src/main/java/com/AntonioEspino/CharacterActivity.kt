package com.AntonioEspino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AntonioEspino.adapters.CharacterAdapter
import com.AntonioEspino.adapters.FilmsAdapter
import com.AntonioEspino.api.ApiRest
import com.AntonioEspino.models.*
import kotlinx.android.synthetic.main.activity_character.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        DataHolder.filmSelected?.characters?.forEach { character ->
            val call = ApiRest.service.getCharacterByUrl(character)

            call.enqueue(object : Callback<Character> {

                override fun onResponse(call: Call<Character>, response: Response<Character>) {

                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        data.add(body)
                        adapter?.notifyDataSetChanged()
                    } else {
                        Log.e(TAG, response.errorBody()?.string()!!)
                    }
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {
                    Log.e(TAG, t.message!!)
                }
            })
        }


    }

    fun loadAdapter() {
        val mLayoutManager = GridLayoutManager(this, 2)
        recycler_characters.layoutManager = mLayoutManager
        adapter = CharacterAdapter(data)
        recycler_characters.adapter = adapter
    }

}