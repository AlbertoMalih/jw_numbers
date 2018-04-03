package com.example.jwnumbers.data.interactor

import com.example.jwnumbers.model.NumberDTO

interface HomesInteractor {
    fun doWriteDescriptionIntoDbCall(number: NumberDTO)
}