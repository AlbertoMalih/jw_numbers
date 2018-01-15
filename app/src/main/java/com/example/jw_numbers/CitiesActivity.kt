package com.example.jw_numbers

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jw_numbers.viewmodel.NumbersViewModel
import kotlinx.android.synthetic.main.activity_cities.*
import javax.inject.Inject

class CitiesActivity : AppCompatActivity() {
    @Inject lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setContentView(R.layout.activity_cities)
        allCities.adapter = CitiesAdapter(viewModel, viewModel.data.keys.toList(), this)
    }

    fun startStreetActivity(bundles: Bundle) {
        startActivity(Intent(this, StreetActivity::class.java).putExtras(bundles))
    }

}


class CitiesAdapter(val viewModel: NumbersViewModel, val cities: List<String>, val activity: CitiesActivity) : RecyclerView.Adapter<CitiesAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cities_item, parent, false), activity)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.name.text = cities[position]
    }

    override fun getItemCount() = cities.size

    inner class CityViewHolder(view: View, activity: CitiesActivity) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.nameCity)!!

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder(activity).setTitle("Выбери улицу")
                            .setItems(viewModel.data[cities[adapterPosition]]?.keys?.toTypedArray(),
                                    { dialog, item ->
                                        val currentStreet = viewModel.data[cities[adapterPosition]]?.entries?.toTypedArray()!![item]
                                        val bundles = Bundle()
                                        bundles.putSerializable("numberHome", currentStreet.key)
                                        viewModel.currentList = currentStreet.value
                                        dialog.dismiss()
                                        activity.startStreetActivity(bundles)
                                    }).show()
                }
            })
        }
    }
}