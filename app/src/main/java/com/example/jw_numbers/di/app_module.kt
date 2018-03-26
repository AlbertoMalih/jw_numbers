package com.yanevskyy.y.bythewayanalitics.di

import android.preference.PreferenceManager
import com.example.jw_numbers.model.CitiesContainer
import com.example.jw_numbers.services.DbManager
import com.example.jw_numbers.services.NumberRepository
import com.example.jw_numbers.viewmodel.CitiesViewModel
import com.example.jw_numbers.viewmodel.HomesViewModel
import com.example.jw_numbers.viewmodel.SplashViewModel
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