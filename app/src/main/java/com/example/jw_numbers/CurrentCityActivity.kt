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

class CurrentCityActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street)
        App.component.inject(this)
        title = viewModel.currentCity.name
        allHomes.adapter = HomesAdapter(viewModel, viewModel.currentCity.numbers, this)
    }
}


private class HomesAdapter(val viewModel: NumbersViewModel, val homes: List<NumberDTO>, val activity: CurrentCityActivity) : RecyclerView.Adapter<HomesAdapter.HomesViewHolder>() {
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

    inner class HomesViewHolder(view: View, val activity: CurrentCityActivity) : RecyclerView.ViewHolder(view) {
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
                        if (view.text.isNotEmpty()) descriptionHome.visibility = View.VISIBLE
                        else descriptionHome.visibility = View.GONE
                        currentHome.description = view.text.toString()
                        descriptionHome.paddingLeft.and(20)
                        descriptionHome.paddingRight.and(20)
                        descriptionHome.text = if (view.text.toString().length <= 25) view.text.toString() else view.text.toString().substring(0, 25) + "..."
                        viewModel.dbManager.setDescriptionOfNumber(currentHome)
                    }).create().show()
                }
            })
        }
    }
}