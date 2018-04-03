package com.example.jwnumbers.data.repository

import io.reactivex.Completable

interface NumbersRepositoryHelper {
    fun installNumbersFromCurrentStore(storeId: String): Completable
}