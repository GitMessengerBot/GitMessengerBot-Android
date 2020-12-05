package com.sungbin.gitkakaobot.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.RhinoUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.sungbintool.DialogUtils
import com.sungbin.sungbintool.extensions.hideKeyboard
import com.sungbin.sungbintool.extensions.toEditable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_code_edit.*
import kotlinx.android.synthetic.main.activity_code_edit.tv_title
import kotlinx.android.synthetic.main.layout_editor_debug.*
import kotlinx.android.synthetic.main.layout_editor_tool.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.jsoup.Connection
import java.util.*
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by SungBin on 2020-08-24.
 */


@AndroidEntryPoint
class CodeEditActivity : AppCompatActivity() {

    private val timer = Timer()

    @Named("pretty")
    @Inject
    lateinit var beautifyClient: Connection // Jsoup

    @Named("minify")
    @Inject
    lateinit var minifyClient: Connection // Jsoup

    private val loadingDialog by lazy { LoadingDialog(this) }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_edit)

        val botJsonString = intent.getStringExtra("bot").toString()
        val bot = BotUtil.createBotItem(JSONObject(botJsonString))

        tv_title.text = bot.name
        tv_console.movementMethod = ScrollingMovementMethod()

        DialogUtils.showOnce(
            this,
            getString(R.string.experimental_function),
            getString(R.string.editor_experimental_function_description),
            "experimental_editor2",
            null, false
        )

        sce_editor.run {
            setPadding(16, 16, 16, 16)
            text = BotUtil.getBotCode(bot).toEditable()
            typeface = ResourcesCompat.getFont(applicationContext, R.font.d2coding)
        }

        iv_save.setOnClickListener {
            BotUtil.saveBotCode(bot, sce_editor.text.toString())
            UiUtil.toast(
                applicationContext,
                getString(R.string.saved)
            )
        }

        btn_beautify.setOnClickListener {
            loadingDialog.show()
            CoroutineScope(Dispatchers.IO).launch {
                val minifyCode = async {
                    JSONObject(
                        beautifyClient.data("input", sce_editor.text.toString())
                            .post().wholeText()
                    )["output"].toString()
                }
                sce_editor.text = minifyCode.await().toEditable()
                runOnUiThread { loadingDialog.close() }
            }
        }

        btn_minify.setOnClickListener {
            loadingDialog.show()
            CoroutineScope(Dispatchers.IO).launch {
                val minifyCode = async {
                    minifyClient.data("input", sce_editor.text.toString())
                        .post().wholeText()
                }

                minifyCode.await().run {
                    if (contains("Error") &&
                        contains("Line") &&
                        contains("Col")
                    ) {
                        runOnUiThread {
                            loadingDialog.setError(
                                Exception("스크립트 코드에 오류가 있습니다.\n오류 수정 후 다시 시도해 주세요.\n\n\n$minifyCode"),
                                true
                            )
                        }
                    } else {
                        sce_editor.text = toEditable()
                        runOnUiThread { loadingDialog.close() }
                    }
                }
            }
        }

        iv_run.setOnClickListener {
            RhinoUtil.debug(sce_console.text.toString(), tv_console)
            sce_console.hideKeyboard()
        }


        /* if (autoSave) {
            timer.schedule(
                AutoSaveTimer(
                    this,
                    sce_editor,
                    bot
                ),
                300000,
                300000
            )
        } */

    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private class AutoSaveTimer(
        private val activity: Activity,
        private val editText: EditText,
        private val bot: Bot
    ) : TimerTask() {
        override fun run() {
            BotUtil.saveBotCode(bot, editText.text.toString())
            activity.runOnUiThread {
                UiUtil.toast(
                    activity,
                    activity.getString(R.string.auto_saving)
                )
            }
        }
    }

}