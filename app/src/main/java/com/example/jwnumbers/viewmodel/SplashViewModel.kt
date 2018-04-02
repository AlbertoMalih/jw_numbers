package com.example.jwnumbers.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.example.jwnumbers.activity.SplashActivityView
import com.example.jwnumbers.data.Response
import com.example.jwnumbers.data.interactor.OnReceivedNumbers
import com.example.jwnumbers.data.interactor.SplashInteractor
import com.example.jwnumbers.model.CitiesContainer
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.internal.operators.completable.CompletableFromObservable
import org.koin.standalone.KoinComponent

class SplashViewModel(citiesContainer: CitiesContainer, private val interactor: SplashInteractor) : BaseViewModel<SplashActivityView>(citiesContainer), KoinComponent {
//    lateinit var responseReceivedNumbers: Observable<Void>


    init {
        interactor.doRefreshDb()
    }

    override fun onCleared() {
        super.onCleared()
        interactor.doCloseDb()
    }

    fun installAllNumbers(listener: OnReceivedNumbers, storeId: String) {
        interactor.doReceiveNumbersCall(object : OnReceivedNumbers() {
            override fun onSuchReceived() {
                listener.onSuchReceived()
            }

            override fun onFailReceived() {
                listener.onFailReceived()
            }
        }, storeId)
    }

    fun manageConnect() {
        if (interactor.isEnabledAutoConnectToStore())
            view?.onEnabledAutoConnect(interactor.getStoreId())
        else
            view?.onDisabledAutoConnect()
    }
}