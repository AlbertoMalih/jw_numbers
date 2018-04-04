package com.example.jwnumbers.data.repository

import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.CityDTO
import com.example.jwnumbers.model.NumberDTO
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

private const val COLLECTION_STORES = "cities_Homes_Numbers_test_1"
private const val COLLECTION_NUMBERS = "cur_numbers"

class AppNumbersRepositoryHelper(private val citiesContainer: CitiesContainer) : NumbersRepositoryHelper {

    override fun installNumbersFromCurrentStore(storeId: String): Completable =
            Completable.create { emitter ->
                FirebaseFirestore.getInstance().collection(COLLECTION_STORES)
                        .document(storeId).collection(COLLECTION_NUMBERS).get()
                        .addOnSuccessListener { numbersDocuments ->
                            Completable.create {
                                citiesContainer.clear()
                                numbersDocuments.documents
                                        .filter { numberDoc -> numberDoc.id != "emptyObject" }
                                        .forEach InCities@ { numberDoc -> addNumberToCities(parseNumber(numberDoc)) }
                                emitter.onComplete()
                            }.subscribeOn(Schedulers.computation()).subscribe()
                        }.addOnFailureListener { error -> emitter.onError(error) }
            }

    private fun parseNumber(numberDoc: DocumentSnapshot): NumberDTO {
        val currentNumber = numberDoc.toObject(NumberDTO::class.java)
        currentNumber.number = numberDoc.id
        return currentNumber
    }

    private fun addNumberToCities(currentNumber: NumberDTO) {
        (citiesContainer.getCityByName(currentNumber.place)?.numbers?.add(currentNumber)
                ?: citiesContainer.cities.add(CityDTO(mutableListOf(currentNumber), currentNumber.place)))
    }
}