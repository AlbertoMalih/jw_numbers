package com.example.jwnumbers.activity

import android.os.Bundle
import com.example.jwnumbers.R
import com.example.jwnumbers.adapter.HomesAdapter
import com.example.jwnumbers.viewmodel.HomesViewModel
import kotlinx.android.synthetic.main.activity_street.*
import org.koin.android.ext.android.inject

class HomesActivity : BaseActivity<HomesActivityView>(), HomesActivityView {
    override val viewModel: HomesViewModel by inject()
    override var view: HomesActivityView = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_street)
        title = viewModel.citiesContainer.currentCity.name
        allHomes.adapter = HomesAdapter(viewModel, viewModel.citiesContainer.currentCity.numbers, this)
    }
}

