package com.example.jwnumbers.activity

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.jwnumbers.R
import com.example.jwnumbers.services.OnReceivedNumbers
import com.example.jwnumbers.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.architecture.ext.viewModel


class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getterStoreId.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("StoreId", ""))
    }

    override fun onResume() {
        super.onResume()
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isExit", false)) {
            startLoading()
            startInstallingUsers(PreferenceManager.getDefaultSharedPreferences(this).getString("StoreId", ""))
        } else
            startGetDate.setOnClickListener({
                startLoading()
                val text = getterStoreId.text.toString().trim()
                if (text.isEmpty()) return@setOnClickListener
                startInstallingUsers(text)
            })
    }

    private fun startInstallingUsers(storeId: String) {
        viewModel.installAllUsers(object : OnReceivedNumbers() {
            override fun onSuchReceived() {
                PreferenceManager.getDefaultSharedPreferences(this@SplashActivity).edit().putBoolean("isExit", true).apply()
                stopLoading()
                startActivity(Intent(this@SplashActivity, CitiesActivity::class.java)
                        .setFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK))
            }
        }, storeId)
    }

    private fun stopLoading() {
        startGetDate.isClickable = true
        progressBar.visibility = View.GONE
    }

    private fun startLoading() {
        progressBar.visibility = View.VISIBLE
        startGetDate.isClickable = false
    }
}

interface OnGetUsersListener {
    fun onSuchGetUsers()
    fun onFailGetUsers()
}