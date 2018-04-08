package com.example.jwnumbers.ui.splash

import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.jwnumbers.R
import com.example.jwnumbers.ui.cities.CitiesActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.architecture.ext.viewModel


class SplashActivity : AppCompatActivity(), SplashNavigator {
    private val viewModel: SplashViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.navigator = this
        viewModel.manageConnect()
        viewModel.isDisableAutoConnected.observe(this, Observer { isDisabledAutoConnected ->
            isDisabledAutoConnected?.let { if (isDisabledAutoConnected) updateIndicatorOnStartLoading() else onDisabledAutoConnect() }
        })
        viewModel.resultLoadedNumbers.observe(this, Observer { result ->
            updateIndicatorOnStopLoading()
            if (result == FAIL_LOADED_NUMBERS) displayFailLoadedMessage()
        })
        viewModel.storeId.observe(this, Observer { storeId -> getterStoreId.setText(storeId) })
    }

    override fun openCitiesActivity() {
        startActivity(Intent(this@SplashActivity, CitiesActivity::class.java)
                .setFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK))
    }


    private fun displayFailLoadedMessage() {
        Toast.makeText(this@SplashActivity,
                this@SplashActivity.getString(R.string.not_complete_received_numbers), Toast.LENGTH_SHORT).show()
    }

    private fun onDisabledAutoConnect() {
        startGetDate.setOnClickListener { startLoading(getterStoreId.text.toString().trim()) }
    }

    private fun startLoading(writtenStoreId: String) {
        if (writtenStoreId.isEmpty()) return
        updateIndicatorOnStartLoading()
        viewModel.installAllNumbers(writtenStoreId)
    }

    private fun updateIndicatorOnStopLoading() {
        startGetDate.isClickable = true
        progressBar.visibility = View.GONE
    }

    private fun updateIndicatorOnStartLoading() {
        progressBar.visibility = View.VISIBLE
        startGetDate.isClickable = false
    }
}