package com.example.jwnumbers.activity

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.jwnumbers.R
import com.example.jwnumbers.services.OnReceivedNumbers
import com.example.jwnumbers.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.android.ext.android.inject


class SplashActivity : BaseActivity<SplashActivityView>(), SplashActivityView {
    override val viewModel: SplashViewModel by inject()
    override var view: SplashActivityView = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.manageConnect()
    }

    override fun isWillAutoConnect(isWillAutoConnect: Boolean, storeId: String) {
        getterStoreId.setText(storeId)
        if (isWillAutoConnect)
            prepareInstallingNumbers(storeId)
        else
            startGetDate.setOnClickListener {
                getterStoreId.text.toString().trim().let { writtenStoreId -> prepareInstallingNumbers(writtenStoreId) }
            }
    }

    private fun prepareInstallingNumbers(storeId: String) {
        if (storeId.isEmpty()) return
        startLoading()
        startInstallingNumbers(storeId)
    }

    private fun startInstallingNumbers(storeId: String) {
        viewModel.installAllNumbers(object : OnReceivedNumbers() {
            override fun onFailReceived() {
                stopLoading()
                Toast(this@SplashActivity).setText("не удалось загрузить номера")
            }

            override fun onSuchReceived() {
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