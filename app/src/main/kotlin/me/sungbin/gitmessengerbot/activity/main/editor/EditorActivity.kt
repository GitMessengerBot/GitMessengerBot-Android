/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [EditorActivity.kt] created by Ji Sungbin on 21. 7. 10. 오전 6:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import me.sungbin.gitmessengerbot.activity.main.script.ScriptViewModel
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.util.config.PathConfig

class EditorActivity : ComponentActivity() {

    private val scriptVm: ScriptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scriptId = intent.getStringExtra(PathConfig.IntentScriptId)!!.toInt()

        setContent {
            MaterialTheme {
                Editor(script = scriptVm.getScriptById(scriptId), scriptVm = scriptVm)
            }
        }
    }
}
