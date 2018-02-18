package com.example.jw_numbers.dagger

import com.example.jw_numbers.CitiesActivity
import com.example.jw_numbers.SplashActivity
import com.example.jw_numbers.CurrentCityActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class))

interface AppComponent {
    fun inject(activity: SplashActivity)
    fun inject(activity: CurrentCityActivity)
    fun inject(activity: CitiesActivity)
}