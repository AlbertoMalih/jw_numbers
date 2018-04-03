package com.example.jwnumbers.viewmodel

import com.example.jwnumbers.activity.SplashActivityView
import com.example.jwnumbers.data.interactor.SplashInteractor
import com.example.jwnumbers.model.CitiesContainer
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent

class SplashViewModel(citiesContainer: CitiesContainer, private val interactor: SplashInteractor) : BaseViewModel<SplashActivityView>(citiesContainer), KoinComponent {

    fun installAllNumbers(storeId: String): Completable =
            Completable.create { subscriber ->
                interactor.doReceiveNumbersCall(storeId).observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ subscriber.onComplete() }, { error -> subscriber.onError(error) })
            }

    fun manageConnect() {
        view?.showStoreIdIfMaybe(interactor.getStoreId())
        if (interactor.isEnabledAutoConnectToStore())
            view?.onEnabledAutoConnect(interactor.getStoreId())
        else
            view?.onDisabledAutoConnect()
    }
}