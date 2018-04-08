package com.example.jwnumbers.ui.currentCity

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.jwnumbers.R
import com.example.jwnumbers.model.NumberDTO
import io.reactivex.android.schedulers.AndroidSchedulers

class CurrentCityAdapter(val viewModel: CurrentCityViewModel, val homes: List<NumberDTO>) :
        RecyclerView.Adapter<CurrentCityAdapter.HomesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false))

    override fun onBindViewHolder(holder: HomesViewHolder, position: Int) {
        val currentHome = homes[position]
        holder.numberHome.text = currentHome.number
        holder.placeHome.text = currentHome.name
        if (currentHome.description.isNotEmpty()) {
            holder.descriptionHome.text = getShortDescription(currentHome.description)
            holder.descriptionHome.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = homes.size


    private fun getShortDescription(description: String) =
            if (description.length <= 25) description else description.substring(0, 25) + "..."


    inner class HomesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var numberHome = view.findViewById<TextView>(R.id.numberHome)!!
        var placeHome = view.findViewById<TextView>(R.id.placeHome)!!
        var descriptionHome = view.findViewById<TextView>(R.id.descriptionHome)!!

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentNumber = homes[adapterPosition]
                    val dialogView = EditText(view.context)
                    dialogView.setText(currentNumber.description)
                    AlertDialog.Builder(view.context).setView(dialogView).setPositiveButton("изменить", { _, _ ->
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
            descriptionHome.text = getShortDescription(descriptionsText.text.toString())
        }
    }
}