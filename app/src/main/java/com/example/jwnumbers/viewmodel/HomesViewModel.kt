package com.example.jwnumbers.viewmodel

import com.example.jwnumbers.activity.HomesActivityView
import com.example.jwnumbers.data.interactor.HomesInteractor
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class HomesViewModel(citiesContainer: CitiesContainer, private val interactor: HomesInteractor) :
        BaseViewModel<HomesActivityView>(citiesContainer) {

    fun setNumberDescriptionToDb(number: NumberDTO): Completable = Completable.create { emitter ->
        try {
            interactor.doWriteDescriptionIntoDbCall(number)
            emitter.onComplete()
        }catch (exception: Exception){
            emitter.onError(exception)
        }
    }.subscribeOn(Schedulers.computation())

    fun calculateCities() {
        view?.showCalculatedCities(citiesContainer)
    }
}