package com.example.jwnumbers.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import com.example.jwnumbers.model.CitiesContainer

data class CitiesViewModel(val citiesContainer: CitiesContainer, private val preferences: SharedPreferences) : ViewModel() {

    fun markStopAutoConnectToRepository() {
        preferences.edit().putBoolean("isExit", false).apply()
    }
}