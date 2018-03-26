package com.example.jwnumbers.adapter;

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.jwnumbers.R
import com.example.jwnumbers.activity.CurrentCityActivity
import com.example.jwnumbers.model.NumberDTO
import com.example.jwnumbers.viewmodel.HomesViewModel

class HomesAdapter(val viewModel: HomesViewModel, val homes: List<NumberDTO>, private val activity: CurrentCityActivity) : RecyclerView.Adapter<HomesAdapter.HomesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false), activity)


    override fun onBindViewHolder(holder: HomesViewHolder, position: Int) {
        val currentHome = homes[position]
        holder.numberHome.text = currentHome.number
        holder.placeHome.text = currentHome.name
        if (currentHome.description.isNotEmpty()) {
            holder.descriptionHome.text = if (currentHome.description.length <= 25) currentHome.description else currentHome.description.substring(0, 25) + "..."
            holder.descriptionHome.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = homes.size

    inner class HomesViewHolder(view: View, private val activity: CurrentCityActivity) : RecyclerView.ViewHolder(view) {
        var numberHome = view.findViewById<TextView>(R.id.numberHome)
        var placeHome = view.findViewById<TextView>(R.id.placeHome)
        var descriptionHome = view.findViewById<TextView>(R.id.descriptionHome)

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentNumber = homes[adapterPosition]
                    val dialogView = EditText(activity)
                    dialogView.setText(currentNumber.description)
                    AlertDialog.Builder(activity).setView(dialogView).setPositiveButton("изменить", { _, _ ->
                        descriptionHome.visibility = if (dialogView.text.isNotEmpty()) View.VISIBLE else View.GONE
                        currentNumber.description = dialogView.text.toString()
                        viewModel.setNumberDescriptionToDb(currentNumber)
                        descriptionHome.paddingLeft.and(20)
                        descriptionHome.paddingRight.and(20)
                        descriptionHome.text = if (dialogView.text.toString().length <= 25) dialogView.text.toString() else
                            dialogView.text.toString().substring(0, 25) + "..."
                    }).create().show()
                }
            })
        }
    }
}