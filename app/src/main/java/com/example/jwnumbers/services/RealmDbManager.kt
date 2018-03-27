package com.example.jwnumbers.services

import com.example.jwnumbers.activity.OnGetNumbersListener
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmDbManager {
    private var realm = Realm.getInstance(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())


    fun installDescriptionToNumbers(citiesContainer: CitiesContainer, listener: OnGetNumbersListener) {
        for (numberInDb in getAllDescriptions()) {
            citiesContainer.getNumberByCity(numberInDb.place, numberInDb.id).description = numberInDb.description
        }
        listener.onSuchGetNumbers()
    }

    fun setDescriptionOfNumber(number: NumberDTO) {
        realm.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(number)
        }
    }

    fun close() {
        if (!realm.isClosed) realm.close()
    }


    private fun getAllDescriptions() = realm.where<NumberDTO>(NumberDTO::class.java)
            .isNotEmpty("description").findAll()
}