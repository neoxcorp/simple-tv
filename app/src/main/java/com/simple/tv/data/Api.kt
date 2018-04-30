package com.simple.tv.data

import com.simple.tv.data.dto.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("{json}")
    fun getChannels(@Path("json") json: String): Call<Response>

    @GET("{id}")
    fun getChannelsTwo(@Path("id") id: String): Call<Response>
}
