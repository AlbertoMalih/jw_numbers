package com.example.jwnumbers.viewmodel

import android.content.SharedPreferences
import com.example.jwnumbers.activity.CitiesActivityView
import com.example.jwnumbers.model.CitiesContainer

class CitiesViewModel(citiesContainer: CitiesContainer, private val preferences: SharedPreferences)
    : BaseViewModel<CitiesActivityView>(citiesContainer) {

    fun markStopAutoConnectToRepository() {
        preferences.edit().putBoolean(WILL_IS_AUTO_CONNECT_TO_STORE_KEY, false).apply()
    }
}