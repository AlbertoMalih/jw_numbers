package com.example.jwnumbers.ui.cities

import android.arch.lifecycle.MutableLiveData
import com.example.jwnumbers.data.interactor.CitiesInteractor
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.ui.base.BaseViewModel

class CitiesViewModel(citiesContainer: CitiesContainer, private val interator: CitiesInteractor)
    : BaseViewModel<CitiesNavigator>(citiesContainer) {

    var onLoadedCities: MutableLiveData<CitiesContainer> = MutableLiveData()


    fun onExitFromStore() {
        interator.doDisableAutoConnectCall()
    }

    fun loadCities() {
        onLoadedCities.value = citiesContainer
    }
}