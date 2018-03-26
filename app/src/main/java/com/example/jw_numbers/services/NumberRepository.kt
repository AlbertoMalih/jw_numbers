package com.example.jw_numbers.services

import com.example.jw_numbers.model.CitiesContainer
import com.example.jw_numbers.model.CityDTO
import com.example.jw_numbers.model.NumberDTO
import com.google.firebase.firestore.FirebaseFirestore

const val COLLECTION_STORES = "cities_Homes_Numbers_test_1"
const val COLLECTION_NUMBERS = "cur_numbers"

class NumberRepository(private val citiesContainer: CitiesContainer) {

    fun installAllUsers(listener: OnReceivedNumbers, storeId: String) {
        FirebaseFirestore.getInstance().collection(COLLECTION_STORES)
                .document(storeId).collection(COLLECTION_NUMBERS).get()
                .addOnSuccessListener { numbersDocuments ->
                    citiesContainer.clear()
                    numbersDocuments.documents.forEach InCities@ { numberDoc ->
                        if (numberDoc.id == "emptyObject") return@InCities

                        val currentNumber = numberDoc.toObject(NumberDTO::class.java)
                        currentNumber.number = numberDoc.id

                        citiesContainer.cities.forEach { currentCity ->
                            if (currentNumber.place == currentCity.name) {
                                currentCity.numbers.add(currentNumber)
                                return@InCities
                            }
                        }
                        citiesContainer.cities.add(CityDTO(mutableListOf(currentNumber), currentNumber.place))
                    }
                    listener.onSuchReceived()
                }.addOnFailureListener { listener.onFailReceived() }
    }
}

abstract class OnReceivedNumbers {
    abstract fun onSuchReceived()
    fun onFailReceived() {}
}