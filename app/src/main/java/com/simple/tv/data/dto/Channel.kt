package com.simple.tv.data.dto

import java.io.Serializable

data class Channel(val name: String, val stream_url: String, val image_url: String) : Serializable
