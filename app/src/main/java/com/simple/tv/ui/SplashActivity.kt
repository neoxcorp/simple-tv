package com.simple.tv.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import kotlinx.android.synthetic.main.activity_splash.*
import com.simple.tv.R
import com.simple.tv.data.ContentManager
import com.simple.tv.data.dto.Channel
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

    private fun getContent() {
        Log.i(TAG, "getContent")

        ContentManager(this).getListChannels({
            openChannels(it)
        },
            {
                progressBar.visibility = INVISIBLE
                tryAgainAppCompatButton.visibility = VISIBLE
                infoMessageAppCompatTextView.text = it.message
                it.printStackTrace()
            })
    }

    private fun openChannels(channels: List<Channel>) {
        Log.i(TAG, "openChannels -> channels: ${channels.size}")

        launch(UI) {
            delay(200)
            // startActivity(ChannelsActivity.)
        }
    }
}
