/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GlideImage.kt] created by Ji Sungbin on 21. 6. 14. 오후 9:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.glideimage

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import me.sungbin.gitmessengerbot.GlideApp

@Composable
fun GlideImage(modifier: Modifier, src: Any) {
    val context = LocalContext.current

    AndroidView(factory = { ImageView(context) }, modifier = modifier) {
        GlideApp.with(context).load(src).into(it)
    }
}
