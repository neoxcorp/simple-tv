package com.simple.tv.data

import android.content.Context
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.simple.tv.BuildConfig
import com.simple.tv.data.dto.Channel
import com.simple.tv.data.dto.Response
import com.simple.tv.ui.channels.adapter.ChannelItem
import com.simple.tv.ui.channels.types.Data
import org.jetbrains.anko.toast

class ContentManager(val context: Context) {

    private val TAG = "ContentManager"

    init {
        Ion.getDefault(context).configure().setLogging(TAG, Log.DEBUG)
    }

    fun getListChannels(
        success: (channels: Data) -> Unit,
        error: (exception: Exception) -> Unit
    ) {
        Log.e(TAG, "getContent")

        /*Ion.with(context)
            .load(BuildConfig.API_URL)
            .`as`(object : TypeToken<Response>() {})
            .setCallback({ e, result ->
                Log.i(TAG, "result: $result")
                context.toast("result: $result")

                result?.let {
                    success.invoke(convertToData(it))
                } ?: run {
                    context.toast("Empty list")
                    Crashlytics.logException(Exception("Empty list"))
                    Crashlytics.log("Empty list")
                    error.invoke(Exception("Empty list"))
                }

                Log.e(TAG, "exception: $e")
                context.toast("exception: $e")

                e?.let {
                    Crashlytics.logException(it)
                    Crashlytics.log(it.message)
                    error.invoke(it)
                }
            })*/

        Ion.with(context)
            .load(BuildConfig.API_URL)
            .asJsonObject()
            .setCallback({ e, result ->
                Log.i(TAG, "result: $result")
                context.toast("result: $result")

                result?.let {
                    success.invoke(convertToData(parseResponse(result)))
                } ?: run {
                    context.toast("Empty list")
                    Crashlytics.logException(Exception("Empty list"))
                    Crashlytics.log("Empty list")
                    error.invoke(Exception("Empty list"))
                }

                Log.e(TAG, "exception: $e")
                context.toast("exception: $e")

                e?.let {
                    Crashlytics.logException(it)
                    Crashlytics.log(it.message)
                    error.invoke(it)
                }
            })
    }

    private fun parseResponse(json: JsonObject): Response {
        Log.e(TAG, "parseResponse -> json: $json")
        context.toast("parseResponse -> json: $json")

        val channels = arrayListOf<Channel>()

        val  tv_list = json["tv_list"].asJsonArray

        tv_list?.let {
            it.forEach {
                val o = it.asJsonObject
                channels.add(
                    Channel(o["name"].asString,
                        o["stream_url"].asString,
                        o["image_url"].asString))
            }

        }

        return Response(channels)
    }

    private fun convertToData(response: Response): Data {
        Log.e(TAG, "convertToChannelItem -> response: $response")

        val channels = arrayListOf<ChannelItem>()

        response.tv_list.forEach {
            channels.add(ChannelItem(it.name, it.stream_url, it.image_url))
        }

        return Data(channels)
    }
}
