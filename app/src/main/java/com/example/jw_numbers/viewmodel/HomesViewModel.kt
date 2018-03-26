package com.example.jw_numbers.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.jw_numbers.model.CitiesContainer
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.services.DbManager

class HomesViewModel(val citiesContainer: CitiesContainer, private val dbManager: DbManager) : ViewModel() {

    fun setNumberDescriptionToDb(number: NumberDTO) {
        dbManager.setDescriptionOfNumber(number)
    }
}