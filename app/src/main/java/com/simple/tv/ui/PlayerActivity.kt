package com.simple.tv.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.simple.tv.R
import kotlinx.android.synthetic.main.activity_player.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource


class PlayerActivity : AppCompatActivity(), Player.EventListener {

    private val TAG = "PlayerActivity"

    private lateinit var player: SimpleExoPlayer

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
        setContentView(R.layout.activity_player)

        intent.extras.getString(SOURCE_URL, null)?.let {
            playStreamVideo(it)
        }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
        Log.e(TAG, "onPlaybackParametersChanged")
    }

    override fun onSeekProcessed() {
        Log.e(TAG, "onSeekProcessed")
    }

    override fun onTracksChanged(
            trackGroups: TrackGroupArray?,
            trackSelections: TrackSelectionArray?
    ) {
        Log.e(TAG, "onTracksChanged")
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        Log.e(TAG, "onPlayerError")

        error?.let {
            error.printStackTrace()
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
        Log.e(TAG, "onLoadingChanged -> isLoading: $isLoading")
    }

    override fun onPositionDiscontinuity(reason: Int) {
        Log.e(TAG, "onPositionDiscontinuity")
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        Log.e(TAG, "onRepeatModeChanged")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        Log.e(TAG, "onShuffleModeEnabledChanged")
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
        Log.e(TAG, "onTimelineChanged")
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Log.e(TAG, "onPlayerStateChanged")
    }

    private fun playStreamVideo(url: String) {
        Log.e(TAG, "playStreamVideo -> url: $url")

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(BandwidthMeter { 5000 })

        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player
        playerView.useController = false
        player.playWhenReady = true

        player.addListener(this)

        val bandwidthMeter = DefaultBandwidthMeter()

        val dataSourceFactory = DefaultDataSourceFactory(
                this,
                Util.getUserAgent(
                        this,
                        getString(R.string.app_name)
                ),
                bandwidthMeter
        )

        val hlsMediaSource =
                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))

        player.prepare(hlsMediaSource)
    }

    override fun onDestroy() {
        super.onDestroy()

        player.stop()
        player.release()
    }
}
