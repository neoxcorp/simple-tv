package com.simple.tv.ui

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import kotlinx.android.synthetic.main.activity_splash.*
import com.simple.tv.R
import com.simple.tv.data.ContentManager
import com.simple.tv.data.dto.Response
import com.simple.tv.ui.channels.ChannelsActivity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        setupTryAgain()
    }

    override fun onStart() {
        super.onStart()

        getContent()
    }

    private fun setupTryAgain() {
        Log.i(TAG, "setupTryAgain")

        tryAgainAppCompatButton.setOnClickListener {
            getContent()

            progressBar.visibility = VISIBLE
            tryAgainAppCompatButton.visibility = INVISIBLE
            infoMessageAppCompatTextView.text = getString(R.string.loading)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getContent() {
        Log.i(TAG, "getContent")

        ContentManager(this).getListChannels({
            if (it.tv_list.isNotEmpty()) {
                openChannels(it)
            } else {
                progressBar.visibility = INVISIBLE
                tryAgainAppCompatButton.visibility = VISIBLE
                infoMessageAppCompatTextView.text = "Something went wrong"
            }
        },
            {
                progressBar.visibility = INVISIBLE
                tryAgainAppCompatButton.visibility = VISIBLE
                infoMessageAppCompatTextView.text = "Something went wrong"
                it.printStackTrace()
            })
    }

    private fun openChannels(dataChannels: Response) {
        Log.i(TAG, "openChannels -> channels: ${dataChannels.tv_list.size}")

        launch(UI) {
            delay(200)
            startActivity(ChannelsActivity.newInstance(this@SplashActivity, dataChannels))
        }
    }
}
