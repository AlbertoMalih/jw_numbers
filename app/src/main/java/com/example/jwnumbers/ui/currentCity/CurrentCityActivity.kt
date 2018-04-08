package com.example.jwnumbers.ui.currentCity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jwnumbers.R
import com.example.jwnumbers.model.CityDTO
import kotlinx.android.synthetic.main.activity_current_city.*
import org.koin.android.architecture.ext.viewModel

class CurrentCityActivity : AppCompatActivity(), CurrentCityNavigator {
    private val viewModel: CurrentCityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_city)
        viewModel.navigator = this
        viewModel.onLoadedCities.observe(this, Observer { city -> city?.let { showCalculatedCities(city) } })
        viewModel.loadCities()
    }

    private fun showCalculatedCities(currentCity: CityDTO) {
        title = currentCity.name
        allHomes.adapter = CurrentCityAdapter(viewModel, currentCity.numbers)
    }
}