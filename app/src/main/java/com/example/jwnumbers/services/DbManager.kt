package com.example.jwnumbers.services

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.preference.PreferenceManager
import com.example.jwnumbers.activity.OnGetUsersListener
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.model.NumberDTO

const val TABLE_NAME = "numbers"
private const val COLUMN_ID = "_id"

private const val COLUMN_DESCRIPTION = "description"
private const val COLUMN_NUMBER = "number"
private const val COLUMN_NAME = "name"
private const val COLUMN_PLACE = "place"
private const val COLUMN_STORE_ID = "store_id"

class DbManager(context: Context) : SQLiteOpenHelper(context, "NumbersDb.db", null, 1) {
    private var storeId: String = ""

    init {
        restartStoreId(context)
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                "create table " + TABLE_NAME + " " +
                        "(" + COLUMN_ID + " integer primary key, " + COLUMN_DESCRIPTION +
                        " text, " + COLUMN_PLACE + " text, " + COLUMN_NUMBER + " text, " +
                        COLUMN_STORE_ID + " text, " + COLUMN_NAME + " text" + " )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }

    fun setDescriptionOfNumber(number: NumberDTO) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DESCRIPTION, number.description)

        if (1 > db.update(TABLE_NAME, contentValues, "${COLUMN_NUMBER} = ? AND ${COLUMN_STORE_ID} = ?",
                        arrayOf(number.number, storeId))) {
            contentValues.put(COLUMN_PLACE, number.place)
            contentValues.put(COLUMN_NUMBER, number.number)
            contentValues.put(COLUMN_STORE_ID, storeId)
            db.insert(TABLE_NAME, null, contentValues)
        }

        db.close()
    }

    fun installDescriptionToNumbers(citiesContainer: CitiesContainer, listener: OnGetUsersListener) {
        RequestAllNumbers(citiesContainer, this, listener, storeId).execute()
    }

    private fun restartStoreId(context: Context) {
        storeId = PreferenceManager.getDefaultSharedPreferences(context).getString("StoreId", "")
    }
}

private class RequestAllNumbers(val citiesContainer: CitiesContainer, val dbManager: DbManager, val listener: OnGetUsersListener,
                                val storeId: String) : AsyncTask<Void, Void, Void>() {

    override fun doInBackground(vararg p0: Void?): Void? {
        val allData = dbManager.writableDatabase.query(TABLE_NAME, null, COLUMN_STORE_ID + " = ?", arrayOf(storeId), null, null, COLUMN_PLACE)
        val descriptionIndex = allData.getColumnIndex(COLUMN_DESCRIPTION)
        val numberIndex = allData.getColumnIndex(COLUMN_NUMBER)
        val placeIndex = allData.getColumnIndex(COLUMN_PLACE)

        while (allData.moveToNext()) {
            val place = allData.getString(placeIndex)
            val number = allData.getString(numberIndex)
            citiesContainer.cities.first { currentCity -> currentCity.name == place }
                    .numbers.first { currentNumber -> currentNumber.number == number }
                    .description = allData.getString(descriptionIndex)

        }
        allData.close()
        dbManager.writableDatabase.close()
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.onSuchGetUsers()
    }
}