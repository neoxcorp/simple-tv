package com.simple.tv.ui.channels

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.simple.tv.ui.PlayerActivity
import com.simple.tv.ui.channels.adapter.ChannelItem
import com.simple.tv.ui.channels.types.Data
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ChannelsActivity : AppCompatActivity() {

    private val TAG = "ChannelsActivity"

    private lateinit var fastAdapter: FastAdapter<ChannelItem>
    private val itemAdapter: ItemAdapter<ChannelItem> = ItemAdapter()

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

        itemAdapter.set(data.channels)
    }

    private fun buildLayout() {
        Log.i(TAG, "buildLayout")

        fastAdapter = FastAdapter.with(mutableListOf(itemAdapter))

        fastAdapter.withOnClickListener { _, _, item, _ ->
            openPlayer(item.sourceUrl)
            true
        }

        frameLayout {
            padding = dip(16)
            recyclerView {
                layoutManager = GridLayoutManager(this@ChannelsActivity, 4)
                adapter = fastAdapter
            }
        }
    }

    private fun openPlayer(sourceUrl: String) {
        Log.i(TAG, "openPlayer -> sourceUrl: $sourceUrl")

        startActivity(PlayerActivity.newInstance(this, sourceUrl))
    }
}
