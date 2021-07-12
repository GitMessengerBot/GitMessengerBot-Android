/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Commit.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.git.model

import com.google.gson.annotations.SerializedName

data class Commit(
    @SerializedName("committer")
    val committer: Committer,

    @SerializedName("author")
    val author: Author,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("tree")
    val tree: Tree,

    @SerializedName("message")
    val message: String,

    @SerializedName("sha")
    val sha: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("verification")
    val verification: Verification,

    @SerializedName("node_id")
    val nodeId: String,

    @SerializedName("parents")
    val parents: List<Any>
)