package com.simple.tv.data

import android.content.Context
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.simple.tv.BuildConfig
import com.simple.tv.data.dto.Channel
import com.simple.tv.data.dto.Response

class ContentManager(val context: Context) {

    private val TAG = "ContentManager"

    init {
        Ion.getDefault(context).configure().setLogging(TAG, Log.DEBUG)
    }

    fun getListChannels(
        success: (channelList: List<Channel>) -> Unit,
        error: (exception: Exception) -> Unit
    ) {
        Log.e(TAG, "getContent")

        Ion.with(context)
            .load(BuildConfig.API_URL)
            .`as`(object : TypeToken<Response>() {})
            .setCallback({ e, result ->
                Log.i(TAG, "result: $result")

                result?.let {
                    if (it.tv_list.isNotEmpty()) {
                        success.invoke(it.tv_list)
                    } else {
                        error.invoke(Exception("Server Error"))
                    }
                }.run {
                    error.invoke(Exception("Server Error"))
                }

                Log.e(TAG, "exception: $e")

                e?.let {
                    error.invoke(it)
                }
            })
    }
}
