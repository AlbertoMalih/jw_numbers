package com.example.jw_numbers.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import com.example.jw_numbers.OnGetUsersListener
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.services.DbManager
import com.google.firebase.firestore.FirebaseFirestore

class NumbersViewModel(val dbManager: DbManager, val context: Context) {
    var data: MutableMap<String, MutableMap<String, ArrayList<NumberDTO>>> = HashMap()
    var currentList: ArrayList<NumberDTO>? = null

    fun installAllUsers(listener: OnGetUsersListener, storeId: String) {
//        if (DatabaseUtils.queryNumEntries(dbManager.readableDatabase, TABLE_NAME) < 1) {
        if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.isConnected) {
            FirebaseFirestore.getInstance().collection("cities_Homes_Numbers")
                    .document(storeId).get()
                    .addOnSuccessListener { task ->
                        dbManager.insertAllNumbers(
                                task.data.filter { it.value is Map<*, *> }.mapTo(ArrayList()) {
                                    NumberDTO(number = it.key, place = (it.value as Map<*, *>)["place"] as String)
                                }.filter { it.number != "notValidObject" }
                        )
                        dbManager.installAllNotesInListener(this, listener)
                    }
        } else
            dbManager.installAllNotesInListener(this, listener)
    }
}