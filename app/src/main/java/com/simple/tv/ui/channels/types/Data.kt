package com.simple.tv.ui.channels.types

import com.simple.tv.ui.channels.adapter.ChannelItem
import java.io.Serializable

data class Data(val channels: List<ChannelItem>) : Serializable
