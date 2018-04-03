package com.example.jwnumbers.activity

interface SplashActivityView {
    fun onEnabledAutoConnect(storeId: String)

    fun onDisabledAutoConnect()

    fun showStoreIdIfMaybe(storeId: String)
}