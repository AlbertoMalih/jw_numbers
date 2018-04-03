package com.example.jwnumbers.activity

import android.os.Bundle
import com.example.jwnumbers.R
import com.example.jwnumbers.adapter.HomesAdapter
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.viewmodel.HomesViewModel
import kotlinx.android.synthetic.main.activity_street.*
import org.koin.android.architecture.ext.viewModel

class HomesActivity : BaseActivity<HomesActivityView>(), HomesActivityView {
    override val viewModel: HomesViewModel by viewModel()
    override var view: HomesActivityView = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street)
        viewModel.calculateCities()
    }

    override fun showCalculatedCities(cities: CitiesContainer) {
        title = cities.currentCity.name
        allHomes.adapter = HomesAdapter(viewModel, cities.currentCity.numbers, this)
    }
}

