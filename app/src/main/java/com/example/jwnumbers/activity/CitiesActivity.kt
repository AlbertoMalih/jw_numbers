package com.example.jwnumbers.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.jwnumbers.R
import com.example.jwnumbers.adapter.CitiesAdapter
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.activity_cities.*
import org.koin.android.architecture.ext.viewModel

class CitiesActivity : BaseActivity<CitiesActivityView>(), CitiesActivityView {
    override val viewModel: CitiesViewModel by viewModel()
    override var view: CitiesActivityView = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        viewModel.calculateCities()
    }

    override fun showCalculatedCities(cities: CitiesContainer) {
        allCities.adapter = CitiesAdapter(cities, cities.cityNames, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit_menu_item -> {
                viewModel.markDisableAutoConnectToRepository()
                startActivity(Intent(this, SplashActivity::class.java)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}