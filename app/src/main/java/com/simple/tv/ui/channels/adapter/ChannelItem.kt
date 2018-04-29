package com.simple.tv.ui.channels.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.simple.tv.R
import kotlinx.android.synthetic.main.item_channel.view.*
import java.io.Serializable

class ChannelItem(
    val name: String,
    val sourceUrl: String,
    val imageSource: String? = null
) : AbstractItem<ChannelItem, ChannelItem.ViewHolder>(),
    Serializable {

    override fun getType() = R.id.rootCardView

    override fun getViewHolder(v: View) = ViewHolder(v)

    override fun getLayoutRes() = R.layout.item_channel

    class ViewHolder(val view: View) : FastAdapter.ViewHolder<ChannelItem>(view) {

        override fun unbindView(item: ChannelItem) {
            view.nameAppCompatTextView.text = ""

            Glide.with(view.iconAppCompatImageView)
                .clear(view.iconAppCompatImageView)
        }

        override fun bindView(item: ChannelItem, payloads: MutableList<Any>?) {
            view.nameAppCompatTextView.text = item.name

            item.imageSource?.let {
                Glide.with(view.iconAppCompatImageView)
                    .load(it)
                    .into(view.iconAppCompatImageView)
            }
        }

    }
}
