package com.simple.tv.ui

import android.util.Log
import com.simple.tv.BuildConfig

object Log {

    private const val TAG = "DEBUG_LOG_TAG"

    fun isShowLog() = BuildConfig.DEBUG

    fun i(message: String) {
        if (isShowLog()) {
            Log.i(TAG, message)
        }
    }

    fun d(message: String) {
        if (isShowLog()) {
            Log.d(TAG, message)
        }
    }

    fun e(message: String) {
        if (isShowLog()) {
            Log.e(TAG, message)
        }
    }
}
