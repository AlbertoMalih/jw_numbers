package com.example.jwnumbers.activity

interface SplashActivityView {
    fun isWillAutoConnect(isWillAutoConnect: Boolean, storeId: String)
}

interface OnGetNumbersListener {
    fun onSuchGetNumbers()
    fun onFailGetNumbers()
}