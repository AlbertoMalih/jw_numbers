package com.example.jwnumbers.data.interactor

import com.example.jwnumbers.model.NumberDTO
import io.reactivex.Completable

interface DataInteractor {
    fun doDisableAutoConnectCall()

    fun doWriteDescriptionIntoDbCall(number: NumberDTO)

    fun doReceiveNumbersCall(storeId: String): Completable

    fun isEnabledAutoConnectToStore(): Boolean

    fun getStoreId(): String

    fun doRefreshDb()
}