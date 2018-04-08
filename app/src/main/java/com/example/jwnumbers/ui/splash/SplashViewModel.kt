package com.example.jwnumbers.ui.splash

import android.arch.lifecycle.MutableLiveData
import com.example.jwnumbers.data.interactor.SplashInteractor
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.ui.base.BaseViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent

class SplashViewModel(citiesContainer: CitiesContainer, private val interactor: SplashInteractor) : BaseViewModel<SplashNavigator>(citiesContainer), KoinComponent {
    var isDisableAutoConnected: MutableLiveData<Boolean> = MutableLiveData()
    var storeId: MutableLiveData<String> = MutableLiveData()
    var resultLoadedNumbers: MutableLiveData<Boolean> = MutableLiveData()


    fun installAllNumbers(storeId: String) {
        Completable.create { subscriber ->
            interactor.doReceiveNumbersCall(storeId).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ subscriber.onComplete() }, { error -> subscriber.onError(error) })
        }.subscribe({
            resultLoadedNumbers.value = SUCH_LOADED_NUMBERS
            navigator.openCitiesActivity()
        }, { resultLoadedNumbers.value = FAIL_LOADED_NUMBERS })
    }

    fun manageConnect() {
        val storeIdInPreferences = interactor.getStoreId()
        storeIdInPreferences.let { it -> if (it.isNotEmpty()) storeId.value = it }

        interactor.isEnabledAutoConnectToStore().let { isEnabledAutoConnectPreferences ->
            isDisableAutoConnected.value = isEnabledAutoConnectPreferences
            if (isEnabledAutoConnectPreferences)
                installAllNumbers(storeIdInPreferences)
        }
    }
}

const val SUCH_LOADED_NUMBERS = true
const val FAIL_LOADED_NUMBERS = false