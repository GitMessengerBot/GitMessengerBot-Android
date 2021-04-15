/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.fancybottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

/**
 * Created by Ji Sungbin on 2021/04/15.
 */

private interface FancyItemListener {
    fun onItemChanged(item: FancyItem)
}

private var listener: FancyItemListener? = null

@Composable
fun FancyBottomBar(
    fancyColors: FancyColors = FancyColors(),
    fancyOptions: FancyOptions = FancyOptions(),
    items: List<FancyItem>,
    action: FancyItem.() -> Unit
) {
    listener = object : FancyItemListener {
        override fun onItemChanged(item: FancyItem) {
            action(item)
        }
    }

    var fancyItemState by remember { mutableStateOf(items.first().id) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(fancyOptions.barHeight)
            .background(color = fancyColors.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            /*val isFocusedItem: Boolean
                get() {
                    fancyItemStatue == item.id
                }*/ // todo: Why need import?????? What`s current import for `get` property????

            val fancyItemColor by animateColorAsState(if (fancyItemState == item.id) fancyColors.primary else fancyColors.indicatorBackground)

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        fancyItemState = item.id
                        listener?.onItemChanged(item)
                    }
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fancyOptions.indicatorHeight)
                        .background(color = fancyItemColor)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (item.icon != null) {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = fancyItemColor)
                        )
                    }
                    if (item.title.isNotBlank()) {
                        Text(
                            text = item.title,
                            color = fancyItemColor,
                            modifier = Modifier.padding(top = fancyOptions.titleTopPadding),
                            fontFamily = fancyOptions.fontFamily
                        )
                    }
                }
            }
        }
    }
}
