package com.example.jwnumbers.viewmodel

import android.arch.lifecycle.ViewModel
import com.example.jwnumbers.model.CitiesContainer


open class BaseViewModel<ViewType>(val citiesContainer: CitiesContainer) : ViewModel() {
    open var view: ViewType? = null

    fun attachView(attachingView: ViewType) {
        view = attachingView
    }

    fun detachView() {
        view = null
    }
}