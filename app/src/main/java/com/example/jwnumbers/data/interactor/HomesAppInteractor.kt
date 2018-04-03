package com.example.jwnumbers.data.interactor

import com.example.jwnumbers.data.db.DbHelper
import com.example.jwnumbers.model.NumberDTO

class HomesAppInteractor(private val dbHelper: DbHelper) : HomesInteractor {
    override fun doWriteDescriptionIntoDbCall(number: NumberDTO) {
        dbHelper.setDescriptionToDB(number)
    }
}