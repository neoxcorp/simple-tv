package com.simple.tv.ui

import com.simple.tv.BuildConfig

object Log {

    private const val TAG = "DEBUG_LOG_TAG"

    fun isShowLog() = BuildConfig.DEBUG

    fun i(message: String) {
    }

    fun d(message: String) {
    }

    fun e(message: String) {
    }
}
