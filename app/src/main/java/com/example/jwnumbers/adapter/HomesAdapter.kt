package com.example.jwnumbers.adapter

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.jwnumbers.R
import com.example.jwnumbers.activity.HomesActivity
import com.example.jwnumbers.model.NumberDTO
import com.example.jwnumbers.viewmodel.HomesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class HomesAdapter(val viewModel: HomesViewModel, val homes: List<NumberDTO>, private val activity: HomesActivity) :
        RecyclerView.Adapter<HomesAdapter.HomesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false), activity)


    override fun onBindViewHolder(holder: HomesViewHolder, position: Int) {
        val currentHome = homes[position]
        holder.numberHome.text = currentHome.number
        holder.placeHome.text = currentHome.name
        if (currentHome.description.isNotEmpty()) {
            holder.descriptionHome.text =
                    if (currentHome.description.length <= 25) currentHome.description else
                        currentHome.description.substring(0, 25) + "..."
            holder.descriptionHome.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = homes.size

    inner class HomesViewHolder(view: View, private val activity: HomesActivity) : RecyclerView.ViewHolder(view) {
        var numberHome = view.findViewById<TextView>(R.id.numberHome)!!
        var placeHome = view.findViewById<TextView>(R.id.placeHome)!!
        var descriptionHome = view.findViewById<TextView>(R.id.descriptionHome)!!

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentNumber = homes[adapterPosition]
                    val dialogView = EditText(activity)
                    dialogView.setText(currentNumber.description)
                    AlertDialog.Builder(activity).setView(dialogView).setPositiveButton("изменить", { _, _ ->
                        changeDescription(dialogView, currentNumber)
                    }).create().show()
                }
            })
        }

        private fun changeDescription(descriptionsText: EditText, currentNumber: NumberDTO) {
            currentNumber.description = descriptionsText.text.toString()
            viewModel.setNumberDescriptionToDb(currentNumber).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ showDescription(descriptionsText) }, {})
        }

        private fun showDescription(descriptionsText: EditText) {
            descriptionHome.visibility = if (descriptionsText.text.isNotEmpty()) View.VISIBLE else View.GONE
            descriptionHome.text = if (descriptionsText.text.toString().length <= 25) descriptionsText.text.toString() else
                descriptionsText.text.toString().substring(0, 25) + "..."
        }
    }
}