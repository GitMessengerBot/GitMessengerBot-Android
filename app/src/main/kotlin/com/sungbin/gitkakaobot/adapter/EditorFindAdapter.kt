package com.sungbin.gitkakaobot.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.EditorFindItem


/**
 * Created by SungBin on 2020-05-12.
 */

class EditorFindAdapter (private val list: ArrayList<EditorFindItem>) :
    RecyclerView.Adapter<EditorFindAdapter.EditorFindViewHolder>() {

    private var ctx: Context? = null

    interface OnItemClickListener {
        fun onItemClick(findText: String, text: String, line: Int, index: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setOnItemClickListener(listener: (String, String, Int, Int) -> Unit) {
        this.listener = object : OnItemClickListener {
            override fun onItemClick(findText: String, text: String, line: Int, index: Int) {
                listener(findText, text, line, index)
            }
        }
    }

    inner class EditorFindViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textview: TextView = view.findViewById(R.id.textview)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): EditorFindViewHolder {
        ctx = viewGroup.context
        val view = LayoutInflater.from(ctx).inflate(R.layout.layout_editor_find_textview, viewGroup, false)
        return EditorFindViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewholder: EditorFindViewHolder, position: Int) {
        val findText = list[position].findText
        val text = list[position].text
        val line = list[position].line
        val index = list[position].index
        viewholder.textview.isSelected = true

        if(index > 0){
            val span = SpannableStringBuilder(text)
            span.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(ctx!!, R.color.colorAccent)
                ),
                index,
                index + findText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            viewholder.textview.text = span
        }
        else viewholder.textview.text = text

        viewholder.textview.setOnClickListener {
            if(listener != null) {
                listener!!.onItemClick(findText, text, line, index)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun getItem(position: Int): EditorFindItem {
        return list[position]
    }

}
