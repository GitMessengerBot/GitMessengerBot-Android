package com.sungbin.gitkakaobot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.LayoutBotBinding
import com.sungbin.gitkakaobot.model.BotData


/**
 * Created by SungBin on 2020-08-23.
 */

class BotAdapter constructor(
    private val items: List<BotData>
) : RecyclerView.Adapter<BotAdapter.ViewHolder>() {

    class ViewHolder(private val itemBinding: LayoutBotBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindViewHolder(bot: BotData) {
            with(itemBinding) {
                tvName.isSelected = true
                item = bot
                invalidateAll()
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_bot, viewGroup, false
            )
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}