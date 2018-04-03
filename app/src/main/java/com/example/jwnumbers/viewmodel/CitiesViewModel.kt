package com.example.jwnumbers.viewmodel

import com.example.jwnumbers.activity.CitiesActivityView
import com.example.jwnumbers.data.interactor.CitiesInteractor
import com.example.jwnumbers.model.CitiesContainer

class CitiesViewModel(citiesContainer: CitiesContainer, private val interator: CitiesInteractor)
    : BaseViewModel<CitiesActivityView>(citiesContainer) {

    fun markDisableAutoConnectToRepository() {
        interator.doDisableAutoConnectCall()
    }

    fun calculateCities() {
        view?.showCalculatedCities(citiesContainer)
    }
}