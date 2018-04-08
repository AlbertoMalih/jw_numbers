package com.example.jwnumbers.ui.cities

import android.arch.lifecycle.MutableLiveData
import com.example.jwnumbers.data.interactor.DataInteractor
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.ui.base.BaseViewModel

class CitiesViewModel(citiesContainer: CitiesContainer, private val interator: DataInteractor)
    : BaseViewModel<CitiesNavigator>(citiesContainer) {

    var onLoadedCities: MutableLiveData<CitiesContainer> = MutableLiveData()


    fun onExitFromStore() {
        interator.doDisableAutoConnectCall()
    }

    fun loadCities() {
        onLoadedCities.value = citiesContainer
    }
}