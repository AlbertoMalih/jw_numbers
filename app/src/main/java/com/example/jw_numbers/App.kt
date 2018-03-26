package com.example.jw_numbers

import android.support.multidex.MultiDexApplication
import com.yanevskyy.y.bythewayanalitics.di.jw_numbersModules
import io.realm.Realm
import org.koin.android.ext.android.startKoin


class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin(this, jw_numbersModules)
    }
}