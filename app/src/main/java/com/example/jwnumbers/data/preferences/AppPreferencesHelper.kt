package com.example.jwnumbers.data.preferences

import android.content.SharedPreferences


const val STORE_ID_KEY = "StoreId"
const val WILL_IS_AUTO_CONNECT_TO_STORE_KEY = "WILL_IS_AUTO_CONNECT_TO_STORE_KEY"

class AppPreferencesHelper(private val preferences: SharedPreferences) : PreferencesHelper {

    override fun markDisableAutoConnectToStore() {
        preferences.edit().putBoolean(WILL_IS_AUTO_CONNECT_TO_STORE_KEY, false).apply()
    }

    override fun markEnabledAutoConnectToStore() {
        preferences.edit().putBoolean(WILL_IS_AUTO_CONNECT_TO_STORE_KEY, true).apply()
    }

    override fun isEnabledAutoConnectToStore(): Boolean = preferences.getBoolean(WILL_IS_AUTO_CONNECT_TO_STORE_KEY, false)

    override fun getStoreId(): String = preferences.getString(STORE_ID_KEY, "")

    override fun setStoreId(storeId: String) {
        preferences.edit().putString(STORE_ID_KEY, storeId).apply()
    }
}