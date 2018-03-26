package com.example.jwnumbers.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jwnumbers.R
import com.example.jwnumbers.adapter.HomesAdapter
import com.example.jwnumbers.viewmodel.HomesViewModel
import kotlinx.android.synthetic.main.activity_street.*
import org.koin.android.ext.android.inject

class CurrentCityActivity : AppCompatActivity() {
    private val homesViewModel: HomesViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street)
        title = homesViewModel.citiesContainer.currentCity.name
        allHomes.adapter = HomesAdapter(homesViewModel, homesViewModel.citiesContainer.currentCity.numbers, this)
    }
}

