package com.example.jwnumbers.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import com.example.jwnumbers.services.DbManager

class HomesViewModel(val citiesContainer: CitiesContainer, private val dbManager: DbManager) : ViewModel() {

    fun setNumberDescriptionToDb(number: NumberDTO) {
        dbManager.setDescriptionOfNumber(number)
    }
}