package com.example.jw_numbers

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.jw_numbers.viewmodel.NumbersViewModel
import kotlinx.android.synthetic.main.activity_cities.*
import javax.inject.Inject

class CitiesActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setContentView(R.layout.activity_cities)
    }

    override fun onResume() {
        super.onResume()
        allCities.adapter = CitiesAdapter(viewModel, viewModel.citiesNames, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit_menu_item -> {
                Toast.makeText(this, this.getString(R.string.exit_info), Toast.LENGTH_LONG).show()
                PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isExit", false).apply()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        var name = view.findViewById<TextView>(R.id.nameCity)

        init {
            view.setOnClickListener({ _ ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    viewModel.currentCity = viewModel.data[adapterPosition]
                    activity.startActivity(Intent(activity, CurrentCityActivity::class.java))
                }
            })
        }
    }
}