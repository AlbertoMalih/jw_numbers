package com.example.jwnumbers.data.repository

import android.util.Log
import com.example.jwnumbers.data.interactor.OnReceivedNumbers
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.CityDTO
import com.example.jwnumbers.model.NumberDTO
import com.google.firebase.firestore.FirebaseFirestore

private const val COLLECTION_STORES = "cities_Homes_Numbers_test_1"
private const val COLLECTION_NUMBERS = "cur_numbers"

class AppNumbersRepositoryHelper(private val citiesContainer: CitiesContainer) : NumbersRepositoryHelper {

    override fun installNumbersFromCurrentStore(listener: OnReceivedNumbers, storeId: String) {
        FirebaseFirestore.getInstance().collection(COLLECTION_STORES)
                .document(storeId).collection(COLLECTION_NUMBERS).get()
                .addOnSuccessListener { numbersDocuments ->
                    citiesContainer.clear()
                    numbersDocuments.documents
                            .filter { numberDoc -> numberDoc.id != "emptyObject" }
                            .forEach InCities@ { numberDoc ->
                                val currentNumber = numberDoc.toObject(NumberDTO::class.java)
                                currentNumber.id = numberDoc.id

                                addNumberToCities(currentNumber)
                            }
                    listener.onSuchReceived()
                }.addOnFailureListener { listener.onFailReceived() }
    }

    private fun addNumberToCities(currentNumber: NumberDTO) {
        (citiesContainer.getCityByName(currentNumber.place)?.numbers?.add(currentNumber)
                ?: citiesContainer.cities.add(CityDTO(mutableListOf(currentNumber), currentNumber.place)))
    }
}