package com.example.jwnumbers.data.interactor

import com.example.jwnumbers.data.preferences.PreferencesHelper

class CitiesAppInteractor(private val preferences: PreferencesHelper) : CitiesInteractor {
    override fun doDisableAutoConnectCall() {
        preferences.markDisableAutoConnectToStore()
    }
}