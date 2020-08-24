package com.sungbin.gitkakaobot.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Selection
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.`interface`.BeautifyInterface
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.ui.dialog.LoadingDialog
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.ui.UiUtil
import com.sungbin.sungbintool.DialogUtils
import com.sungbin.sungbintool.extensions.afterTextChanged
import com.sungbin.sungbintool.extensions.hide
import com.sungbin.sungbintool.extensions.show
import com.sungbin.sungbintool.extensions.toEditable
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_code_edit.*
import kotlinx.android.synthetic.main.layout_editor_tool.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.mozilla.javascript.CompilerEnvirons
import org.mozilla.javascript.Context
import org.mozilla.javascript.Parser
import org.mozilla.javascript.ast.NodeVisitor
import retrofit2.Retrofit
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
    lateinit var prettyClient: Retrofit

    @Named("minify")
    @Inject
    lateinit var minifyClient: Retrofit

    val loadingDialog by lazy {
        LoadingDialog(this)
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_edit)

         SlidingRootNavBuilder(this)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(true)
            .withSavedState(savedInstanceState)
            .withToolbarMenuToggle(toolbar)
            .withGravity(SlideGravity.LEFT)
            .withMenuLayout(R.layout.layout_editor_tool)
            .inject()

        val botJsonString = intent.getStringExtra("bot").toString()
        val bot = BotUtil.createBotItem(JSONObject(botJsonString))

        tv_title.text = bot.name

        DialogUtils.showOnce(
            this,
            getString(R.string.experimental_function),
            getString(R.string.editor_experimental_function_description),
            "experimental_editor2",
            null, false
        )

        val editText = sce_editor.editor
        editText.setPadding(16, 16, 16, 16)
        editText.text = BotUtil.getBotCode(bot).toEditable()

        iv_save.setOnClickListener {
            BotUtil.saveBotCode(bot, editText.text.toString())
            UiUtil.toast(
                applicationContext,
                getString(R.string.saved)
            )
        }

        btn_beautify.setOnClickListener {

        }

        btn_minify.setOnClickListener {
            minifyClient
                .create(BeautifyInterface::class.java).run {
                    loadingDialog.show()

                    requestMinify(
                        editText.text.toString().toRequestBody("text/plain".toMediaType())
                    )
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            editText.text = response.string().toEditable()
                        }, { throwable ->
                            loadingDialog.setError(throwable)
                        }, {
                            loadingDialog.close()
                        })
                }
        }

        /*val textSize = DataUtils.readData(applicationContext, "TextSize", "17").toInt()
        val autoSave = DataUtils.readData(applicationContext, "AutoSave", "true").toBoolean()
        val notHighting = DataUtils.readData(applicationContext, "NotHighting", "false").toBoolean()
        val notErrorHighting = DataUtils.readData(applicationContext, "NotErrorHighting", "false").toBoolean()*/

        /*val headerView = LayoutInflater
            .from(applicationContext)
            .inflate(R.layout.header_editor_find, null, false)
        val etFind = headerView.findViewById<TextInputEditText>(R.id.et_find)
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
                             editText.text.split("\n")[array[0]],
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
                         val i = editText.getStartIndex(line, index)
                         editText.setSelection(i, i + findText.length)
                     }
                 }
                 rvList.adapter = adapter
                 adapter.notifyDataSetChanged()
             } else return@setOnEditorActionListener false
             return@setOnEditorActionListener true
         }*/

        val typeface = ResourcesCompat.getFont(applicationContext, R.font.d2coding)
        editText.typeface = typeface

        /*editText.textSize = textSize.toFloat()
        if(autoSave){

        }*/

        timer.schedule(
            AutoSaveTimer(
                this,
                editText,
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
        iv_tab.setOnClickListener { editText.insert("\t\t\t\t") }
        iv_undo.setOnClickListener { sce_editor.undo() }
        iv_redo.setOnClickListener { sce_editor.redo() }
        tv_right_big.setOnClickListener { editText.insert("{") }
        tv_left_big.setOnClickListener { editText.insert("}") }
        tv_right_small.setOnClickListener { editText.insert("(") }
        tv_left_small.setOnClickListener { editText.insert(")") }
        tv_right_slash.setOnClickListener { editText.insert("/") }
        tv_left_slash.setOnClickListener { editText.insert("\\") }
        tv_big_quote.setOnClickListener { editText.insert("\"") }
        tv_small_quote.setOnClickListener { editText.insert("'") }
        tv_dot.setOnClickListener { editText.insert(".") }
        tv_semicolon.setOnClickListener { editText.insert(";") }
        tv_plus.setOnClickListener { editText.insert("+") }
        tv_minus.setOnClickListener { editText.insert("-") }
        tv_underbar.setOnClickListener { editText.insert("_") }

        editText.afterTextChanged {
            try {
                suggestList.clear()
                loadClassName(editText.text.toString())
                val layout = editText.layout
                val selectionStart = Selection.getSelectionStart(editText.text)
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
                                editText.insert(suggestList[0].replace(now, ""))
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
                                editText.insert(
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
        }
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
                    highlightingKeywords.add(name)
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