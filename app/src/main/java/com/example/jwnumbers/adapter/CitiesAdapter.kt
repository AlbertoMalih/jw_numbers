package com.example.jwnumbers.adapter;

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jwnumbers.R
import com.example.jwnumbers.activity.CitiesActivity
import com.example.jwnumbers.activity.HomesActivity
import com.example.jwnumbers.model.CitiesContainer

class CitiesAdapter(private val citiesContainer: CitiesContainer, private val cityNames: List<String>, private val activity: CitiesActivity) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cities_item, parent, false), activity)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.name.text = cityNames[position]
    }

    override fun getItemCount() = cityNames.size

    inner class CityViewHolder(view: View, activity: CitiesActivity) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.nameCity)

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    citiesContainer.currentCity = citiesContainer.cities[adapterPosition]
                    activity.startActivity(Intent(activity, HomesActivity::class.java))
                }
            })
        }
    }
}