package com.sungbin.gitkakaobot.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.sungbin.androidutils.extensions.hideKeyboard
import com.sungbin.androidutils.extensions.toEditable
import com.sungbin.androidutils.util.DialogUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.adapter.EditorFindAdapter
import com.sungbin.gitkakaobot.databinding.ActivityCodeEditBinding
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.model.EditorFound
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import org.json.JSONObject
import org.jsoup.Connection
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList


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
    private val binding by lazy { ActivityCodeEditBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val botJsonString = intent.getStringExtra("bot").toString()
        val bot = BotUtil.createBotItem(JSONObject(botJsonString))

        binding.ivBack.setOnClickListener { finish() }
        binding.tvTitle.text = bot.name

        DialogUtil.showOnce(
            this,
            getString(R.string.codeedit_experimental_function),
            getString(R.string.codeedit_editor_experimental_function_description),
            "experimental_editor2",
            null, false
        )

        binding.sceEditor.apply {
            setPadding(16, 16, 16, 16)
            text = BotUtil.getBotCode(bot).toEditable()
        }

        binding.ivSave.setOnClickListener {
            BotUtil.saveBotCode(bot, binding.sceEditor.text.toString())
            UiUtil.toast(
                applicationContext,
                getString(R.string.saved)
            )
        }

        binding.btnBeautify.setOnClickListener {
            loadingDialog.show()

            Thread {
                Observable.just(
                    JSONObject(
                        beautifyClient.data("input", binding.sceEditor.text.toString())
                            .post().wholeText()
                    )["output"].toString().toEditable()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ beautifyCode ->
                        binding.sceEditor.text = beautifyCode
                    }, { throwable ->
                        loadingDialog.setError(
                            Exception("오류가 발생했습니다.\n\n\n${throwable.localizedMessage}"),
                        )
                    }, {
                        loadingDialog.close()
                    })
            }.start()

        }

        binding.btnMinify.setOnClickListener {
            loadingDialog.show()

            Thread {
                Observable.just(
                    minifyClient.data("input", binding.sceEditor.text.toString())
                        .post().wholeText()
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ minifyCode ->
                        if (minifyCode.contains("Error") &&
                            minifyCode.contains("Line") &&
                            minifyCode.contains("Col")
                        ) {
                            loadingDialog.setError(
                                Exception("스크립트 코드에 오류가 있습니다.\n오류 수정 후 다시 시도해 주세요.\n\n\n$minifyCode"),
                            )
                        } else {
                            binding.sceEditor.text = minifyCode.toEditable()
                        }
                    }, { throwable ->
                        loadingDialog.setError(
                            Exception("오류가 발생했습니다.\n\n\n${throwable.localizedMessage}"),
                        )
                    }, {
                        loadingDialog.close()
                    })
            }.start()

        }

        binding.tietSearch.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                binding.tietSearch.hideKeyboard()
                val items = ArrayList<EditorFound>()
                val result = binding.sceEditor.findText(
                    binding.tietSearch.text.toString(),
                    true
                )
                if (result.isNotEmpty()) {
                    for (array in result) {
                        val item = EditorFound(
                            binding.sceEditor.text.toString().split("\n")[array[0]],
                            binding.tietSearch.text.toString(),
                            array[0],
                            array[1]
                        )
                        items.add(item)
                    }
                } else {
                    items.add(
                        EditorFound(
                            getString(R.string.codeedit_search_empty),
                            "null",
                            -1,
                            -1
                        )
                    )
                }
                val adapter = EditorFindAdapter(items)
                adapter.setOnItemClickListener { findText, _, line, index ->
                    if (index > 0) {
                        val i = binding.sceEditor.getStartIndex(line, index)
                        binding.sceEditor.setSelection(i, i + findText.length)
                    }
                }
                binding.rvFoundResult.adapter = adapter
                adapter.notifyDataSetChanged()
            } else return@setOnEditorActionListener false
            return@setOnEditorActionListener true
        }

        val typeface = ResourcesCompat.getFont(applicationContext, R.font.d2coding)
        binding.sceEditor.typeface = typeface

        timer.schedule(
            AutoSaveTimer(
                this,
                binding.sceEditor,
                bot
            ),
            300000,
            300000
        )
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
                    activity.getString(R.string.codeedit_auto_saved)
                )
            }
        }
    }

    private fun EditText.getStartIndex(line: Int, index: Int): Int {
        var i = 0
        val texts = this.text.split("\n")
        for (n in 0..line) i += texts[n].length
        return i + index
    }

}