package com.simple.tv.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class PlayerActivity : AppCompatActivity() {

    private val TAG = "PlayerActivity"

    companion object {
        private val SOURCE_URL = "SOURCE_URL"

        fun newInstance(context: Context, sourceUrl: String): Intent {
            return Intent(context, PlayerActivity::class.java)
                .apply {
                    this.putExtra(SOURCE_URL, sourceUrl)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras.getString(SOURCE_URL, null)?.let {
            playStreamVideo(it)
        }
    }



    private fun playStreamVideo(url: String) {
        Log.e(TAG, "getContent -> url: $url")

        /*val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(BandwidthMeter { 5000 })

        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        val player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player
        playerView.useController = false
        player.playWhenReady = true

        val bandwidthMeter = DefaultBandwidthMeter()

        val dataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(
                this,
                "mediaPlayerSample"
            ),
            bandwidthMeter
        )

        val hlsMediaSource =
            HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))

        player.prepare(hlsMediaSource)

        playerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE*/
    }
}
