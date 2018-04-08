package com.example.jwnumbers.ui.currentCity

import android.arch.lifecycle.MutableLiveData
import com.example.jwnumbers.data.interactor.DataInteractor
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.CityDTO
import com.example.jwnumbers.model.NumberDTO
import com.example.jwnumbers.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class CurrentCityViewModel(citiesContainer: CitiesContainer, private val interactor: DataInteractor) :
        BaseViewModel<CurrentCityNavigator>(citiesContainer) {

    var onLoadedCities: MutableLiveData<CityDTO> = MutableLiveData()


    fun setNumberDescriptionToDb(number: NumberDTO): Completable = Completable.create { emitter ->
        try {
            interactor.doWriteDescriptionIntoDbCall(number)
            emitter.onComplete()
        } catch (exception: Exception) {
            emitter.onError(exception)
        }
    }.subscribeOn(Schedulers.computation())

    fun loadCities() {
        onLoadedCities.value = citiesContainer.currentCity
    }
}