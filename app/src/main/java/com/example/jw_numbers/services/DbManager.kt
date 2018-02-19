package com.example.jw_numbers.services

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.preference.PreferenceManager
import android.support.annotation.Nullable
import com.example.jw_numbers.OnGetUsersListener
import com.example.jw_numbers.model.CityDTO
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.viewmodel.NumbersViewModel

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

    fun insertAllNumbers(numbers: List<NumberDTO>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            for (number in numbers) {
                val contentValues = ContentValues()
                contentValues.put(COLUMN_PLACE, number.place)
                contentValues.put(COLUMN_NUMBER, number.number)
                contentValues.put(COLUMN_NAME, number.name)
                contentValues.put(COLUMN_STORE_ID, number.currentStoreId)

                if (1 > db.update(TABLE_NAME, contentValues, "$COLUMN_NUMBER = ? AND $COLUMN_STORE_ID = ?",
                                arrayOf(number.number, storeId))) {
                    contentValues.put(COLUMN_DESCRIPTION, number.description)
                    db.insert(TABLE_NAME, null, contentValues)
                }
            }
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
        db.close()
    }

    fun setDescriptionOfNumber(number: NumberDTO) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DESCRIPTION, number.description)
        db.update(TABLE_NAME, contentValues, "$COLUMN_NUMBER = ? AND $COLUMN_STORE_ID = ?",
                arrayOf(number.number, storeId))
        db.close()
    }

    fun installAllNotes(viewModel: NumbersViewModel, listener: OnGetUsersListener, dataForInstallToDb: Map<String, *>?) {
        RequestAllNumbers(viewModel, this, listener, storeId, dataForInstallToDb).execute()
    }

    fun restartStoreId(context: Context) {
        storeId = PreferenceManager.getDefaultSharedPreferences(context).getString("StoreId", "")
    }
}

private class RequestAllNumbers(val viewModel: NumbersViewModel, val dbManager: DbManager, val listener: OnGetUsersListener,
                                val storeId: String, @Nullable val dataForInstallToDb: Map<String, *>?) : AsyncTask<Void, Void, ArrayList<CityDTO>>() {

    override fun doInBackground(vararg p0: Void?): ArrayList<CityDTO> {
        val data = ArrayList<CityDTO>()
        dataForInstallToDb?.let {
            dbManager.insertAllNumbers(it.filter { itInFilter -> itInFilter.value is Map<*, *> && itInFilter.key != "notValidObject" }
                    .mapTo(ArrayList()) { currentNumberData ->
                        NumberDTO(number = currentNumberData.key,
                                place = (currentNumberData.value as Map<*, *>)["place"] as String,
                                name = (currentNumberData.value as Map<*, *>)["name"] as String,
                                currentStoreId = storeId)
                    })
        }
        val allData = dbManager.writableDatabase.query(TABLE_NAME, null, COLUMN_STORE_ID + " = ?", arrayOf(storeId), null, null, COLUMN_PLACE)
        val descriptionIndex = allData.getColumnIndex(COLUMN_DESCRIPTION)
        val numberIndex = allData.getColumnIndex(COLUMN_NUMBER)
        val placeIndex = allData.getColumnIndex(COLUMN_PLACE)
        val nameIndex = allData.getColumnIndex(COLUMN_NAME)
        val storeIdIndex = allData.getColumnIndex(COLUMN_STORE_ID)

        while (allData.moveToNext()) {
            val currentNumber = NumberDTO(allData.getString(descriptionIndex), allData.getString(numberIndex),
                    allData.getString(placeIndex), allData.getString(nameIndex), allData.getString(storeIdIndex))
            if (!viewModel.citiesNames.contains(currentNumber.place)) {
                viewModel.citiesNames.add(currentNumber.place)
                data.add(CityDTO(numbers = arrayListOf(currentNumber), name = currentNumber.place))
            } else data.first { it.name == currentNumber.place }.numbers.add(currentNumber)
        }
        allData.close()
        dbManager.writableDatabase.close()
        data.sortBy { it.name }
        data.forEach { itCity -> itCity.numbers.sortBy { itNumber -> itNumber.number } }
        return data
    }

    override fun onPostExecute(result: ArrayList<CityDTO>) {
        super.onPostExecute(result)
        viewModel.data = result
        listener.onGetUsers()
    }
}
