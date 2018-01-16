package com.example.jw_numbers

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jw_numbers.viewmodel.NumbersViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), OnGetUsersListener {
    @Inject lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        App.INSTANCE.appComponent().inject(this)
        getterStoreId.setText(getPreferences(Context.MODE_PRIVATE).getString("StoreId", ""))
        if (getPreferences(Context.MODE_PRIVATE).getString("StoreId", "").isNotEmpty())
            viewModel.installAllUsers(this, getPreferences(Context.MODE_PRIVATE).getString("StoreId", ""))
        else
            startGetDate.setOnClickListener({
            val text = getterStoreId.text.toString().trim()
            if (text.isEmpty()) return@setOnClickListener
            getPreferences(Context.MODE_PRIVATE).edit().putString("StoreId", text).apply()
            viewModel.installAllUsers(this, text)
        })
    }

    override fun onGetUsers() {
        startActivity(Intent(this, CitiesActivity::class.java))
    }
}

interface OnGetUsersListener {
    fun onGetUsers()
}