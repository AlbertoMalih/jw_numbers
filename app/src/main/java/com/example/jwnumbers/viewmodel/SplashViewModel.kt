package com.example.jwnumbers.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import com.example.jwnumbers.activity.OnGetUsersListener
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.services.DbManager
import com.example.jwnumbers.services.NumberRepository
import com.example.jwnumbers.services.OnReceivedNumbers
import io.realm.Realm


class SplashViewModel(val citiesContainer: CitiesContainer, val dbManager: DbManager, private val preferences: SharedPreferences,
                      private val realmDb: Realm, private val repository: NumberRepository) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        realmDb.close()
    }

    fun installAllUsers(listener: OnReceivedNumbers, storeId: String) {
        repository.installAllUsers(object : OnReceivedNumbers() {
            override fun onSuchReceived() {
                if (citiesContainer.cities.isEmpty()) {
                    listener.onFailReceived()
                    return
                }

                citiesContainer.cities.sortBy { it.name }
                citiesContainer.cityNames = citiesContainer.cities.mapTo(mutableListOf()) {city-> city.name }
                citiesContainer.cityNames.sort()
                citiesContainer.cities.forEach { itCity -> itCity.numbers.sortBy { itNumber -> itNumber.number } }

                preferences.edit().putString("StoreId", storeId).apply()

                dbManager.installDescriptionToNumbers(citiesContainer, object : OnGetUsersListener {
                    override fun onSuchGetUsers() {
                        listener.onSuchReceived()
                    }

                    override fun onFailGetUsers() {
                        listener.onFailReceived()
                    }
                })
            }
        }, storeId)
    }
}