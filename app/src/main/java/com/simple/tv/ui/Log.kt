package com.simple.tv.ui

import com.simple.tv.BuildConfig

object Log {

    private const val TAG = "LOG_TAG"

    fun isShowLog() = BuildConfig.DEBUG

    fun i() {
    }
}
