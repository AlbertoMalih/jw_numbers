package com.example.jw_numbers

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.viewmodel.NumbersViewModel
import kotlinx.android.synthetic.main.activity_street.*
import javax.inject.Inject

class StreetActivity : AppCompatActivity() {
    @Inject lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street)
        App.component.inject(this)
        title = intent.extras.getString("name") ?: ""
        allHomes.adapter = HomesAdapter(viewModel, viewModel.currentList ?: arrayListOf(), this)
    }
}


private class HomesAdapter(val viewModel: NumbersViewModel, val homes: List<NumberDTO>, val activity: StreetActivity) : RecyclerView.Adapter<HomesAdapter.HomesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false), activity)


    override fun onBindViewHolder(holder: HomesViewHolder, position: Int) {
        val currentHome = homes[position]
        holder.numberHome.text = currentHome.number
        holder.placeHome.text = currentHome.place.split("//").last()
        holder.descriptionHome.text = if (currentHome.description.length <= 25) currentHome.description else currentHome.description.substring(0, 25) + "..."
    }

    override fun getItemCount() = homes.size

    inner class HomesViewHolder(view: View, val activity: StreetActivity) : RecyclerView.ViewHolder(view) {
        var numberHome = view.findViewById<TextView>(R.id.numberHome)
        var placeHome = view.findViewById<TextView>(R.id.placeHome)
        var descriptionHome = view.findViewById<TextView>(R.id.descriptionHome)

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val currentHome = homes[adapterPosition]
                    val view = EditText(activity)
                    view.setText(currentHome.description)
                    AlertDialog.Builder(activity).setView(view).setPositiveButton("изменить", { interfaceDialog, _ ->
                        currentHome.description = view.text.toString()
                        descriptionHome.text = if (view.text.toString().length <= 25) view.text.toString() else view.text.toString().substring(0, 25) + "..."
                        viewModel.dbManager.setDescriptionOfNumber(currentHome)
                    }).create().show()
                }
            })
        }
    }
}