package com.simple.tv

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.upstream.BandwidthMeter
import kotlinx.android.synthetic.main.activity_splash.*
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.simple.tv.data.ContentManager

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        getContent()
    }

    fun getContent() {
        Log.e(TAG, "getContent")

        /*ContentManager(this).getFirstChannel({
            playStreamVideo(it)
        },
            {
                progressBar.visibility = GONE
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            })*/

        ContentManager(this).getListChannels({
            playStreamVideo(it[0].stream_url)
        },
            {
                progressBar.visibility = GONE
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                it.printStackTrace()
            })
    }

    fun playStreamVideo(url: String) {
        Log.e(TAG, "getContent -> url: $url")

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(BandwidthMeter { 5000 })

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

        playerView.visibility = VISIBLE
        progressBar.visibility = GONE
    }
}
