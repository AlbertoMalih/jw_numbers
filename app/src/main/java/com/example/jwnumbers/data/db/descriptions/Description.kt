package com.example.jwnumbers.data.db.descriptions

import android.arch.persistence.room.Entity
import com.example.jwnumbers.data.db.DESCRIPTION_TABLE_DB_NAME

@Entity(tableName = DESCRIPTION_TABLE_DB_NAME, primaryKeys = ["numberId", "storeId"])
open class Description(var description: String = "", var numberPlace: String = "",
                       var numberId: String = "", var storeId: String = "")