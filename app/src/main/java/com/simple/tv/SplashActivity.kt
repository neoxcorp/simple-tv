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

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        playStreamVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
    }

    fun playStreamVideo(url: String) {
        val dataSourceFactory
                = DefaultDataSourceFactory(this,
                                           Util.getUserAgent(this,
                                                             "mediaPlayerSample"))

        val videoTrackSelectionFactory
                = AdaptiveTrackSelection.Factory(BandwidthMeter { 5000 })

        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        val player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player
        player.playWhenReady = true

        val extractorsFactory = DefaultExtractorsFactory()

        val mediaSource =
                ExtractorMediaSource(Uri.parse(url),
                                     dataSourceFactory, extractorsFactory, null, null)
        player.prepare(mediaSource)
    }
}
