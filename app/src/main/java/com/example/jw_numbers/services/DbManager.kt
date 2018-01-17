package com.example.jw_numbers.services

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import com.example.jw_numbers.OnGetUsersListener
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.viewmodel.NumbersViewModel
import java.util.*

val TABLE_NAME = "numbers"
private val COLUMN_ID = "_id"

private val COLUMN_DESCRIPTION = "description"
private val COLUMN_NUMBER = "number"
private val COLUMN_PLACE = "place"

class DbManager(context: Context) : SQLiteOpenHelper(context, "NumbersDb.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                "create table " + TABLE_NAME + " " +
                        "(" + COLUMN_ID + " integer primary key, " + COLUMN_DESCRIPTION +
                        " text, " + COLUMN_PLACE + " text," + COLUMN_NUMBER + " text" + " )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }

    fun deleteAllNotes() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun insertAllNumbers(numbers: List<NumberDTO>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            for (number in numbers) {
                val contentValues = ContentValues()
                contentValues.put(COLUMN_PLACE, number.place)
                contentValues.put(COLUMN_NUMBER, number.number)

                if (1 > db.update(TABLE_NAME, contentValues, COLUMN_NUMBER + " = ?", arrayOf(number.number))) {
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
        db.update(TABLE_NAME, contentValues, COLUMN_NUMBER + " = ?", arrayOf(number.number))
        db.close()
    }

    fun installAllNotesInListener(viewModel: NumbersViewModel, listener: OnGetUsersListener) {
        RequestAllNumbers(viewModel, listener, writableDatabase).execute()
    }
}

private class RequestAllNumbers(val viewModel: NumbersViewModel, val listener: OnGetUsersListener, val writableDatabase: SQLiteDatabase) :
        AsyncTask<Void, Void, MutableMap<String, MutableMap<String, ArrayList<NumberDTO>>>>() {

    override fun doInBackground(vararg p0: Void?): MutableMap<String, MutableMap<String, ArrayList<NumberDTO>>> {
        val data = HashMap<String, MutableMap<String, ArrayList<NumberDTO>>>()
        val allData = writableDatabase.query(TABLE_NAME, null, null, null, null, null, COLUMN_PLACE)

        val descriptionIndex = allData.getColumnIndex(COLUMN_DESCRIPTION)
        val numberIndex = allData.getColumnIndex(COLUMN_NUMBER)
        val placeIndex = allData.getColumnIndex(COLUMN_PLACE)

        while (allData.moveToNext()) {
            val currentNumber = NumberDTO(allData.getString(descriptionIndex), allData.getString(numberIndex), allData.getString(placeIndex))
            val places = currentNumber.place.split("//")
            if (!data.containsKey(places[0])) {
                data.put(places[0], HashMap())
            }
            if (!data[places[0]]!!.containsKey(places[1])) {
                data[places[0]]!!.put(places[1], ArrayList())
            }
            data[places[0]]!![places[1]]!!.add(currentNumber)
        }
        allData.close()
        return data
    }

    override fun onPostExecute(result: MutableMap<String, MutableMap<String, ArrayList<NumberDTO>>>) {
        super.onPostExecute(result)
        writableDatabase.close()
        viewModel.data = result
        listener.onGetUsers()
    }
}
