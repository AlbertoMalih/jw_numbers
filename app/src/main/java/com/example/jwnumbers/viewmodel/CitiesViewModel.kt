package com.example.jwnumbers.viewmodel

import com.example.jwnumbers.activity.CitiesActivityView
import com.example.jwnumbers.data.preferences.PreferencesHelper
import com.example.jwnumbers.model.CitiesContainer

class CitiesViewModel(citiesContainer: CitiesContainer, private val preferences: PreferencesHelper)
    : BaseViewModel<CitiesActivityView>(citiesContainer) {

    fun markDisableAutoConnectToRepository() {
        preferences.markDisableAutoConnectToStore()
    }

    fun calculateCities() {
        view?.showCalculatedCities(citiesContainer)
    }
}