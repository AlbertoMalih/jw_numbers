package com.example.jwnumbers.ui.cities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.jwnumbers.R
import com.example.jwnumbers.ui.currentCity.CurrentCityActivity
import com.example.jwnumbers.ui.splash.SplashActivity
import com.example.jwnumbers.model.CitiesContainer
import kotlinx.android.synthetic.main.activity_cities.*
import org.koin.android.architecture.ext.viewModel

class CitiesActivity : AppCompatActivity(), CitiesNavigator {
    private val viewModel: CitiesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        setContentView(R.layout.activity_cities)
        observeOnLoadedCities()
        viewModel.loadCities()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit_menu_item -> {
                viewModel.onExitFromStore()
                startActivity(Intent(this, SplashActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openStreetActivity() {
        startActivity(Intent(this, CurrentCityActivity::class.java))
    }


    private fun observeOnLoadedCities() {
        viewModel.onLoadedCities.observe(this, Observer { cities ->
            cities?.let { showLoadedCities(cities) }
        })
    }

    private fun showLoadedCities(cities: CitiesContainer) {
        allCities.adapter = CitiesAdapter(cities, cities.cityNames, this)
    }
}
