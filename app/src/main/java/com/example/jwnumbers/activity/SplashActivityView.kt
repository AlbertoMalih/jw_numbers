package com.example.jwnumbers.activity

interface SplashActivityView {
    //    fun isWillAutoConnect(isWillAutoConnect: Boolean, storeId: String)
    fun onEnabledAutoConnect(storeId: String)

    fun onDisabledAutoConnect()
}

interface OnGetNumbersListener {
    fun onSuchGetNumbers()
    fun onFailGetNumbers()
}