/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [FilesItem.kt] created by Ji Sungbin on 21. 9. 1. 오후 7:40
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

// Status Note
// 삭제됨: removed
// 수정됨: modified
// 추가됨: added
data class FilesItem(
    @field:JsonProperty("patch")
    val patch: String?,

    @field:JsonProperty("filename")
    val filename: String?,

    @field:JsonProperty("additions")
    val additions: Int?,

    @field:JsonProperty("deletions")
    val deletions: Int?,

    @field:JsonProperty("changes")
    val changes: Int?,

    @field:JsonProperty("sha")
    val sha: String?,

    @field:JsonProperty("blob_url")
    val blobUrl: String?,

    @field:JsonProperty("raw_url")
    val rawUrl: String?,

    @field:JsonProperty("status")
    val status: String?,

    @field:JsonProperty("contents_url")
    val contentsUrl: String?,
)
