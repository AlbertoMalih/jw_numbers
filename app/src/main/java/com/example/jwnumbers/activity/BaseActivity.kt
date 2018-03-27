package com.example.jwnumbers.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jwnumbers.viewmodel.BaseViewModel

abstract class BaseActivity<View> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<View>
    abstract var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.attachView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.detachView()
    }
}