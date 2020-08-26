package com.sungbin.gitkakaobot.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.BotItem
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Connection
import org.mozilla.javascript.CompilerEnvirons
import org.mozilla.javascript.Context
import org.mozilla.javascript.Parser
import org.mozilla.javascript.ast.NodeVisitor
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

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

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

        sce_editor.apply {
            setPadding(16, 16, 16, 16)
            text = BotUtil.getBotCode(bot).toEditable()
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
            CoroutineScope(Dispatchers.Default).launch {
                val minifyCode =
                    withContext(Dispatchers.IO) {
                        JSONObject(
                            beautifyClient.data("input", sce_editor.text.toString())
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

        /*val textSize = DataUtils.readData(applicationContext, "TextSize", "17").toInt()
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
        sce_editor.typeface = typeface

        /*sce_editor.textSize = textSize.toFloat()
        if(autoSave){

        }*/

        timer.schedule(
            AutoSaveTimer(
                this,
                sce_editor,
                bot
            ),
            300000,
            300000
        )

        //if(notHighting) sce_editor.applyHighlight = false

        val suggestList: ArrayList<String> = ArrayList()
        highlightingKeywords = arrayListOf(
            "String",
            "File",
            "java",
            "io",
            "Array",
            "int",
            "function",
            "return",
            "var",
            "let",
            "const",
            "if",
            "else",
            "switch",
            "for",
            "while",
            "do",
            "break",
            "continue",
            "case",
            "in",
            "with",
            "true",
            "false",
            "new",
            "null",
            "undefined",
            "typeof",
            "delete",
            "try",
            "catch",
            "finally",
            "prototype",
            "this",
            "super",
            "default",
            "prototype"
        )

        tv_left_slash.text = "\\"
        iv_tab.setOnClickListener { sce_editor.insert("\t\t\t\t") }
        iv_undo.setOnClickListener { sce_editor.undo() }
        iv_redo.setOnClickListener { sce_editor.redo() }
        tv_right_big.setOnClickListener { sce_editor.insert("{") }
        tv_left_big.setOnClickListener { sce_editor.insert("}") }
        tv_right_small.setOnClickListener { sce_editor.insert("(") }
        tv_left_small.setOnClickListener { sce_editor.insert(")") }
        tv_right_slash.setOnClickListener { sce_editor.insert("/") }
        tv_left_slash.setOnClickListener { sce_editor.insert("\\") }
        tv_big_quote.setOnClickListener { sce_editor.insert("\"") }
        tv_small_quote.setOnClickListener { sce_editor.insert("'") }
        tv_dot.setOnClickListener { sce_editor.insert(".") }
        tv_semicolon.setOnClickListener { sce_editor.insert(";") }
        tv_plus.setOnClickListener { sce_editor.insert("+") }
        tv_minus.setOnClickListener { sce_editor.insert("-") }
        tv_underbar.setOnClickListener { sce_editor.insert("_") }

        /*sce_editor.afterTextChanged {
            try {
                suggestList.clear()
                loadClassName(sce_editor.text.toString())
                val layout = sce_editor.layout
                val selectionStart = Selection.getSelectionStart(sce_editor.text)
                val now = it.toString().split("\n")[layout.getLineForOffset(selectionStart)]
                    .trim() //지금 쓰고있는 단어 가져오기
                    .split(" ")[it.toString().split("\n")
                        [layout.getLineForOffset(selectionStart)]
                    .trim().split(" ").size - 1]
                val all = it.toString()
                for (element in highlightingKeywords) {
                    if (!highlightingKeywords.contains(element)) highlightingKeywords.add(element)
                }
                for (element in highlightingKeywords) {
                    if (element.startsWith(now) && element != now
                        && now.isNotBlank() && now.replace(" ", "").length > 1
                    ) {
                        suggestList.add(element)
                    }
                }
                if (suggestList.size == 0) {
                    append_auto.hide(true)
                } else {
                    if (suggestList.isNotEmpty()) {
                        if (suggestList.size == 1) {
                            append_auto.show()
                            append_auto.text = suggestList[0]
                            append_auto.setOnClickListener {
                                append_auto.hide(true)
                                sce_editor.insert(suggestList[0].replace(now, ""))
                            }
                        }
                    } else if (suggestList.size != highlightingKeywords.size &&
                        !all.split("\n")[layout.getLineForOffset(selectionStart)].isBlank()
                    ) {
                        append_auto.show()
                        append_auto.text = getString(R.string.auto_complete)
                        append_auto.setOnClickListener { view ->
                            val p = PopupMenu(applicationContext, view)
                            for ((index, element) in suggestList.withIndex()) {
                                p.menu.add(0, index, 0, element)
                            }
                            p.setOnMenuItemClickListener { item ->
                                append_auto.hide(true)
                                sce_editor.insert(
                                    item.title.toString().replace(now, "")
                                )
                                return@setOnMenuItemClickListener false
                            }
                            p.show()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
    }

    private fun EditText.getStartIndex(line: Int, index: Int): Int {
        var i = 0
        val texts = this.text.split("\n")
        for (n in 0..line) i += texts[n].length
        return i + index
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun loadClassName(source: String) {
        try {
            highlightingKeywords.clear()
            val env = CompilerEnvirons()
            env.languageVersion = Context.VERSION_ES6
            env.optimizationLevel = -1
            val parser = Parser(env)
            val nv = NodeVisitor {
                val type = it.javaClass.name
                if (type == "org.mozilla.javascript.ast.Name") {
                    val name = it.toSource()
                    if (name[0].isUpperCase()) {
                        sce_editor.highlighter.addReservedWord(name, Color.parseColor("#3F51B5"))
                        highlightingKeywords.add(name)
                    }
                    else {
                        sce_editor.highlighter.addReservedWord(name)
                        highlightingKeywords.add(name)
                    }
                }
                true
            }
            parser.parse(source, null, 1).visitAll(nv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun EditText.insert(tag: String) {
        this.text.insert(this.selectionStart, tag)
    }

    private class AutoSaveTimer constructor(
        private val activity: Activity,
        private val editText: EditText,
        private val bot: BotItem
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