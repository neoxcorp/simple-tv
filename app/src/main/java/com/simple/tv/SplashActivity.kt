package com.simple.tv

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.upstream.BandwidthMeter
import kotlinx.android.synthetic.main.activity_splash.*
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        // val videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        val videoUrl = "http://playlist.onlybest.tv/ru/channel/d0f21a70397d3659fe259aa5e2a62107/70/index.m3u8"
        playStreamVideo(videoUrl)
    }

    fun playStreamVideo(url: String) {
        val videoTrackSelectionFactory
                = AdaptiveTrackSelection.Factory(BandwidthMeter { 5000 })

        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        val player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player
        player.playWhenReady = true

        val bandwidthMeter = DefaultBandwidthMeter()

        val dataSourceFactory
                = DefaultDataSourceFactory(this,
                                           Util.getUserAgent(this,
                                                             "mediaPlayerSample"),
                                           bandwidthMeter)

        val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url))

        player.prepare(videoSource)
    }
}
