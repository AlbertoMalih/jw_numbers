package com.example.jw_numbers.viewmodel

import android.content.Context
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.jw_numbers.OnGetUsersListener
import com.example.jw_numbers.R
import com.example.jw_numbers.SplashActivity
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.services.DbManager
import com.google.firebase.firestore.FirebaseFirestore


class NumbersViewModel(val dbManager: DbManager, val context: Context) {
    var data: MutableMap<String, MutableMap<String, ArrayList<NumberDTO>>> = HashMap()
    var currentList: ArrayList<NumberDTO>? = null

    fun installAllUsers(listener: OnGetUsersListener, storeId: String) {
        if (storeId != PreferenceManager.getDefaultSharedPreferences(context).getString("StoreId", ""))
            dbManager.deleteAllNotes()
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
                        dbManager.insertAllNumbers(
                                task.data.filter { it.value is Map<*, *> && it.key != "notValidObject" }
                                        .mapTo(ArrayList()) {
                                            NumberDTO(number = it.key, place = (it.value as Map<*, *>)["place"] as String)
                                        }
                        )
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