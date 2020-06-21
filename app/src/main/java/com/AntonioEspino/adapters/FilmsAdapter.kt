package com.AntonioEspino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.AntonioEspino.R
import com.AntonioEspino.models.Film
import kotlinx.android.synthetic.main.film_item.view.*


class FilmsAdapter(private val data: ArrayList<Film>, val filmListener: FilmsListener) :
    RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Film) {
            itemView.title.text = item.title
            itemView.card.setCardBackgroundColor(generateColor().toInt())
            itemView.card.setOnClickListener {
                filmListener.onClick(item)
            }
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

interface FilmsListener {
    fun onClick(FilmSelected: Film)
}