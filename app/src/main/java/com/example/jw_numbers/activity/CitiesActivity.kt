package com.example.jw_numbers.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.jw_numbers.R
import com.example.jw_numbers.adapter.CitiesAdapter
import com.example.jw_numbers.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.activity_cities.*
import org.koin.android.ext.android.inject

class CitiesActivity : AppCompatActivity() {
    private val viewModel: CitiesViewModel  by inject()


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
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onBackPressed() {
//        finishAffinity()
//    }
}