package com.example.jwnumbers.data.db

import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO
import io.realm.Realm
import io.realm.RealmConfiguration

class AppDbHelper : DbHelper {
    private var realm = createRealm()


    override fun setDescriptionsFromDb(citiesContainer: CitiesContainer, listener: OnInsertedDescription) {
        try {
            getAllDescriptions().forEach { numberInDb ->
                citiesContainer.getNumberByCity(numberInDb.place, numberInDb.id).description = numberInDb.description
            }
            listener.onSuchInserted()
        } catch (exception: Exception) {
            listener.onFailInserted()
        }
    }

    override fun setDescriptionOfNumber(number: NumberDTO) {
        realm.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(number)
        }
    }

    override fun refreshStore() {
        if (realm.isClosed) realm = createRealm()
    }

    override fun close() {
        if (!realm.isClosed) realm.close()
    }


    private fun createRealm(): Realm = Realm.getInstance(RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build())

    private fun getAllDescriptions() = realm.where<NumberDTO>(NumberDTO::class.java)
            .isNotEmpty("description").findAll()
}