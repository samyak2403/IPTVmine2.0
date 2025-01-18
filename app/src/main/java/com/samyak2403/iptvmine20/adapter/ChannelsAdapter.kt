/*
 * Created by Samyak Kamble on 8/14/24, 12:18 PM
 *  Copyright (c) 2024. All rights reserved.
 *  Last modified 8/14/24, 12:18 PM
 */

package com.samyak2403.iptvmine20.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samyak2403.iptvmine20.R

import com.samyak2403.iptvmine20.model.Channel

class ChannelsAdapter(
    private var channels: List<Channel>,
    private val onChannelClicked: (Channel) -> Unit
) : RecyclerView.Adapter<ChannelsAdapter.ChannelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(channels[position])
    }

    override fun getItemCount(): Int = channels.size

    fun updateChannels(newChannels: List<Channel>) {
        val diffResult = DiffUtil.calculateDiff(ChannelDiffCallback(channels, newChannels))
        channels = newChannels
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ChannelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logoImageView: ImageView = itemView.findViewById(R.id.logoImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(channel: Channel) {
            nameTextView.text = channel.name
            Glide.with(itemView.context)
                .load(channel.logoUrl)
                .placeholder(R.drawable.iptv_mine)
                .into(logoImageView)

            itemView.setOnClickListener {
                onChannelClicked(channel)
            }
        }
    }

    private class ChannelDiffCallback(
        private val oldList: List<Channel>,
        private val newList: List<Channel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Assuming the name is unique for each channel
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Check if contents are the same, including the logoUrl and streamUrl
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
