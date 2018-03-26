package com.example.jwnumbers

import android.support.multidex.MultiDexApplication
import com.example.jwnumbers.di.jw_numbersModules
import io.realm.Realm
import org.koin.android.ext.android.startKoin


class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin(this, jw_numbersModules)
    }
}