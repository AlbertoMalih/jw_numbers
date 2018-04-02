package com.example.jwnumbers.data.preferences

interface PreferencesHelper {
    fun markDisableAutoConnectToStore()

    fun markEnabledAutoConnectToStore()

    fun isEnabledAutoConnectToStore(): Boolean

    fun getStoreId(): String

    fun setStoreId(storeId: String)
}