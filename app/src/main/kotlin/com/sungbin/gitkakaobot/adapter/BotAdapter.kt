package com.sungbin.gitkakaobot.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.androidutils.extensions.setTint
import com.sungbin.androidutils.extensions.toColorStateList
import com.sungbin.androidutils.util.ToastType
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.LayoutBotBinding
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.gitkakaobot.ui.activity.CodeEditActivity
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.manager.StackManager
import kotlinx.coroutines.*
import org.jetbrains.anko.startActivity
import java.util.*


/**
 * Created by SungBin on 2020-08-23.
 */

class BotAdapter(
    private val items: ArrayList<Bot>,
    private val activity: Activity,
) : RecyclerView.Adapter<BotAdapter.ViewHolder>() {

    init {
        items.sortWith { item, item2 ->
            return@sortWith item.index.compareTo(item2.index)
        }
    }

    inner class ViewHolder(
        private val itemBinding: LayoutBotBinding,
        private val activity: Activity,
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindViewHolder(bot: Bot) {
            with(itemBinding) {
                item = bot
                vCompileStatue.backgroundTintList = (if (StackManager.v8.containsKey(bot.uuid)) {
                    activity.getColor(R.color.colorGreen)
                } else activity.getColor(R.color.colorLightRed)).toColorStateList()
                swPower.setOnCheckedChangeListener { _, isChecked ->
                    BotUtil.updateBotPower(bot, isChecked)
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

                ivReload.setOnClickListener {
                    when (bot.type) {
                        BotType.SIMPLE -> {
                            // todo: 간편 자동응답 만들기
                        }
                        else -> {
                            CoroutineScope(Dispatchers.Default).launch {
                                val ms = System.currentTimeMillis()
                                val status =
                                    async { com.sungbin.gitkakaobot.bot.Bot.compileJavaScript(bot) }
                                status.await().let {
                                    val ms2 = System.currentTimeMillis()
                                    val reloadTime = ms2 - ms
                                    activity.runOnUiThread {
                                        if (it.isCompiled) {
                                            UiUtil.snackbar(
                                                activity.window.decorView,
                                                activity.getString(
                                                    R.string.bot_reload_done,
                                                    reloadTime
                                                )
                                            )
                                        } else {
                                            UiUtil.toast(
                                                activity,
                                                "${it.exception}\n\n리로드 시간: $reloadTime ms",
                                                ToastType.ERROR
                                            )
                                            it.exception!!.printStackTrace()
//                                            loadingDialog.setError(
//                                                Exception("${it.exception}\n\n리로드 시간: $reloadTime ms")
//                                            )
//                                            loadingDialog.show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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