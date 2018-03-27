package com.example.jwnumbers.model

import io.realm.RealmModel
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class NumberDTO(@PrimaryKey var id: String = "", var description: String = "", var number: String = "",
                     var place: String = "", @Ignore var name: String = "") : RealmModel