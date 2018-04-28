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

    fun getFirstChannel(success: (sourceUrl: String) -> Unit, error: (exception: Exception) -> Unit) {
        Log.e(TAG, "getContent")

        Ion.with(context)
            .load(BuildConfig.API_URL)
            .asJsonObject()
            .setCallback({ e, result ->
                Log.i(TAG, "result: $result")

                result?.let {
                    if (result.isJsonNull) {
                        return@let
                    }

                    success.invoke(it["tv_list"].asJsonArray[0].asJsonObject["stream_url"].asString)
                }

                Log.e(TAG, "exception: $e")

                e?.let {
                    error.invoke(it)
                }
            })
    }

    fun getListChannels(success: (channelList: List<Channel>) -> Unit, error: (exception: Exception) -> Unit) {
        Log.e(TAG, "getContent")

        Ion.with(context)
            .load(BuildConfig.API_URL)
            .`as`(object : TypeToken<Response>() {})
            .setCallback({ e, result ->
                Log.i(TAG, "result: $result")

                result?.let {
                    success.invoke(it.tv_list)
                }

                Log.e(TAG, "exception: $e")

                e?.let {
                    error.invoke(it)
                }
            })
    }
}
