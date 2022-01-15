/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ExceptionDialog.kt] created by Ji Sungbin on 21. 9. 3. 오후 4:49
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.ui.exception

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.common.constant.ExceptionConstant
import io.github.jisungbin.gitmessengerbot.common.core.Util
import io.github.jisungbin.gitmessengerbot.common.extension.toast

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExceptionDialog(exception: MutableState<Exception?>) {
    if (exception.value != null) {
        val context = LocalContext.current
        val content = ExceptionConstant.ObjectContents.random()
        val exceptionMessage = (exception.value!!.message ?: ExceptionConstant.Unknown)
            .split(ExceptionConstant.PathPrefix)[1]

        AlertDialog(
            onDismissRequest = { exception.value = null },
            buttons = {},
            shape = RoundedCornerShape(30.dp),
            text = {
                Text(
                    text = stringResource(
                        R.string.activity_exception_message,
                        content,
                        exceptionMessage
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {
                                toast(
                                    context,
                                    context.getString(R.string.activity_exception_toast_longclick_to_copy_exception)
                                )
                            },
                            onLongClick = {
                                Util.copy(context, exceptionMessage)
                            }
                        )
                )
            }
        )
    }
}
