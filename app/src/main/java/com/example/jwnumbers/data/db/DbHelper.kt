package com.example.jwnumbers.data.db

import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO

interface DbHelper {
    fun setDescriptionsFromDb(citiesContainer: CitiesContainer)

    fun setDescriptionToDB(numberDescription: NumberDTO)

    fun refreshStore(storeId: String)
}