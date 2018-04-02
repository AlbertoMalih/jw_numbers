package com.example.jwnumbers.data.repository

import com.example.jwnumbers.data.interactor.OnReceivedNumbers

interface NumbersRepositoryHelper {
    fun installNumbersFromCurrentStore(listener: OnReceivedNumbers, storeId: String)
}