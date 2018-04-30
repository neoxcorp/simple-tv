package com.simple.tv.data

import android.content.Context
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.simple.tv.BuildConfig
import com.simple.tv.data.dto.Channel
import com.simple.tv.data.dto.Response
import com.simple.tv.ui.channels.adapter.ChannelItem
import com.simple.tv.ui.channels.types.Data
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ContentManager(val context: Context) {

    private val TAG = "ContentManager"

    init {
        Ion.getDefault(context).configure().setLogging(TAG, Log.DEBUG)
    }

    fun getListChannels(
        success: (channels: Data) -> Unit,
        error: (exception: Throwable) -> Unit
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

        /*Ion.with(context)
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
            })*/

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()


        val api = retrofit.create(Api::class.java)

        val call = api.getChannels()
        call.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, e: Throwable?) {
                Log.e(TAG, "exception: $e")
                context.toast("exception: $e")

                e?.let {
                    Crashlytics.logException(it)
                    Crashlytics.log(it.message)
                    error.invoke(it)
                }
            }

            override fun onResponse(
                call: Call<Response>?,
                response: retrofit2.Response<Response>?
            ) {
                Log.i(TAG, "call: $call")
                Log.i(TAG, "response: $response")
                context.toast("response: $response")

                val resp = response?.body()

                resp?.let {
                    success.invoke(convertToData(resp))
                }
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
