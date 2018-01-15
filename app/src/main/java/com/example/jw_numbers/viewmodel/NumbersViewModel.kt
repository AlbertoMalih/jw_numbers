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

    fun installAllUsers(listener: OnGetUsersListener) {
//        if (DatabaseUtils.queryNumEntries(dbManager.readableDatabase, TABLE_NAME) < 1) {
        if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.isConnected) {
            FirebaseFirestore.getInstance().collection("citiesHomesNumbers").get().addOnCompleteListener({ task ->
                val numbers: MutableList<NumberDTO> = ArrayList()
                task.result.documents.mapTo(numbers) {
                    NumberDTO(number = it.data["number"] as String, place = it.data["place"] as String, firebaseId = it.id)
                }
                dbManager.insertAllNumbers(numbers)
                dbManager.installAllNotesInListener(this, listener)
            })
        } else
            dbManager.installAllNotesInListener(this, listener)
    }
}