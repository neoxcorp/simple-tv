package com.simple.tv.data

import com.simple.tv.data.dto.Response
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("things/orG5.json")
    fun getChannels(): Call<Response>
}
