package com.example.jw_numbers.viewmodel

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.jw_numbers.OnGetUsersListener
import com.example.jw_numbers.R
import com.example.jw_numbers.SplashActivity
import com.example.jw_numbers.model.CityDTO
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.services.DbManager
import com.google.firebase.firestore.FirebaseFirestore


class NumbersViewModel(val dbManager: DbManager, val context: Context) {
    var data: ArrayList<CityDTO> = ArrayList()
    lateinit var currentCity: CityDTO
    var citiesNames = ArrayList<String>()

    fun installAllUsers(listener: OnGetUsersListener, storeId: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("StoreId", storeId).apply()
        if ((isOnline())) {
            FirebaseFirestore.getInstance().collection("cities_Homes_Numbers")
                    .document(storeId).get()
                    .addOnSuccessListener { task ->
                        if (!task.exists()) {
                            (listener as? SplashActivity)?.stopLoading()
                            Toast.makeText(context, context.getString(R.string.not_exist_store), Toast.LENGTH_LONG).show()
                            return@addOnSuccessListener
                        }
                        dbManager.restartStoreId(context)
                        dbManager.insertAllNumbers(/*todo refactore reate users in dbManager in background*/
                                task.data.filter { it.value is Map<*, *> && it.key is String && it.key != "notValidObject" }
                                        .mapTo(ArrayList()) {
                                            NumberDTO(number = it.key,
                                                    place = (it.value as Map<*, *>)["place"] as String,
                                                    name = (it.value as Map<*, *>)["name"] as String,
                                                    currentStoreId = storeId)
                                        })
                        dbManager.installAllNotesInListener(this, listener)
                    }
        } else
            dbManager.installAllNotesInListener(this, listener)
    }

    private fun isOnline(): Boolean {
        try {
            return java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com").waitFor() == 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}