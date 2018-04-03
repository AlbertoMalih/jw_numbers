package com.example.jwnumbers

import android.support.multidex.MultiDexApplication
import com.example.jwnumbers.di.jw_numbersModules
import org.koin.android.ext.android.startKoin


class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, jw_numbersModules)
    }
}