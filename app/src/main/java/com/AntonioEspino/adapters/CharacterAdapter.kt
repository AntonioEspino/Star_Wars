package com.AntonioEspino.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AntonioEspino.R
import com.AntonioEspino.api.ApiRest
import com.AntonioEspino.models.Character
import com.AntonioEspino.models.HomeWorld
import com.AntonioEspino.models.Specie
import kotlinx.android.synthetic.main.character_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CharacterAdapter(private val data: ArrayList<Character>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    val TAG = "MiApp"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Character) {
            itemView.name.text = item.name
            itemView.genre.text = item.gender
            itemView.specie.text = item.species[0].toString()
            itemView.home_world.text = item.homeworld
            itemView.card_character.setCardBackgroundColor(generateColor().toInt())
        }

        fun generateColor(): Long {
            val colors = arrayListOf(
                0xfff44336, 0xffe91e63, 0xff9c27b0, 0xff673ab7,
                0xff3f51b5, 0xff2196f3, 0xff03a9f4, 0xff00bcd4,
                0xff009688, 0xff4caf50, 0xff8bc34a, 0xffcddc39,
                0xffffc107, 0xffff9800, 0xffff5722,
                0xff795548, 0xff9e9e9e, 0xff607d8b, 0xff333333
            )
            return colors.random()
        }
    }
}
