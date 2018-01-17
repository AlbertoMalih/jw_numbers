package com.example.jw_numbers

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.jw_numbers.viewmodel.NumbersViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), OnGetUsersListener {
    @Inject lateinit var viewModel: NumbersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        App.INSTANCE.appComponent().inject(this)
        getterStoreId.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("StoreId", ""))
    }

    override fun onResume() {
        super.onResume()
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isExit", false)) {
            startLoading()
            viewModel.installAllUsers(this, PreferenceManager.getDefaultSharedPreferences(this).getString("StoreId", ""))
        } else
            startGetDate.setOnClickListener({
                startLoading()
                val text = getterStoreId.text.toString().trim()
                if (text.isEmpty()) return@setOnClickListener
                viewModel.installAllUsers(this, text)
            })
    }

    fun stopLoading() {
        startGetDate.isClickable = true
        progressBar.visibility = View.GONE
    }

    private fun startLoading() {
        progressBar.visibility = View.VISIBLE
        startGetDate.isClickable = false
    }

    override fun onGetUsers() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isExit", true).apply()
        stopLoading()
        startActivity(Intent(this, CitiesActivity::class.java))
    }
}

interface OnGetUsersListener {
    fun onGetUsers()
}