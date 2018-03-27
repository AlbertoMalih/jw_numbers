package com.example.jwnumbers.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.jwnumbers.R
import com.example.jwnumbers.adapter.CitiesAdapter
import com.example.jwnumbers.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.activity_cities.*
import org.koin.android.ext.android.inject

class CitiesActivity : BaseActivity<CitiesActivityView>(), CitiesActivityView {
    override val viewModel: CitiesViewModel by inject()
    override var view: CitiesActivityView = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
    }

    override fun onResume() {
        super.onResume()
        allCities.adapter = CitiesAdapter(viewModel.citiesContainer, viewModel.citiesContainer.cityNames, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit_menu_item -> {
                Toast.makeText(this, this.getString(R.string.exit_info), Toast.LENGTH_LONG).show()
                viewModel.markStopAutoConnectToRepository()
                startActivity(Intent(this, SplashActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}