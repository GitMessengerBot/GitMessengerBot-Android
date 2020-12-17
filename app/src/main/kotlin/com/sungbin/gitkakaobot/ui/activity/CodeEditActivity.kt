package com.sungbin.gitkakaobot.ui.activity

import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.sungbin.androidutils.extensions.toEditable
import com.sungbin.androidutils.util.DialogUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.ActivityCodeEditBinding
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint
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

    private var highlightingKeywords = ArrayList<String>()
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

        binding.tvTitle.text = bot.name

        // binding.tvConsole.movementMethod = ScrollingMovementMethod()

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

        /* btn_beautify.setOnClickListener {
             loadingDialog.show()
             CoroutineScope(Dispatchers.IO).launch {
                 val minifyCode = async {
                         JSONObject(
                             beautifyClient.data("input", binding.sceEditor.text.toString())
                                 .post().wholeText()
                         )["output"].toString()
                     }
                 sce_editor.text = minifyCode.toEditable()
                 runOnUiThread {
                     loadingDialog.close()
                 }
             }
         }

         btn_minify.setOnClickListener {
             loadingDialog.show()
             CoroutineScope(Dispatchers.Default).launch {
                 val minifyCode =
                     withContext(Dispatchers.IO) {
                         minifyClient.data("input", sce_editor.text.toString())
                             .post().wholeText()
                     }
                 if (minifyCode.contains("Error") &&
                     minifyCode.contains("Line") &&
                     minifyCode.contains("Col")
                 ) {
                     runOnUiThread {
                         loadingDialog.setError(
                             Exception("스크립트 소스에 오류가 있습니다.\n오류 수정 후 다시 시도해 주세요.\n\n\n$minifyCode"),
                             true
                         )
                     }
                 } else {
                     sce_editor.text = minifyCode.toEditable()
                     runOnUiThread {
                         loadingDialog.close()
                     }
                 }
             }
         }

         iv_run.setOnClickListener {
             RhinoUtil.debug(sce_console.text.toString(), tv_console)
             sce_console.hideKeyboard()
         }

         val textSize = DataUtils.readData(applicationContext, "TextSize", "17").toInt()
         val autoSave = DataUtils.readData(applicationContext, "AutoSave", "true").toBoolean()
         val notHighting = DataUtils.readData(applicationContext, "NotHighting", "false").toBoolean()
         val notErrorHighting = DataUtils.readData(applicationContext, "NotErrorHighting", "false").toBoolean()*/

        /*val headerView = LayoutInflater
            .from(applicationContext)
            .inflate(R.layout.header_editor_find, null, false)
        val etFind = headerView.findViewById<TextInputsce_editor>(R.id.et_find)
        val swIgnoreUpper = headerView.findViewById<SwitchMaterial>(R.id.sw_ignore)
        val rvList = headerView.findViewById<RecyclerView>(R.id.rv_list)

        rvList.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )*/

        /* etFind.imeOptions = EditorInfo.IME_ACTION_SEARCH
         etFind.setOnEditorActionListener { _, id, _ ->
             if (id == EditorInfo.IME_ACTION_SEARCH) {
                 val items = ArrayList<EditorFindItem>()
                 val result = sce_editor.findText(
                     etFind.text.toString(),
                     swIgnoreUpper.isChecked
                 )
                 if (result.isNotEmpty()) {
                     for (i in result.indices) {
                         val array = result[i]
                         val item = EditorFindItem(
                             sce_editor.text.split("\n")[array[0]],
                             etFind.text.toString(),
                             array[0],
                             array[1]
                         )
                         items.add(item)
                     }
                 } else {
                     items.add(
                         EditorFindItem(
                             getString(R.string.find_none),
                             "null",
                             -1,
                             -1
                         )
                     )
                 }
                 val adapter = EditorFindAdapter(items)
                 adapter.setOnItemClickListener { findText, _, line, index ->
                     if (index > 0) {
                         val i = sce_editor.getStartIndex(line, index)
                         sce_editor.setSelection(i, i + findText.length)
                     }
                 }
                 rvList.adapter = adapter
                 adapter.notifyDataSetChanged()
             } else return@setOnEditorActionListener false
             return@setOnEditorActionListener true
         }*/

        val typeface = ResourcesCompat.getFont(applicationContext, R.font.d2coding)
        binding.sceEditor.typeface = typeface

        /*sce_editor.textSize = textSize.toFloat()
        if(autoSave){

        }*/

        timer.schedule(
            AutoSaveTimer(
                this,
                binding.sceEditor,
                bot
            ),
            300000,
            300000
        )

        //if(notHighting) sce_editor.applyHighlight = false
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

}