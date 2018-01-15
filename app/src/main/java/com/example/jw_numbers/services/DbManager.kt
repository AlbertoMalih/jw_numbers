package com.example.jw_numbers.services


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.util.Log
import com.example.jw_numbers.OnGetUsersListener
import com.example.jw_numbers.model.NumberDTO
import com.example.jw_numbers.viewmodel.NumbersViewModel
import java.sql.SQLException
import java.util.*
import android.database.DatabaseUtils


val TABLE_NAME = "numbers"
private val COLUMN_ID = "_id"

private val COLUMN_DESCRIPTION = "description"
private val COLUMN_NUMBER = "number"
private val COLUMN_PLACE = "place"
private val COLUMN_FIREBASE_ID = "firebaseId"

class DbManager(context: Context) : SQLiteOpenHelper(context, "NumbersDb.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                "create table " + TABLE_NAME + " " +
                        "(" + COLUMN_ID + " integer primary key, " + COLUMN_DESCRIPTION +
                        " text, " + COLUMN_PLACE + " text," + COLUMN_NUMBER + " text, " +
                        COLUMN_FIREBASE_ID + " text )"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }

    fun insertAllNumbers(numbers: List<NumberDTO>) {
        val db = this.writableDatabase
        for (number in numbers) {
            Log.d("tag", "insertAllNumbers, number: " + number)
            val contentValues = ContentValues()
            contentValues.put(COLUMN_PLACE, number.place)
            contentValues.put(COLUMN_NUMBER, number.number)
//            db.insert(TABLE_NAME, null, contentValues)

            if (1 > db.update(TABLE_NAME, contentValues, COLUMN_FIREBASE_ID + " = ?", arrayOf(number.firebaseId))) {
                contentValues.put(COLUMN_FIREBASE_ID, number.firebaseId)
                contentValues.put(COLUMN_DESCRIPTION, number.description)
                val tt = db.insert(TABLE_NAME, null, contentValues)
                Log.d("tag", "count insert: " + tt)
            }
        }
        Log.d("tag", "insertAllNumbers, db: " + DatabaseUtils.queryNumEntries(db, TABLE_NAME))
        db.close()
    }

    fun setDescriptionOfNumber(number: NumberDTO) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_DESCRIPTION, number.description)
        db.update(TABLE_NAME, contentValues, COLUMN_FIREBASE_ID + " = ?", arrayOf(number.firebaseId))
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
        val idIndex = allData.getColumnIndex(COLUMN_FIREBASE_ID)

        while (allData.moveToNext()) {
            val currentNumber = NumberDTO(allData.getString(descriptionIndex), allData.getString(numberIndex), allData.getString(placeIndex), allData.getString(idIndex))
            val places = currentNumber.place.split("//")
            Log.d("tag", "currentNumber, place: " + currentNumber.place)
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
        Log.d("tag", "viewModel.data: " + viewModel.data)
        listener.onGetUsers()

    }
}
