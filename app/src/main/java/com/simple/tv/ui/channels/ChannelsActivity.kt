package com.simple.tv.ui.channels

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.simple.tv.ui.channels.types.Data
import org.jetbrains.anko.*

class ChannelsActivity : AppCompatActivity() {

    private val TAG = "ChannelsActivity"

    companion object {
        private val DATA_CHANNELS = "DATA_CHANNELS"

        fun newInstance(context: Context, data: Data): Intent {
            return Intent(context, ChannelsActivity::class.java)
                .apply {
                    this.putExtra(DATA_CHANNELS, data)
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

        val data = intent.extras[DATA_CHANNELS] as Data

        Log.i(TAG, "handleIntent -> data.channels.size: ${data.channels.size}")
    }

    private fun buildLayout() {
        Log.i(TAG, "buildLayout")

        frameLayout {
            padding = dip(16)
            /*recyclerView {

            }*/
        }

        /*verticalLayout {
            padding = dip(30)
            editText {
                hint = "Name"
                textSize = 24f
            }
            editText {
                hint = "Password"
                textSize = 24f
            }
            button("Login") {
                textSize = 26f
            }
        }*/
    }
}
