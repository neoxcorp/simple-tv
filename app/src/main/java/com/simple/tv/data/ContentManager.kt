package com.simple.tv.data

import android.content.Context
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.simple.tv.BuildConfig
import com.simple.tv.data.dto.Response

class ContentManager(val context: Context) {

    private val TAG = "ContentManager"

    init {
        Ion.getDefault(context).configure().setLogging(TAG, Log.DEBUG)
    }

    fun getListChannels(
        success: (response: Response) -> Unit,
        error: (exception: Exception) -> Unit
    ) {
        Log.e(TAG, "getContent")

        Ion.with(context)
            .load(BuildConfig.API_URL)
            .`as`(object : TypeToken<Response>() {})
            .setCallback({ e, result ->
                Log.i(TAG, "result: $result")

                result?.let {
                    success.invoke(it)
                } ?: run {
                    error.invoke(Exception("Empty list"))
                }

                Log.e(TAG, "exception: $e")

                e?.let {
                    error.invoke(it)
                }
            })
    }
}
