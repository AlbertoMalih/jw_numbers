package com.example.jw_numbers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jw_numbers.viewmodel.NumbersViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), OnGetUsersListener{
    @Inject lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        App.INSTANCE.appComponent().inject(this)
        viewModel.installAllUsers(this)
    }

    override fun onGetUsers() {
        startActivity(Intent(this, CitiesActivity::class.java))
    }
}

interface OnGetUsersListener {
    fun onGetUsers()
}