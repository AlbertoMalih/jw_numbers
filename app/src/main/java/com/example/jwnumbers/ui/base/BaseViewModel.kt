package com.example.jwnumbers.ui.base

import android.arch.lifecycle.ViewModel
import com.example.jwnumbers.model.CitiesContainer


open class BaseViewModel<Navigator: Any>(val citiesContainer: CitiesContainer) : ViewModel() {
    lateinit var navigator: Navigator
}