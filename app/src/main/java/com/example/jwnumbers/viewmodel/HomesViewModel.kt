package com.example.jwnumbers.viewmodel

import com.example.jwnumbers.activity.HomesActivityView
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import com.example.jwnumbers.services.RealmDbManager

class HomesViewModel(citiesContainer: CitiesContainer, private val realmDbManager: RealmDbManager) :
        BaseViewModel<HomesActivityView>(citiesContainer) {

    override fun onCleared() {
        super.onCleared()
        realmDbManager.close()
    }

    fun setNumberDescriptionToDb(number: NumberDTO) {
        realmDbManager.setDescriptionOfNumber(number)
    }
}