package com.example.jwnumbers.di

import android.preference.PreferenceManager
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.services.DbManager
import com.example.jwnumbers.services.NumberRepository
import com.example.jwnumbers.viewmodel.CitiesViewModel
import com.example.jwnumbers.viewmodel.HomesViewModel
import com.example.jwnumbers.viewmodel.SplashViewModel
import io.realm.Realm
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val usersModule = applicationContext {
    bean { CitiesContainer() }
    bean { DbManager(get()) }
    bean { NumberRepository(get()) }
    bean { Realm.getDefaultInstance() }
    bean { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val viewModelModule = applicationContext {
    viewModel { SplashViewModel(get(), get(), get(), get(), get()) }
    viewModel { CitiesViewModel(get(), get()) }
    viewModel { HomesViewModel(get(), get()) }
}

val jw_numbersModules = listOf(usersModule, viewModelModule)