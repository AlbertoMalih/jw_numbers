package com.example.jwnumbers.data.interactor

interface SplashInteractor {
    fun doReceiveNumbersCall(listener: OnReceivedNumbers, storeId: String)

    fun doCloseDb()

    fun isEnabledAutoConnectToStore(): Boolean

    fun getStoreId(): String
    fun doRefreshDb()
}