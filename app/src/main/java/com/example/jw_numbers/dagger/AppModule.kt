package com.example.jw_numbers.dagger

import android.app.Application
import android.content.Context
import com.example.jw_numbers.App
import com.example.jw_numbers.services.DbManager
import com.example.jw_numbers.viewmodel.NumbersViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: App) {

    @Provides
    @Singleton
    fun providerApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideDbManager(context: Context): DbManager = DbManager(context)

    @Provides
    @Singleton
    fun provideNumbersViewModel(dbManager: DbManager, context: Context): NumbersViewModel = NumbersViewModel(dbManager, context)


}