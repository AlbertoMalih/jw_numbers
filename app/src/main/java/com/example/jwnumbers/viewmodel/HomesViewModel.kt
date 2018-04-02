package com.example.jwnumbers.viewmodel

import com.example.jwnumbers.activity.HomesActivityView
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import com.example.jwnumbers.data.db.DbHelper

class HomesViewModel(citiesContainer: CitiesContainer, private val dbManager: DbHelper) :
        BaseViewModel<HomesActivityView>(citiesContainer) {

    init {
        dbManager.refreshStore()
    }

    override fun onCleared() {
        super.onCleared()
        dbManager.close()
    }

    fun setNumberDescriptionToDb(number: NumberDTO) {
        dbManager.setDescriptionOfNumber(number)
    }

    fun calculateCities() {
        view?.showCalculatedCities(citiesContainer)
    }
}