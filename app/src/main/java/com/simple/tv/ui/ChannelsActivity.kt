package com.simple.tv.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.simple.tv.data.dto.Response

class ChannelsActivity : AppCompatActivity() {

    private val TAG = "ChannelsActivity"

    companion object {
        private val DATA_CHANNELS = "DATA_CHANNELS"

        fun newInstance(context: Context, dataChannels: Response): Intent {
            return Intent(context, ChannelsActivity::class.java)
                .apply {
                    this.putExtra(DATA_CHANNELS, dataChannels)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buildLayout()
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        Log.i(TAG, "handleIntent -> intent: $intent")

        val dataChannels = intent.extras[DATA_CHANNELS] as Response

        Log.i(TAG, "handleIntent -> dataChannels.tv_list.size: ${dataChannels.tv_list.size}")
    }

    private fun buildLayout() {
        Log.i(TAG, "buildLayout")
    }
}
