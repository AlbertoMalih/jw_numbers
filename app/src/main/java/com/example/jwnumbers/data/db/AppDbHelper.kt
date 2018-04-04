package com.example.jwnumbers.data.db

import android.app.Application
import android.arch.persistence.room.Room
import com.example.jwnumbers.data.db.descriptions.Description
import com.example.jwnumbers.data.db.descriptions.DescriptionsDatabase
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO

internal const val DESCRIPTION_TABLE_DB_NAME = "descriptions"

class AppDbHelper(appContext: Application) : DbHelper {
    private val descriptionsDatabase: DescriptionsDatabase = Room.databaseBuilder(appContext, DescriptionsDatabase::class.java, DESCRIPTION_TABLE_DB_NAME).build()
    private lateinit var storeId: String


    override fun setDescriptionsFromDb(citiesContainer: CitiesContainer) {
        descriptionsDatabase.descriptionDao().getAllNumbersWithDescription(storeId).forEach { descriptionInDb ->
            citiesContainer.getNumberByCity(descriptionInDb.numberPlace, descriptionInDb.numberId)?.description = descriptionInDb.description
        }
    }

    override fun setDescriptionToDB(numberDescription: NumberDTO) {
        descriptionsDatabase.descriptionDao().insertDescription(
                Description(description = numberDescription.description,
                        numberPlace = numberDescription.place, numberId = numberDescription.number, storeId = storeId)
        )
    }

    override fun refreshStore(storeId: String) {
        this.storeId = storeId
    }
}