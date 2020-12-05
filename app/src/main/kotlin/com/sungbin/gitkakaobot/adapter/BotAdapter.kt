package com.sungbin.gitkakaobot.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.LayoutBotBinding
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.ui.activity.CodeEditActivity
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import java.util.*


/**
 * Created by SungBin on 2020-08-23.
 */

class BotAdapter constructor(
    private val items: ArrayList<Bot>,
    private val activity: Activity
) : RecyclerView.Adapter<BotAdapter.ViewHolder>() {

    init {
        Collections.sort(items, Comparator { item, item2 ->
            return@Comparator item.index.compareTo(item2.index)
        })
    }

    class ViewHolder(private val itemBinding: LayoutBotBinding, private val activity: Activity) :
        RecyclerView.ViewHolder(itemBinding.root) {

        val loadingDialog = LoadingDialog(activity)

        fun bindViewHolder(bot: Bot) {
            with(itemBinding) {
                item = bot
                tvName.isSelected = true
                ivReload.setOnClickListener {
                    ivReload.hide(true)
                    pbReloading.show()
                    CoroutineScope(Dispatchers.Default).launch {
                        val ms1 = System.currentTimeMillis()
                        val status = withContext(Dispatchers.Default) {
                            MessageListener.compileJavaScript(bot)
                        }
                        withContext(Dispatchers.Main) {
                            val ms2 = System.currentTimeMillis()
                            val reloadTime = (ms2 - ms1).toString()
                            ivReload.show()
                            pbReloading.hide(true)
                            if (status.isCompiled) {
                                UiUtil.snackbar(
                                    activity.window.decorView,
                                    activity.getString(R.string.reload_done).replace("@time", reloadTime)
                                )
                            } else {
                                loadingDialog.setError(
                                    Exception("${status.exception}\n\n리로드 시간 : ${reloadTime}ms"),
                                    true
                                )
                                loadingDialog.show()
                            }
                        }
                    }
                }
                ivEdit.setOnClickListener {
                    activity.startActivity<CodeEditActivity>("bot" to bot.toString())
                }
                if (bot.type == BotType.SIMPLE) {
                    trivIcon.setTint(
                        ContextCompat.getColor(
                            trivIcon.context,
                            R.color.colorPrimary
                        )
                    )
                }
                invalidateAll()
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.layout_bot, viewGroup, false
            ), activity
        )

    override fun onBindViewHolder(@NonNull viewholder: ViewHolder, position: Int) {
        viewholder.bindViewHolder(items[position])
    }

    override fun getItemCount() = items.size
    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
}