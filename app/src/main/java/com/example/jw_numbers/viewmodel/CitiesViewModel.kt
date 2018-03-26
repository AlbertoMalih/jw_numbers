package com.example.jw_numbers.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import com.example.jw_numbers.model.CitiesContainer

data class CitiesViewModel(val citiesContainer: CitiesContainer, private val preferences: SharedPreferences) : ViewModel() {

    fun markStopAutoConnectToRepository() {
        preferences.edit().putBoolean("isExit", false).apply()
    }
}