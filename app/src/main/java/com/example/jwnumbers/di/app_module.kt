package com.example.jwnumbers.di

import android.preference.PreferenceManager
import com.example.jwnumbers.data.db.AppDbHelper
import com.example.jwnumbers.data.db.DbHelper
import com.example.jwnumbers.data.interactor.*
import com.example.jwnumbers.data.preferences.AppPreferencesHelper
import com.example.jwnumbers.data.preferences.PreferencesHelper
import com.example.jwnumbers.data.repository.AppNumbersRepositoryHelper
import com.example.jwnumbers.data.repository.NumbersRepositoryHelper
import com.example.jwnumbers.model.CitiesContainer
import com.example.jwnumbers.viewmodel.CitiesViewModel
import com.example.jwnumbers.viewmodel.HomesViewModel
import com.example.jwnumbers.viewmodel.SplashViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val usersModule = applicationContext {
    bean { CitiesContainer() }
    bean { AppDbHelper(get()) as DbHelper }
    bean { AppNumbersRepositoryHelper(get()) as NumbersRepositoryHelper }
    bean { AppPreferencesHelper(get()) as PreferencesHelper }
    bean { SplashAppInteractor(get(), get(), get(), get()) as SplashInteractor }
    bean { CitiesAppInteractor(get()) as CitiesInteractor }
    bean { HomesAppInteractor(get()) as HomesInteractor }
    bean { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val viewModelModule = applicationContext {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { CitiesViewModel(get(), get()) }
    viewModel { HomesViewModel(get(), get()) }
}

val jw_numbersModules = listOf(usersModule, viewModelModule)