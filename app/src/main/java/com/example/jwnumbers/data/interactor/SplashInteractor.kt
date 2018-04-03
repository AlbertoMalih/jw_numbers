package com.example.jwnumbers.data.interactor

import io.reactivex.Completable

interface SplashInteractor {
    fun doReceiveNumbersCall(storeId: String): Completable

    fun isEnabledAutoConnectToStore(): Boolean

    fun getStoreId(): String

    fun doRefreshDb()
}