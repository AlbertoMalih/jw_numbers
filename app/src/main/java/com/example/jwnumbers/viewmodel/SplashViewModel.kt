package com.example.jwnumbers.viewmodel

import android.content.SharedPreferences
import com.example.jwnumbers.activity.OnGetNumbersListener
import com.example.jwnumbers.activity.SplashActivityView
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.services.NumberRepository
import com.example.jwnumbers.services.OnReceivedNumbers
import com.example.jwnumbers.services.RealmDbManager
import org.koin.standalone.KoinComponent

const val STORE_ID_KEY = "StoreId"
const val WILL_IS_AUTO_CONNECT_TO_STORE_KEY = "WILL_IS_AUTO_CONNECT_TO_STORE_KEY"

class SplashViewModel(citiesContainer: CitiesContainer, private val preferences: SharedPreferences,
                      private val repository: NumberRepository, private val realmDbManager: RealmDbManager) : BaseViewModel<SplashActivityView>(citiesContainer), KoinComponent {

    override fun onCleared() {
        super.onCleared()
        realmDbManager.close()
    }

    fun installAllNumbers(listener: OnReceivedNumbers, storeId: String) {
        repository.installNumbersFromCurrentStore(object : OnReceivedNumbers() {
            override fun onSuchReceived() {
                if (citiesContainer.cities.isEmpty()) {
                    listener.onFailReceived()
                    return
                }

                preferences.edit().putString(STORE_ID_KEY, storeId).apply()
                preferences.edit().putBoolean(WILL_IS_AUTO_CONNECT_TO_STORE_KEY, true).apply()

                sortCities()

                realmDbManager.installDescriptionToNumbers(citiesContainer, object : OnGetNumbersListener {
                    override fun onSuchGetNumbers() {
                        listener.onSuchReceived()
                    }

                    override fun onFailGetNumbers() {
                        listener.onFailReceived()
                    }
                })
            }
        }, storeId)
    }

    private fun sortCities() {
        citiesContainer.cities.sortBy { it.name }
        citiesContainer.cityNames = citiesContainer.cities.mapTo(mutableListOf()) { city -> city.name }
        citiesContainer.cityNames.sort()
        citiesContainer.cities.forEach { itCity -> itCity.numbers.sortBy { itNumber -> itNumber.number } }
    }

    fun manageConnect() {
        view?.isWillAutoConnect(preferences.getBoolean(WILL_IS_AUTO_CONNECT_TO_STORE_KEY, false), preferences.getString(STORE_ID_KEY, ""))
    }
}