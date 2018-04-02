package com.example.jwnumbers.data.db

import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO

interface DbHelper {

    fun setDescriptionsFromDb(citiesContainer: CitiesContainer, listener: OnInsertedDescription)

    fun setDescriptionOfNumber(number: NumberDTO)

    fun refreshStore()

    fun close()
}

interface OnInsertedDescription {
    fun onSuchInserted()
    fun onFailInserted()
}
