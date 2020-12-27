package com.sungbin.gitkakaobot.ui.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.ui.tooling.preview.Preview


/**
 * Created by SungBin on 2020-12-27.
 */

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun PreviewGreeting() {
    Greeting("Android")
}